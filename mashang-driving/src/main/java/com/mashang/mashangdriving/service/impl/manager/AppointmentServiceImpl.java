package com.mashang.mashangdriving.service.impl.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.*;
import com.mashang.mashangdriving.domain.param.manager.create.CreateStudentAppointment;
import com.mashang.mashangdriving.domain.param.manager.query.ManagerAppointmentQuery;
import com.mashang.mashangdriving.domain.param.student.create.AddRating;
import com.mashang.mashangdriving.domain.vo.manager.ManagerAppointmentListVo;
import com.mashang.mashangdriving.domain.vo.student.ContactInstructorVo;
import com.mashang.mashangdriving.domain.vo.student.MyAppointmentDtlVo;
import com.mashang.mashangdriving.domain.vo.student.StudentAppointmentVo;
import com.mashang.mashangdriving.mapper.manager.AppointmentMapper;
import com.mashang.mashangdriving.mapper.manager.DrivingLocationMapper;
import com.mashang.mashangdriving.mapper.manager.InstructorMapper;
import com.mashang.mashangdriving.mapper.manager.SubjectMapper;
import com.mashang.mashangdriving.mapper.student.CarMapper;
import com.mashang.mashangdriving.mapper.student.RatingMapper;
import com.mashang.mashangdriving.mapper.student.StudentMapper;
import com.mashang.mashangdriving.mapping.student.CreateAppointmentMapping;
import com.mashang.mashangdriving.service.impl.student.AppointmentPeakVO;
import com.mashang.mashangdriving.service.manager.IAppointmentService;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.ruoyi.common.constant.AppointmentConstants;
import com.ruoyi.common.constant.InstructorConstants;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.ZoneId;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;


@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, DrivingAppointment> implements IAppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private InstructorMapper instructorMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private DrivingLocationMapper drivingLocationMapper;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private RatingMapper ratingMapper;

    @Override
    public int countYesterdayStatusOne() {
        LocalDate today = LocalDate.now();

        // 昨天 00:00:00
        LocalDateTime startTime = today
                .minusDays(1)       // 回到昨天
                .atStartOfDay();    // 昨天 00:00:00

        // 今天 00:00:00
        LocalDateTime endTime = today.atStartOfDay();


        // ===== 2. 构造查询条件 =====

        LambdaQueryWrapper<DrivingAppointment> wrapper = Wrappers.lambdaQuery();

        // 条件 1：状态 = "1"
        wrapper.eq(DrivingAppointment::getStatus, AppointmentConstants.PROCESSED_STATUS);

        // 条件 2：创建时间 >= 昨天开始
        wrapper.ge(DrivingAppointment::getCreateTime, startTime);

        // 条件 3：创建时间 < 今天开始
        wrapper.lt(DrivingAppointment::getCreateTime, endTime);


        // ===== 3. 执行 count 查询 =====
        Long countLong = appointmentMapper.selectCount(wrapper);
        int countYesterdayStatusOne = (int) (countLong != null ? countLong.longValue() : 0L);
        return countYesterdayStatusOne;
    }

    @Override
    public int countOnDayStatusOne() {
        LambdaQueryWrapper<DrivingAppointment> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DrivingAppointment::getStatus, AppointmentConstants.PROCESSED_STATUS);
        Long countLong = appointmentMapper.selectCount(wrapper);
        int countOnDayStatusOne = (int) (countLong != null ? countLong.longValue() : 0L);
        return countOnDayStatusOne;
    }

    @Override
    public StudentAppointmentVo createAppointment(CreateStudentAppointment createStudentAppointment) {

        //判断该教练是是否在职
        LambdaQueryWrapper<DrivingInstructor> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DrivingInstructor::getStatus, InstructorConstants.ON_STATUS);
        DrivingInstructor instructor = instructorMapper.selectById(createStudentAppointment.getInstructorId());
        if (instructor == null || !InstructorConstants.ON_STATUS.equals(instructor.getStatus())) {
            throw new RuntimeException("当前教练不存在或未在职，请选择其他教练");
        }

        //可预约时间
        Date noTimeStart = instructor.getSchedulableTimeStart();
        Date noTimeEnd = instructor.getSchedulableTimeEnd();

        if (noTimeStart != null && createStudentAppointment.getStartTime().before(noTimeStart)) {
            throw new RuntimeException("预约时间早于教练可预约开始时间");
        }

        if (noTimeEnd != null && createStudentAppointment.getEndTime().after(noTimeEnd)) {
            throw new RuntimeException("预约时间晚于教练可预约结束时间");
        }

        //检查是否命中【不可预约时间段】
        Date instructorNoTimeStart = instructor.getNoTimeStart();
        Date instructorNoTimeEnd = instructor.getNoTimeEnd();

        boolean overlap = createStudentAppointment.getStartTime().before(instructorNoTimeEnd)
                && createStudentAppointment.getEndTime().after(instructorNoTimeStart);
        if (overlap) {
            throw new RuntimeException("预约时间与教练不可预约时间段冲突");
        }

        //连续时间段校验（时长）
        long minutes = (createStudentAppointment.getEndTime().getTime()
                -
                createStudentAppointment.getStartTime().getTime()) / (1000 * 60);
        if (minutes < 60) {
            throw new RuntimeException("预约时间至少为1小时");
        }

        //时间整点校验
        Calendar cal = Calendar.getInstance();
        cal.setTime(createStudentAppointment.getStartTime());
        if (cal.get(Calendar.MINUTE) != 0) {
            throw new RuntimeException("预约开始时间必须为整点");
        }

        cal.setTime(createStudentAppointment.getEndTime());
        if (cal.get(Calendar.MINUTE) != 0) {
            throw new RuntimeException("预约结束时间必须为整点");
        }

        LambdaQueryWrapper<DrivingStudent> studentWrapper = Wrappers.lambdaQuery();
        studentWrapper.eq(DrivingStudent::getUserId, SecurityUtils.getUserId());
        DrivingStudent student = studentMapper.selectOne(studentWrapper);

        DrivingAppointment drivingAppointment = CreateAppointmentMapping.INSTANCE.toEntity(createStudentAppointment);
        drivingAppointment.setStudentId(student.getStudentId());
        drivingAppointment.setStatus(AppointmentConstants.PROCESSED_STATUS);
        appointmentMapper.insert(drivingAppointment);

        DrivingSubject subject = subjectMapper.selectById(createStudentAppointment.getSubjectId());

        //返回预约消息
        StudentAppointmentVo appointmentVo = appointmentMapper.appointmento();
        appointmentVo.setSubjectName(subject.getSubjectName());
        appointmentVo.setInstructorId(createStudentAppointment.getInstructorId());
        appointmentVo.setStartTime(createStudentAppointment.getStartTime());
        appointmentVo.setEndTime(createStudentAppointment.getEndTime());
        appointmentVo.setSubjectId(createStudentAppointment.getSubjectId());
        return appointmentVo;
    }

    @Override
    public List<MyAppointmentDtlVo> myAllAppointment(String status) {

        LambdaQueryWrapper<DrivingStudent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DrivingStudent::getUserId,SecurityUtils.getUserId());
        DrivingStudent drivingStudent = studentMapper.selectOne(wrapper);

        LambdaQueryWrapper<DrivingAppointment> allWrapper = new LambdaQueryWrapper<>();
        allWrapper.eq(DrivingAppointment::getStudentId, drivingStudent.getStudentId());
        allWrapper.eq(StringUtils.isNotEmpty(status),DrivingAppointment::getStatus,status);
        List<DrivingAppointment> drivingAppointments = appointmentMapper.selectList(allWrapper);

        List<MyAppointmentDtlVo> myAppointmentList = new ArrayList<>();

        for (DrivingAppointment appointment : drivingAppointments) {

            MyAppointmentDtlVo myAppointmentDtlVo = new MyAppointmentDtlVo();

            Long locationId = appointment.getLocationId();
            Long instructorId = appointment.getInstructorId();
            Long subjectId = appointment.getSubjectId();

            //todo 自己判断该数据是否del_flog是否为0 自己手写sql语句
            DrivingInstructor instructor = instructorMapper.selectById(instructorId);
            DrivingLocation location = drivingLocationMapper.selectById(locationId);
            DrivingSubject subject = subjectMapper.selectById(subjectId);

            LambdaQueryWrapper<DrivingCar> carWrapper = new LambdaQueryWrapper<>();
            carWrapper.eq(DrivingCar::getInstructorId, instructor.getInstructorId());
            DrivingCar car = carMapper.selectOne(carWrapper);

            myAppointmentDtlVo.setInstructorId(instructor.getInstructorId());
            myAppointmentDtlVo.setInstructorName(instructor.getInstructorName());
            myAppointmentDtlVo.setCarId(car.getCarId());
            myAppointmentDtlVo.setCarName(car.getCarName());
            myAppointmentDtlVo.setCarBrand(car.getCarBrand());
            myAppointmentDtlVo.setPlateNumber(car.getPlateNumber());
            myAppointmentDtlVo.setLocationId(location.getLocationId());
            myAppointmentDtlVo.setLocationName(location.getLocationName());
            myAppointmentDtlVo.setSubjectId(subject.getSubjectId());
            myAppointmentDtlVo.setSubjectName(subject.getSubjectName());
            myAppointmentDtlVo.setStatus(appointment.getStatus());
            myAppointmentList.add(myAppointmentDtlVo);
        }
        return myAppointmentList;
    }

    @Override
    public ContactInstructorVo getContactInstructor(Long appointmentId) {

        ContactInstructorVo contactInstructorVo = new ContactInstructorVo();
        DrivingAppointment drivingAppointment = appointmentMapper.selectById(appointmentId);

        DrivingInstructor drivingInstructor = instructorMapper.selectById(drivingAppointment.getInstructorId());
        contactInstructorVo.setInstructorName(drivingInstructor.getInstructorName());
        contactInstructorVo.setInstructorId(drivingAppointment.getInstructorId());
        contactInstructorVo.setPhone(drivingInstructor.getPhone());
        return contactInstructorVo;
    }

    @Override
    public int deleteAppointment(Long appointmentId) {

        DrivingAppointment appointment = appointmentMapper.selectById(appointmentId);
        if (AppointmentConstants.ON_PROCESSED_STATUS.equals(appointment.getStatus()
        )){
            throw new RuntimeException("该预约已完成,无法删除");
        }

        return appointmentMapper.deleteById(appointmentId);
    }

    @Override
    public int createRating(AddRating addRating) {

        DrivingAppointment appointment = appointmentMapper.selectById(addRating.getAppointmentId());

        if (appointment.getAppointmentId() == null){
            throw new RuntimeException("不存在该预约");
        }

        if (!Objects.equals(appointment.getStatus(), AppointmentConstants.ON_PROCESSED_STATUS)){
            throw new RuntimeException("未完成预约的状况下不可以评价教练");
        }
        LambdaQueryWrapper<DrivingStudent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DrivingStudent::getUserId,SecurityUtils.getUserId());
        DrivingStudent student = studentMapper.selectOne(wrapper);

        DrivingRating rating = new DrivingRating();
        rating.setContanct(addRating.getContanct());
        rating.setScore(addRating.getScore());
        rating.setStudentId(student.getStudentId());
        rating.setInstructorId(appointment.getInstructorId());

        return ratingMapper.insert(rating);
    }

    @Override
    public int awaitingApproval() {

        LambdaQueryWrapper<DrivingAppointment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DrivingAppointment::getStatus,AppointmentConstants.PROCESSED_STATUS);
        Long count = appointmentMapper.selectCount(wrapper);

        // 直接调用intValue()方法转换
        return count.intValue();
    }

    @Override
    public int confirmApproval() {

        LambdaQueryWrapper<DrivingAppointment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DrivingAppointment::getStatus,AppointmentConstants.NO_PROCESSED_STATUS);
        Long count = appointmentMapper.selectCount(wrapper);

        return count.intValue();
    }

    @Override
    public int completedTodayApproval() {

        // 获取今天的开始和结束时间
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();  // 今天的00:00:00
        LocalDateTime endOfDay = today.atTime(23, 59, 59, 999999999);  // 今天的23:59:59.999999999

        LambdaQueryWrapper<DrivingAppointment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DrivingAppointment::getStatus,AppointmentConstants.ON_PROCESSED_STATUS);
        wrapper.between(DrivingAppointment::getApproveTime, startOfDay, endOfDay);
        Long count = appointmentMapper.selectCount(wrapper);

        return count.intValue();
    }

    @Override
    public int cancelApproval() {

        Long count = appointmentMapper.cancelApproval();

        return count.intValue();
    }

    /**
     * 查询本周预约高峰时间段
     *
     * 业务处理流程说明：
     *
     * 【第一步】计算本周时间范围
     *  - 以当前系统时间为基准
     *  - 本周从周一 00:00:00 开始
     *  - 到周日 23:59:59 结束
     *
     * 【第二步】查询本周内有效的预约记录
     *  - 使用 MyBatis Plus 的 LambdaQueryWrapper 构造查询条件
     *  - 只查询本周时间范围内的预约
     *  - 统计所有且未删除的预约记录
     *
     * 【第三步】按“星期 + 时间段”维度进行统计
     *  - 将预约开始时间转换为 LocalDateTime
     *  - 解析出星期信息（周一 ~ 周日）
     *  - 拼接预约时间段（如：09:00-11:00）
     *  - 使用 Map 进行分组计数
     *
     * 【第四步】封装返回结果
     *  - 将统计结果转换为 VO 对象
     *  - 按预约次数降序排序
     *  - 返回给 Controller 层用于前端展示
     *
     * @return 本周预约高峰时间段统计结果
     */
    @Override
    public List<AppointmentPeakVO> getWeeklyAppointmentPeaks() {

        LocalDate today = LocalDate.now();

        // 本周周一的开始时间（00:00:00）
        LocalDateTime weekStart = today
                .with(DayOfWeek.MONDAY)
                .atStartOfDay();

        // 本周周日的结束时间（23:59:59）
        LocalDateTime weekEnd = today
                .with(DayOfWeek.SUNDAY)
                .atTime(23, 59, 59);

        LambdaQueryWrapper<DrivingAppointment> wrapper = new LambdaQueryWrapper<>();
        // 查询本周时间范围内的预约记录
        wrapper.between(
                DrivingAppointment::getStartTime,
                weekStart,
                weekEnd
        );

        List<DrivingAppointment> appointmentList = this.list(wrapper);

        // 用于统计“星期 + 时间段”对应的预约次数
        Map<String, Integer> peakMap = new HashMap<>();

        for (DrivingAppointment appointment : appointmentList) {

            // 将 Date 转换为 LocalDateTime，方便时间处理
            LocalDateTime startTime = appointment.getStartTime()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            LocalDateTime endTime = appointment.getEndTime()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            // 解析星期（周一 ~ 周日）
            String week = convertWeek(startTime.getDayOfWeek());

            // 拼接预约时间段，如：09:00-11:00
            String timeRange = startTime.toLocalTime()
                    + "-" + endTime.toLocalTime();

            // 组合统计 Key（防止时间段冲突）
            String key = week + "_" + timeRange;

            // 累加预约次数
            peakMap.put(key, peakMap.getOrDefault(key, 0) + 1);
        }

        return peakMap.entrySet()
                .stream()
                // 按预约次数降序排序
                .sorted((a, b) -> b.getValue() - a.getValue())
                // 转换为前端需要的 VO 结构
                .map(entry -> {
                    AppointmentPeakVO vo = new AppointmentPeakVO();
                    String[] arr = entry.getKey().split("_");
                    vo.setWeek(arr[0]);
                    vo.setTimeRange(arr[1]);
                    vo.setCount(entry.getValue());
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentPeakVO> getNextWeeklyAppointmentPeaks() {

        // 1. 本周真实统计
        List<AppointmentPeakVO> thisWeekPeaks = getWeeklyAppointmentPeaks();

        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(System.getenv("sk-5ff2e4ff2fde44119d68cb8dce3f9e5b"))
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();

        String prompt = """
    以下是基于历史数据预测的【下周预约次数】：

    %s

    请根据常见预约规律进行微调：
    1. 只能调整 count
    2. count 必须是正整数
    3. 不允许新增或删除时间段
    4. 每个 count 调整幅度不超过 ±20%%
    5. 只返回 JSON 数组，不要任何解释
    """.formatted(JSON.toJSONString(thisWeekPeaks));

        ChatCompletionCreateParams params =
                ChatCompletionCreateParams.builder()
                        .model("qwen-plus")
                        .addUserMessage(prompt)
                        .build();

        ChatCompletion completion =
                client.chat().completions().create(params);

        String content = completion.choices()
                .get(0)
                .message()
                .content()
                .orElse("[]");

        // 清洗 ```json
        content = content.replaceAll("```json", "")
                .replaceAll("```", "")
                .trim();
        try {
            ChatCompletion chatCompletion = client.chat().completions().create(params);
            System.out.println(chatCompletion);
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return JSON.parseArray(content, AppointmentPeakVO.class);
    }


    @Override
    public Page<ManagerAppointmentListVo> page(PageQuery pageQuery, ManagerAppointmentQuery query) {

        //创建分页对象
        Page<ManagerAppointmentListVo> page = new Page<>(
                pageQuery.getPageNum(),
                pageQuery.getPageSize()
        );

        QueryWrapper<ManagerAppointmentListVo> qw = new QueryWrapper<>();
        qw.eq(
                StringUtils.isNotBlank(query.getStatus()),
                "app.status",
                query.getStatus()
        );

        qw.like(
                StringUtils.isNotBlank(query.getStudentName()),
                "stu.student_name",
                query.getStudentName()
        );

        qw.like(
                StringUtils.isNotBlank(query.getPhone()),
                "stu.phone",
                query.getPhone()
        );

        qw.like(
                StringUtils.isNotBlank(query.getInstructorName()),
                "ins.instructor_name",
                query.getInstructorName()
        );

        qw.like(
                StringUtils.isNotBlank(query.getSubjectName()),
                "sub.subject_name",
                query.getSubjectName()
        );

        return appointmentMapper.page(page,qw);
    }

    @Override
    public ManagerAppointmentListVo appointmentDtl(Long appointmentId) {

        return appointmentMapper.appointmentDtl(appointmentId);
    }

    @Override
    public int updateStatus(Long appointmentId) {

        DrivingAppointment appointment =
                appointmentMapper.selectById(appointmentId);

        if (appointment == null) {
            throw new RuntimeException("预约不存在");
        }

        if (!Objects.equals(appointment.getStatus(), AppointmentConstants.PROCESSED_STATUS)){
            throw new RuntimeException("当前预约已审批");
        }
        LambdaUpdateWrapper<DrivingAppointment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(DrivingAppointment::getAppointmentId,appointmentId)
                .set(DrivingAppointment::getStatus,AppointmentConstants.NO_PROCESSED_STATUS);

        return appointmentMapper.update(null, updateWrapper);
    }

    @Override
    public int managerDeleteAppointment(Long appointmentId) {

        DrivingAppointment appointment =
                appointmentMapper.selectById(appointmentId);

        if (appointment == null) {
            throw new RuntimeException("预约不存在");
        }

        if (!Objects.equals(appointment.getStatus(), AppointmentConstants.PROCESSED_STATUS)){
            throw new RuntimeException("当前预约已审批");
        }

        LambdaUpdateWrapper<DrivingAppointment> deleteWrapper = new LambdaUpdateWrapper<>();
        deleteWrapper.eq(DrivingAppointment::getAppointmentId,appointmentId);

        return appointmentMapper.update(null,deleteWrapper);
    }

    /**
     * 将 DayOfWeek 枚举转换为中文星期
     *
     * @param dayOfWeek Java 时间 API 中的星期枚举
     * @return 中文星期描述（周一 ~ 周日）
     */
    private String convertWeek(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "周一";
            case TUESDAY:
                return "周二";
            case WEDNESDAY:
                return "周三";
            case THURSDAY:
                return "周四";
            case FRIDAY:
                return "周五";
            case SATURDAY:
                return "周六";
            case SUNDAY:
                return "周日";
            default:
                return "";
        }
    }

}
