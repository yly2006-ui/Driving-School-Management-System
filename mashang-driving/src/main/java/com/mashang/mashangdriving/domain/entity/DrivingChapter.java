package com.mashang.mashangdriving.domain.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

@Data
public class DrivingChapter {

    @TableId(type = IdType.AUTO)
    private Long chapterId;
    private Long courseId;
    private String chapterName;
    private String delFlag;
}
