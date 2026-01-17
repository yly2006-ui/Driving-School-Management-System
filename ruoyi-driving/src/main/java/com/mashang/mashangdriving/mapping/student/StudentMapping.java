package com.mashang.mashangdriving.mapping.student;

import org.mapstruct.factory.Mappers;

public interface StudentMapping {

    StudentMapping INSTANCE = Mappers.getMapper(StudentMapping.class);

    //学生实体转学员端首页数据概览实体

}
