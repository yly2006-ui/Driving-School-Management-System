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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class DrivingCourseAttributeServiceImpl extends ServiceImpl<DrivingCourseAttributeMapper, DrivingCourseAttribute> implements IDrivingCourseAttributeService {
    @Autowired
    private DrivingCourseAttributeMapper drivingCourseAttributeMapper;
    @Autowired
    private DrivingAttributeUseridMapper drivingAttributeUseridMapper;
    @Override
    public DrivingCourseAttributeVO selectByCourseId(Long attributeId,Long userId) {

        DrivingCourseAttributeVO drivingCourseAttributeVOS = drivingCourseAttributeMapper.selectByCourseId(attributeId);
        List<DrivingCourseAttributeVO> drivingCourseAttributeVOS1 = drivingCourseAttributeMapper.countFinish(attributeId,
                userId);


        Long total = drivingCourseAttributeMapper.total(attributeId);
        if (!(total >0)){total= 0L;
        }
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
        if (string==null){string= String.valueOf(0);
        }


            long totalTime = 0L;
            String totalTimeStr = String.valueOf(0);
            drivingCourseAttributeVOS.setStudyPersonTotal(string);
        List<DrivingCourseLableVo> drivingCourseLableVoList = drivingCourseAttributeVOS.getDrivingCourseLableVoList();
        for (DrivingCourseLableVo drivingCourseLableVo : drivingCourseLableVoList) {
        for (DrivingCourseContextVo drivingCourseContextVo : drivingCourseLableVo.getList()) {
                    Long contentId = Long.valueOf(drivingCourseContextVo.getContentId());
//                    System.out.println("小节id"+contentId);
                    String l = drivingCourseAttributeMapper.selectFinished(contentId, userId);
//                    System.out.println("学习状态"+l);
                    if (l == null) {
                        drivingCourseContextVo.setFinishStatus("2");
                    } else {

                        drivingCourseContextVo.setFinishStatus(l);
                    }
//                    System.out.println("Content time: " + contentTime);
                    Long contentTime = drivingCourseContextVo.getContentTime();
                    totalTime = totalTime + contentTime;
                    int hours = (int) (totalTime / 60);
                    int minutes = (int) (totalTime % 60);
                    totalTimeStr = hours + "小时" + minutes + "分钟";
                }
            }
        drivingCourseAttributeVOS.setTotalTime(totalTimeStr);
        drivingCourseAttributeVOS.setCourseCount(total.toString());
            String finish = null;
            for (DrivingCourseAttributeVO drivingCourseAttributeVO1 : drivingCourseAttributeVOS1) {
                finish = drivingCourseAttributeVO1.getFinish();
                System.out.println("完成数量"+finish);
                if (finish == null) {
                    finish = String.valueOf(0);
                }
            }
        drivingCourseAttributeVOS.setFinish(finish);

            if (total == 0L){
                drivingCourseAttributeVOS.setPercentage("0%");
            }else {

            String bigDecimal=new BigDecimal(finish).divide(new BigDecimal(total),3, RoundingMode.HALF_UP).
                    multiply(new BigDecimal(100)).setScale(1,RoundingMode.HALF_UP)+"%";
                drivingCourseAttributeVOS.setPercentage(bigDecimal);}




        return  drivingCourseAttributeVOS;
    }
}
