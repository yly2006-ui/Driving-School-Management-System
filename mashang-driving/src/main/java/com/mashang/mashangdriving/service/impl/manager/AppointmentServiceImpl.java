package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.*;
import com.mashang.mashangdriving.domain.param.manager.create.CreateStudentAppointment;
import com.mashang.mashangdriving.domain.vo.student.ContactInstructorVo;
import com.mashang.mashangdriving.domain.vo.student.MyAppointmentDtlVo;
import com.mashang.mashangdriving.domain.vo.student.StudentAppointmentVo;
import com.mashang.mashangdriving.mapper.manager.AppointmentMapper;
import com.mashang.mashangdriving.mapper.manager.DrivingLocationMapper;
import com.mashang.mashangdriving.mapper.manager.InstructorMapper;
import com.mashang.mashangdriving.mapper.manager.SubjectMapper;
import com.mashang.mashangdriving.mapper.student.CarMapper;
import com.mashang.mashangdriving.mapper.student.StudentMapper;
import com.mashang.mashangdriving.mapping.student.CreateAppointmentMapping;
import com.mashang.mashangdriving.service.manager.IAppointmentService;
import com.ruoyi.common.constant.AppointmentConstants;
import com.ruoyi.common.constant.InstructorConstants;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public List<MyAppointmentDtlVo> myAllAppointment() {

        LambdaQueryWrapper<DrivingStudent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DrivingStudent::getUserId,SecurityUtils.getUserId());
        DrivingStudent drivingStudent = studentMapper.selectOne(wrapper);

        LambdaQueryWrapper<DrivingAppointment> allWrapper = new LambdaQueryWrapper<>();
        allWrapper.eq(DrivingAppointment::getStudentId, drivingStudent.getStudentId());
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
}
