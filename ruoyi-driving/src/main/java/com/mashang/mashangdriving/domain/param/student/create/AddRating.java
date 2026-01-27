package com.mashang.mashangdriving.domain.param.student.create;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddRating {

    @ApiModelProperty("评分")
    @NotNull(message = "评分不能空")
    private double score;

    @ApiModelProperty("评价内容")
    @NotBlank(message = "评价内容不能为空")
    private String contanct;

    @ApiModelProperty("预约id")
    @NotNull(message = "预约id不能为空")
    private Long appointmentId;
}
