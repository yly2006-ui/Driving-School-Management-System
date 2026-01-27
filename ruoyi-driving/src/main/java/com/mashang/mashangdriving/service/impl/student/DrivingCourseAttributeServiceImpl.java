package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingCourseAttribute;
import com.mashang.mashangdriving.domain.vo.student.DrivingCourseAttributeVO;
import com.mashang.mashangdriving.domain.vo.student.DrivingCourseContextVo;
import com.mashang.mashangdriving.domain.vo.student.DrivingCourseLableVo;
import com.mashang.mashangdriving.mapper.student.DrivingAttributeUseridMapper;
import com.mashang.mashangdriving.mapper.student.DrivingCourseAttributeMapper;
import com.mashang.mashangdriving.service.student.IDrivingCourseAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrivingCourseAttributeServiceImpl extends ServiceImpl<DrivingCourseAttributeMapper, DrivingCourseAttribute> implements IDrivingCourseAttributeService {
    @Autowired
    private DrivingCourseAttributeMapper drivingCourseAttributeMapper;
    @Autowired
    private DrivingAttributeUseridMapper drivingAttributeUseridMapper;
    @Override
    public List<DrivingCourseAttributeVO> selectByCourseId(Long attributeId,Long userId) {

        List<DrivingCourseAttributeVO> drivingCourseAttributeVOS = drivingCourseAttributeMapper.selectByCourseId(attributeId);
        List<DrivingCourseAttributeVO> drivingCourseAttributeVOS1 = drivingCourseAttributeMapper.countFinish(attributeId,
                userId);


        Long total = drivingCourseAttributeMapper.total(attributeId);
//        System.out.println("查询出的课程下的总条数"+total);
//        for (DrivingCourseAttributeVO drivingCourseAttributeVO : drivingCourseAttributeVOS1) {
//            String finish = drivingCourseAttributeVO.getFinish();
//            int i = Integer.parseInt(finish);
//            int i1 = Integer.parseInt(String.valueOf(total));
//            if (i ==i1){
//                DrivingAttributeUserid drivingAttributeUserid=new DrivingAttributeUserid();
//                drivingAttributeUserid.setUserId(SecurityUtils.getUserId());
//                drivingAttributeUserid.setStatus("1");
//                drivingAttributeUserid.setAttributeId(attributeId);
//                drivingAttributeUseridMapper.insert(drivingAttributeUserid);
//            }
//        }
        String string = drivingCourseAttributeMapper.selectStudyTotal(attributeId);
//        System.out.println("课程下的总条数"+total);


        for (DrivingCourseAttributeVO drivingCourseAttributeVO : drivingCourseAttributeVOS) {

            long totalTime = 0L;
            String totalTimeStr = null;
            drivingCourseAttributeVO.setStudyPersonTotal(string);
            for (DrivingCourseLableVo drivingCourseLableVo : drivingCourseAttributeVO.getDrivingCourseLableVoList()) {
                for (DrivingCourseContextVo drivingCourseContextVo : drivingCourseLableVo.getList()) {
                    Long contentId = Long.valueOf(drivingCourseContextVo.getContentId());
//                    System.out.println("小节id"+contentId);
                    String l = drivingCourseAttributeMapper.selectFinished(contentId, userId);
//                    System.out.println("学习状态"+l);
                    if (l==null){
                        drivingCourseContextVo.setFinishStatus("2");
                    }else {

                    drivingCourseContextVo.setFinishStatus(l);}
//                    System.out.println("Content time: " + contentTime);
                    Long contentTime = drivingCourseContextVo.getContentTime();
                    totalTime = totalTime + contentTime;
                    int hours = (int) (totalTime / 60);
                    int minutes = (int) (totalTime % 60);
                    totalTimeStr = hours + "小时" + minutes + "分钟";
                }
            }
            drivingCourseAttributeVO.setTotalTime(totalTimeStr);
            drivingCourseAttributeVO.setCourseCount(total.toString());
            String finish = null;
            for (DrivingCourseAttributeVO drivingCourseAttributeVO1 : drivingCourseAttributeVOS1) {
                finish = drivingCourseAttributeVO1.getFinish();
//                System.out.println("完成数量"+finish);
            }
            drivingCourseAttributeVO.setFinish(finish);


        }

        return  drivingCourseAttributeVOS;
    }
}
