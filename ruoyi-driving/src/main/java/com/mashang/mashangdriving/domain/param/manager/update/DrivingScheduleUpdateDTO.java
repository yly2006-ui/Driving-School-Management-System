package com.mashang.mashangdriving.domain.param.manager.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Data
public class DrivingScheduleUpdateDTO {
    @ApiModelProperty(value = "教练ID", required = true, example = "1")
    private Integer instructorId;

        @ApiModelProperty(value = "星期几：1=周一, 2=周二, ..., 7=周日", example = "1")
        private String weekDay;

        @ApiModelProperty(value = "时间段索引：0=4:00-5:00, ..., 18=22:00-23:00", example = "1,2,3,4")
        private String timeSlot;

        @ApiModelProperty(value = "状态：-1=未设置, 0=不可预约, 1=可预约", example = "1")
        private Integer status;

        public void setWeekDay(String weekDay) {
            this.weekDay = weekDay;
        }
        public void setTimeSlot(String timeSlot) {
                this.timeSlot = timeSlot;
        }


//        public void setTimeSlot(Object timeSlot) { this.timeSlot = timeSlot; }

        public void setStatus(Integer status) { this.status = status; }

    public Integer getInstructorId() { return instructorId; }
    public void setInstructorId(Integer instructorId) { this.instructorId = instructorId; }
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public List<Integer> getWeekDayList() {
        return parseIntegerList(this.weekDay);
    }
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public List<Integer> getTimeSlotList() {
        return parseIntegerList(this.timeSlot);
    }

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public List<Integer> parseIntegerList(String input) {
        List<Integer> list = new ArrayList<>();

        if (input == null) {
            return list;
        }

        // 如果是数组
//        if (input instanceof List) {
//            @SuppressWarnings("unchecked")
//            List<Object> temp = (List<Object>) input;
//            for (Object obj : temp) {
//                Integer val = convertToInteger(obj);
//                if (val != null) {
//                    list.add(val);
//                }
//            }
//        }
        // 如果是单个数字
//        else if (input instanceof Integer) {
//            list.add((Integer) input);
//        }
        // 如果是字符串（包含逗号）
        else if (input instanceof String) {
            String str = (String) input;
            if (str.contains(",")) {
                String[] parts = str.split(",");
                for (String part : parts) {
                    Integer val = convertToInteger(part);
                    if (val != null) {
                        list.add(val);
                    }
                }
            } else {
                Integer val = convertToInteger(str);
                if (val != null) {
                    list.add(val);
                }
            }
        }
        // 如果是其他数字类型
//        else if (input instanceof Number) {
//            list.add(((Number) input).intValue());
//        }

        return list;
    }

    private Integer convertToInteger(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Integer) return (Integer) obj;
        if (obj instanceof String) {
            try {
                return Integer.parseInt(((String) obj).trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return null;
    }
}
