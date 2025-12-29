package com.mashang.mashangdriving.domain.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

@Data
public class DrivingSection {

    @TableId(type = IdType.AUTO)
    private Long sectionId;
    private String sectionName;
    private Integer sectionSort;
    private Long chapterId;
    private String sectionExplain;
    private String picture;
}
