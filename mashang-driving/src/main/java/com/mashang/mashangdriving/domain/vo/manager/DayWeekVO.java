package com.mashang.mashangdriving.domain.vo.manager;

import lombok.Data;

/**
 * 日期-星期映射VO（仅保留核心字段）
 */
@Data
public class DayWeekVO {
    /**
     * 日期（1-31）
     * 例：1 代表当月1号
     */
    private Integer day;

    /**
     * 星期中文名称
     * 例：周四、周五、周日
     */
    private String weekName;
}