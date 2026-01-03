package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingAppointment;
import com.mashang.mashangdriving.domain.param.manager.create.CreateStudentAppointment;
import com.mashang.mashangdriving.domain.vo.manager.ManagerAppointmentListVo;
import com.mashang.mashangdriving.domain.vo.student.StudentAppointmentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface AppointmentMapper extends BaseMapper<DrivingAppointment> {

    StudentAppointmentVo appointmento();

    //管理端查看已取消预约
    Long cancelApproval();

    //分页插件会自动把这些对象组成一个 List，放到 Page 里面
    Page<ManagerAppointmentListVo> page(@Param("page") Page<ManagerAppointmentListVo> page,
                                        @Param("ew") Wrapper<ManagerAppointmentListVo> wrapper);

    ManagerAppointmentListVo appointmentDtl(Long appointmentId);
}
