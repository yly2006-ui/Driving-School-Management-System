package com.mashang.mashangdriving.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingContent;
import com.mashang.mashangdriving.domain.param.student.create.DrivingContentCreate;
import org.springframework.web.bind.annotation.RequestBody;

public interface IDrivingContentService extends IService<DrivingContent> {

    //新增小节
    int insertContent( DrivingContentCreate create);
}
