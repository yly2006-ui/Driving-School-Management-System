package com.mashang.mashangdriving.domain.param.manager.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(description = "学员参加预约参数")
public class CreateStudentAppointment {

    @ApiModelProperty(value = "科目id",required = true)
    @NotNull(message = "科目id不能为空")
    private Long subjectId;

    @ApiModelProperty(value = "教练id")
    @NotNull(message = "教练id不能为空")
    private Long instructorId;

    @ApiModelProperty(value = "预约开始时间(yyyy-MM-dd HH:mm)")
    @NotNull(message = "预约开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "预约结束时间")
    @NotNull(message = "预约结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "场地id")
    @NotNull(message = "场地id不能为空")
    private Long locationId;
}
