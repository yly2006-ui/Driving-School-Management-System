package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.MyInstructor;
import com.mashang.mashangdriving.domain.vo.student.MyInstructorVo;
import com.mashang.mashangdriving.mapper.student.MyInstructorMapper;
import com.mashang.mashangdriving.service.student.IMyInstructorService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class MyInstructorServiceImpl extends ServiceImpl<MyInstructorMapper, MyInstructor> implements IMyInstructorService {

    //4:00-23:00
    private static final int TOTAL_TIME_SLOTS = 19;
    //周一到周日
    private static final int DAYS_OF_WEEK = 7;
    @Override
    public MyInstructorVo selectMyInstructorById(Long studentId) {
        MyInstructorVo myInstructorVo = baseMapper.selectMyInstructorById(studentId);
        if (myInstructorVo == null) {
            throw new RuntimeException("数据不存在");
        }
        return myInstructorVo;
    }

    @Override
    public int[][] createScheduleMatrixFromDB(Long studentId) {
        List<Map<String, Object>> schedule = baseMapper.findWeeklyScheduleByStudentId(studentId);
        int[][] scheduleMatrix = initializeMatrix();
        if(schedule!=null&&!schedule.isEmpty()){
            fillMatrixFromRecords(scheduleMatrix,schedule);
        }
        return scheduleMatrix;
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
