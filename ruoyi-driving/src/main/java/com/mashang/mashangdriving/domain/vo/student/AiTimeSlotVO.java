package com.mashang.mashangdriving.domain.vo.student;

import lombok.Data;

import java.util.Date;

@Data
public class AiTimeSlotVO {
    /** 例如：09:00-11:00 */
    private String timeSlot;

    /** 实际开始时间 */
    private Date startTime;

    /** 实际结束时间 */
    private Date endTime;
}
