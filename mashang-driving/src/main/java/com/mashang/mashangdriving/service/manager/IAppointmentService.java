package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingAppointment;
import com.mashang.mashangdriving.domain.param.manager.create.CreateStudentAppointment;
import com.mashang.mashangdriving.domain.param.manager.query.ManagerAppointmentQuery;
import com.mashang.mashangdriving.domain.param.student.create.AddRating;
import com.mashang.mashangdriving.domain.vo.manager.ManagerAppointmentListVo;
import com.mashang.mashangdriving.domain.vo.student.ContactInstructorVo;
import com.mashang.mashangdriving.domain.vo.student.MyAppointmentDtlVo;
import com.mashang.mashangdriving.domain.vo.student.StudentAppointmentVo;
import com.mashang.mashangdriving.service.impl.student.AppointmentPeakVO;
import com.ruoyi.common.core.page.PageQuery;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IAppointmentService extends IService<DrivingAppointment> {

    //昨日未处理预约
    int countYesterdayStatusOne();

    //今日未处理所有预约
    int countOnDayStatusOne();

    //学员端提交预约
    StudentAppointmentVo createAppointment(CreateStudentAppointment createStudentAppointment);

    //获取我的所有预约
    List<MyAppointmentDtlVo> myAllAppointment(String status);

    //联系教练接口
    ContactInstructorVo getContactInstructor(Long appointmentId);

    //取消预约
    int deleteAppointment(Long appointmentId);

    //教练评分
    int createRating(AddRating addRating);

    //管理端查看所有待审核的预约
    int awaitingApproval();

    //管理端查看所有已确认的预约
    int confirmApproval();

    //管理端查看今天完成的预约
    int completedTodayApproval();

    //管理端查看已取消预约
    int cancelApproval();

    /**
     * 查询本周预约高峰时间段
     *
     * 业务说明：
     * 1. 以当前系统时间为基准，统计“本周（周一至周日）”内的预约数据
     * 2. 统计所有且未删除的预约记录
     * 3. 按【星期 + 时间段】维度进行分组统计
     * 4. 返回预约数量最多的时间段，供前端展示预约高峰
     *
     * 返回结果说明：
     * - week：周几（如：周一、周二）
     * - timeRange：预约时间段（如：09:00-11:00）
     * - count：该时间段内的预约次数
     *
     * @return 本周预约高峰时间段列表
     */
    List<AppointmentPeakVO> getWeeklyAppointmentPeaks();

    //管理端分页查询所有预约
    Page<ManagerAppointmentListVo> page(PageQuery pageQuery, ManagerAppointmentQuery managerAppointmentQuery);

    //管理端查询预约详情
    ManagerAppointmentListVo appointmentDtl(Long appointmentId);

    //管理员审批通过预约(改为已确认状态)
    int updateStatus(Long appointmentId);

    //管理员拒绝预约(删除预约)
    int managerDeleteAppointment(Long appointmentId);


}
