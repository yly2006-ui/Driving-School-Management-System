package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingChapter {

    @TableId(type = IdType.AUTO)
    private Long chapterId;
    private Long courseId;
    private String chapterName;
    private String delFlag;
}
