package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class DrivingBillRecord {

    @TableId(type = IdType.AUTO)
    @Excel(name = "记录表id")
    private Long recordId;

    @Excel(name = "支付id")
    private Long payId;

    @Excel(name = "用户id")
    private Long userId;

    @Excel(name = "项目id")
    private Long chargeLtemId;

    @Excel(name = "角色id")
    private Long roleId;

    @Excel(name = "删除标志", readConverterExp = "0=存在,2=删除")
    private String delFlag;

    // 关键修改：添加 dateFormat 参数
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}