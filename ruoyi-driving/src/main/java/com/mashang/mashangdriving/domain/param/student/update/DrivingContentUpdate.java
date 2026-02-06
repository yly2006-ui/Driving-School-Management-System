package com.mashang.mashangdriving.domain.param.student.update;

import lombok.Data;

@Data
public class DrivingContentUpdate {

    private Long contentId;
    private Long lableId;
    private String contectName;
    private String courseContect;
    private String ipstructionalVideo;
    private String contentTime;
    private String videoExplain;

}
