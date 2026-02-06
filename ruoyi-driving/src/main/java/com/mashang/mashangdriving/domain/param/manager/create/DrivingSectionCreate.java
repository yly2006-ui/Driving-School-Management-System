package com.mashang.mashangdriving.domain.param.manager.create;


import lombok.Data;

@Data
public class DrivingSectionCreate {


    private String sectionName;
    private Integer  sectionSort;
    private Long chapterId;
    private String sectionExplain;
    private String picture;
    private String pictureExplain;
}
