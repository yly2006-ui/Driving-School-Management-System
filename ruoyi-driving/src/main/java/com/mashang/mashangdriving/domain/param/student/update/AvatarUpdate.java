package com.mashang.mashangdriving.domain.param.student.update;

import com.openai.core.MultipartField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AvatarUpdate {
    @ApiModelProperty("学生id")
    private Long studentId;

    @ApiModelProperty("头像")
    private MultipartFile avatar;
}
