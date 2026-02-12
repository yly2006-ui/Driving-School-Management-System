package com.mashang.mashangdriving.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingContent;
import com.mashang.mashangdriving.domain.param.student.create.DrivingContentCreate;
import com.mashang.mashangdriving.domain.vo.student.DrivingCourseAndContentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

public interface IDrivingContentService extends IService<DrivingContent> {

    //新增小节
    int insertContent( DrivingContentCreate create);

    // 新增：查询详情+上/下节ID
    DrivingCourseAndContentVo selectContentWithPrevNext(@Param("contentId") Long contentId);
}
