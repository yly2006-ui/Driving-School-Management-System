package com.mashang.mashangdriving.mapper.student;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.vo.student.DrivingStudentDtlVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface DrivingStudentMapper extends BaseMapper<DrivingStudent> {
    DrivingStudentDtlVo selectById(@Param("studentId") Long studentId);

    //查询学生对应邮箱
    String selectMail(Long userId);

    // 更新学员头像
    @Update("UPDATE driving_student SET avatar = #{avatarUrl} WHERE student_id = #{studentId}")
    void updateAvatar(@Param("studentId") Long studentId, @Param("avatarUrl") String avatarUrl);
}
