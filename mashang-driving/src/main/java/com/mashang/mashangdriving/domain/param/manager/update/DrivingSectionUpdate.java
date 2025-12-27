package com.mashang.mashangdriving.domain.param.manager.update;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingSectionUpdate {

    private Long sectionId;
    private String sectionName;
    private String sectionSort;
    private Long chapterId;
    private String sectionExplain;
    private String picture;
}
