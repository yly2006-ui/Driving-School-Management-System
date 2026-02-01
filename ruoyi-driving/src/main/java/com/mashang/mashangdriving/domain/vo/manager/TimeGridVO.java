package com.mashang.mashangdriving.domain.vo.manager; // 和Entity同模块的vo包

import lombok.Data;

/**
 * 时间格子VO：给前端返回的月维度4-20点数据
 * 对应前端：小时行×日期列，白绿红三状态
 */
@Data
public class TimeGridVO {
    /** 唯一匹配键：年-月-日 时（如2026-02-01 04），后端匹配用 */
    private String timeKey;
    /** 年（前端渲染列：日期分组） */
    private Integer year;
    /** 月（前端渲染列：日期分组） */
    private Integer month;
    /** 日（前端渲染列：每列的日期） */
    private Integer day;
    /** 小时（前端渲染行：4-20点，每行行头） */
    private Integer hour;
    /** 状态【和前端约定】：0-白色(不可预约) 1-绿色(可预约) 2-红色(已预约)，默认0 */
    private Integer status = 0;
    /** 扩展字段：已预约时存预约人/备注，对应数据库person */
    private String person;
}