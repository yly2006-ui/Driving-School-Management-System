package com.mashang.mashangdriving.domain.param.manager.update;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingChapterUpdate {

    private Long chapterId;
    private Long courseId;
    private String chapterName;
}
