package com.mashang.mashangdriving.domain.param.student.update;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingContentUpdate {

    private Long contentId;
    private Long lableId;
    private String contectName;
    private String courseContect;
    private String ipstructionalVideo;
    private String contentTime;
}
