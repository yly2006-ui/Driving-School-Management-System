package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.*;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingInstructorCreate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingInstructorUpdate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingScheduleUpdateDTO;
import com.mashang.mashangdriving.domain.vo.manager.DrivingInstructorListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingOneInstructorVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingRatingStudentVO;
import com.mashang.mashangdriving.mapper.manager.DrivingInstructorMapper;
import com.mashang.mashangdriving.mapper.manager.DrivingRatingMapper;
import com.mashang.mashangdriving.mapper.manager.DrivingStudentManagerMapper;
import com.mashang.mashangdriving.mapper.student.DrivingStudentMapper;
import com.mashang.mashangdriving.service.manager.IDrivingInstructorService;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DrivingInstructorServiceImpl extends ServiceImpl<DrivingInstructorMapper, DrivingInstructor> implements IDrivingInstructorService {

    @Autowired
    private DrivingRatingMapper drivingRatingMapper;
    @Autowired
    private DrivingStudentManagerMapper drivingStudentManagerMapper;
    //4:00-23:00
    private static final int TOTAL_TIME_SLOTS = 19;
    //周一到周日
    private static final int DAYS_OF_WEEK = 7;

    @Override
    public Page<DrivingInstructorListVo> getList(Page<DrivingInstructorListVo> page) {
        Page<DrivingInstructorListVo> list = baseMapper.getList(page);
        List<DrivingInstructorListVo> records = list.getRecords();

        return list;
    }

    @Override
    public DrivingOneInstructorVo getByInstructorId(Long instructorId) {
        return baseMapper.getByInstructorId(instructorId);
    }

    @Override
    public Page<DrivingOneInstructorVo> getByInstructorName(String instructorName,Page<DrivingOneInstructorVo> page) {
        return baseMapper.getByInstructorName(instructorName, page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DrivingInstructorListVo insert(DrivingInstructorCreate dto) {
        DrivingInstructor drivingInstructor = new DrivingInstructor();
        drivingInstructor.setInstructorName(dto.getInstructorName());
        drivingInstructor.setIdNumber(dto.getIdNumber());
        drivingInstructor.setPhone(dto.getPhone());
        if(StringUtils.isNotBlank(dto.getCertificate())){
            drivingInstructor.setCertificate(dto.getCertificate());
        }
        if(StringUtils.isNotBlank(dto.getGoodSubject())){
            drivingInstructor.setGoodSubject(dto.getGoodSubject());
        }
        if(StringUtils.isNotBlank(dto.getTeachingYears())){
            drivingInstructor.setTeachingYears(dto.getTeachingYears());
        }
        drivingInstructor.setEntryDate(new Date());
        drivingInstructor.setInstructorNo("JL"+drivingInstructor.getEntryDate().getTime());
        drivingInstructor.setDelFlag("0");
        drivingInstructor.setStatus("0");
        int i = baseMapper.insert(drivingInstructor);
        if (i<=0){
            throw new RuntimeException("保存失败");
        }
        if (drivingInstructor.getInstructorId()!=null){
            drivingInstructor.setUserId(Long.valueOf("100"+drivingInstructor.getInstructorId()));
            baseMapper.updateById(drivingInstructor);
        }else  {
            drivingInstructor.setUserId(null);
        }

        return getDrivingInstructorVo(drivingInstructor);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DrivingInstructorListVo update(DrivingInstructorUpdate drivingInstructorUpdate) {
        if (drivingInstructorUpdate.getInstructorId()==null){
            throw new RuntimeException("ID不能为空");
        }

        LambdaUpdateWrapper<DrivingInstructor> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(DrivingInstructor::getInstructorId,drivingInstructorUpdate.getInstructorId());

        if(StringUtils.isNotBlank(drivingInstructorUpdate.getInstructorName())){
            wrapper.set(DrivingInstructor::getInstructorName,drivingInstructorUpdate.getInstructorName());
        }
        if(StringUtils.isNotBlank(drivingInstructorUpdate.getPhone())){
            wrapper.set(DrivingInstructor::getPhone,drivingInstructorUpdate.getPhone());
        }
        if (StringUtils.isNotBlank(drivingInstructorUpdate.getIdNumber())){
            wrapper.set(DrivingInstructor::getIdNumber, drivingInstructorUpdate.getIdNumber());
        }
        if(StringUtils.isNotBlank(drivingInstructorUpdate.getTeachingYears())){
            wrapper.set(DrivingInstructor::getTeachingYears,drivingInstructorUpdate.getTeachingYears());
        }
        if(StringUtils.isNotBlank(drivingInstructorUpdate.getGoodSubject())){
            wrapper.set(DrivingInstructor::getGoodSubject,drivingInstructorUpdate.getGoodSubject());
        }
        if(StringUtils.isNotBlank(drivingInstructorUpdate.getPhoto())){
            wrapper.set(DrivingInstructor::getPhoto, drivingInstructorUpdate.getPhoto());
        }
        if(StringUtils.isNotBlank(drivingInstructorUpdate.getStatus())){
            wrapper.set(DrivingInstructor::getStatus,drivingInstructorUpdate.getStatus());
        }
        int update = baseMapper.update(null, wrapper);
        if (update<=0){
            throw new RuntimeException("更新失败");
        }
        DrivingInstructor instructor = baseMapper.selectById(drivingInstructorUpdate.getInstructorId());
        if (instructor==null){
            throw new RuntimeException("该教练不存在");
        }
        return getDrivingInstructorVo(instructor);
    }

    @Override
    public Page<DrivingRatingStudentVO> getRatingByInstructorWithStudentInfo(Long instructorId, Page<DrivingRatingStudentVO> page,String timeFilter) {
        if(timeFilter!=null){
            List<String> list = Arrays.asList("all", "week", "month", "quarter");
            if(!list.contains(timeFilter)){
                throw new RuntimeException("不包含\"all\", \"week\", \"month\", \"quarter\"的字段");
            }
        }
        Page<DrivingRatingStudentVO> rating = drivingRatingMapper.getRatingByInstructorWithStudentInfo(instructorId, page,timeFilter);
        if (rating==null){
            throw new RuntimeException("查询错误");
        }
        return rating;
    }

//    @Override
//    public Page<DrivingRating> getRating(Long instructorId,Page<DrivingRating> page) {
//        DrivingInstructor instructor = baseMapper.selectById(instructorId);
//        if (instructor == null) {
//            throw new RuntimeException("教练不存在评论");
//        }
//        LambdaQueryWrapper<DrivingRating> wrapper = new LambdaQueryWrapper<DrivingRating>()
//                .eq(DrivingRating::getInstructorId, instructorId);
//        wrapper.select(DrivingRating::getStudentId,DrivingRating::getInstructorId);
//        LambdaQueryWrapper<DrivingStudent> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
//
//        return drivingRatingMapper.selectPage(page, wrapper);
//    }


    @Override
    public int[][] createScheduleMatrixFromDB(Long instructorId) {
        List<Map<String, Object>> schedule = baseMapper.findWeeklyScheduleByInstructorId(instructorId);
        int[][] scheduleMatrix = initializeMatrix();
        if(schedule!=null&&!schedule.isEmpty()){
            fillMatrixFromRecords(scheduleMatrix,schedule);
        }
        return scheduleMatrix;
    }

    @Override
    public DrivingInstructorStatus getAllStatus() {
        return baseMapper.getAllStatus();
    }


    @Override
    public void updatePartialSchedule(DrivingScheduleUpdateDTO dto) {
        List<Integer> timeSlotList = dto.getTimeSlotList();
        if (timeSlotList == null || timeSlotList.isEmpty()) {
            throw new RuntimeException("时间段列表不能为空");
        }

        ArrayList<CoachWeeklySchedule> coachWeeklySchedules = new ArrayList<>();
        for (Integer timeSlot : timeSlotList) {
            if (timeSlot == null) {
                continue;
            }

            CoachWeeklySchedule coachWeeklySchedule = new CoachWeeklySchedule();

            coachWeeklySchedule.setInstructorId(dto.getInstructorId());
            coachWeeklySchedule.setWeekDay(dto.getWeekDay());
            coachWeeklySchedule.setTimeSlot(timeSlot);
            coachWeeklySchedule.setStatus(dto.getStatus());
            coachWeeklySchedules.add(coachWeeklySchedule);
        }

            baseMapper.batchUpsert(coachWeeklySchedules);
    }

    private static DrivingInstructorListVo getDrivingInstructorVo(DrivingInstructor drivingInstructor) {
        DrivingInstructorListVo drivingInstructorListVo = new DrivingInstructorListVo();
        drivingInstructorListVo.setIdNumber(String.valueOf(drivingInstructor.getIdNumber()));
        drivingInstructorListVo.setInstructorName(drivingInstructor.getInstructorName());
        drivingInstructorListVo.setCertificate(drivingInstructor.getCertificate());
        drivingInstructorListVo.setGoodSubject(drivingInstructor.getGoodSubject());
        drivingInstructorListVo.setEntryDate(drivingInstructor.getEntryDate());
        drivingInstructorListVo.setTeachingYears(drivingInstructor.getTeachingYears());
        drivingInstructorListVo.setPhone(drivingInstructor.getPhone());
        drivingInstructorListVo.setPhoto(drivingInstructor.getPhoto());
        drivingInstructorListVo.setInstructorId(drivingInstructor.getInstructorId());
        drivingInstructorListVo.setStatus(drivingInstructor.getStatus());
        drivingInstructorListVo.setDelFlag(drivingInstructor.getDelFlag());
        return drivingInstructorListVo;
    }

    private int[][] initializeMatrix() {
        int[][] matrix = new int[TOTAL_TIME_SLOTS][DAYS_OF_WEEK];
        for (int i = 0; i < TOTAL_TIME_SLOTS; i++) {
            Arrays.fill(matrix[i], -1);
        }
        return matrix;
    }
    private void fillMatrixFromRecords(int[][] matrix, List<Map<String, Object>> records) {
        for (Map<String, Object> record : records) {
            Integer weekDay = getIntegerValue(record, "week_day");
            Integer timeSlot = getIntegerValue(record, "time_slot");
            Integer status = getIntegerValue(record, "status");

            // 验证数据有效性
            if (isValidWeekDay(weekDay) && isValidTimeSlot(timeSlot) && status != null) {
                // week_day: 1=周一, 2=周二, ..., 7=周日
                // 转换为列索引: 0=周一, 1=周二, ..., 6=周日
                int columnIndex = weekDay - 1;
                matrix[timeSlot][columnIndex] = status;
            }
        }
    }
    private boolean isValidWeekDay(Integer weekDay) {
        return weekDay != null && weekDay >= 1 && weekDay <= DAYS_OF_WEEK;
    }
    private boolean isValidTimeSlot(Integer timeSlot) {
        return timeSlot != null && timeSlot >= 0 && timeSlot < TOTAL_TIME_SLOTS;
    }
    private Integer getIntegerValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return null;
    }
}
