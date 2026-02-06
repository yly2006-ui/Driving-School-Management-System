package com.mashang.mashangdriving.domain.param.manager.update;

import lombok.Data;

@Data
public class DrivingSectionUpdate {

    private Long sectionId;
    private String sectionName;
    private String sectionSort;
    private Long chapterId;
    private String sectionExplain;
    private String picture;
    private String pictureExplain;
}
