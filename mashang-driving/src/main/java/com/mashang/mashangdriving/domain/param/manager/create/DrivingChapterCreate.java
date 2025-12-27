package com.mashang.mashangdriving.domain.param.manager.create;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingChapterCreate {

    private Long courseId;
    private String chapterName;
}
