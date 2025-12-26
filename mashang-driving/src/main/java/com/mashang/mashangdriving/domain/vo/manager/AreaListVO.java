package com.mashang.mashangdriving.domain.vo.manager;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mashang.mashangdriving.domain.entity.Area;
import lombok.Data;

import java.util.List;

@Data
public class AreaListVO {

    private Long id;
    private String name;
    private List<Area> areaList;
}
