package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Area {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentId;
    private String name;
    private String administrativeRegion;
    private String province;
    private String city;
    private String county;
    private String zoneCode;
    private String postcode;
    private String forShort;
    private String spell;
    private String logogram;
    private String initial;
    private String englishName;
    private String longitude;
    private String latitude;
    private String parentPath;
    private Long level;
    private String fullPath;
}
