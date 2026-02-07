package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingSection {

    @TableId(type = IdType.AUTO)
    private Long sectionId;
    private String sectionName;
    private Long chapterId;
    private String sectionExplain;
    private String picture;
}
