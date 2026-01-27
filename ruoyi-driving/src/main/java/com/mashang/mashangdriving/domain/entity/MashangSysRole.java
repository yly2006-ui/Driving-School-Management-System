package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("MashangSysRole")
@Data
@TableName("sys_role")
public class MashangSysRole  {
  @TableId(value = "role_id", type = IdType.AUTO)
  private long roleId;
  private String roleName;
  private String roleKey;
  private long roleSort;
  private String dataScope;
  private long menuCheckStrictly;
  private long deptCheckStrictly;
  private String status;
  private String delFlag;
  private String createBy;
  private java.sql.Timestamp createTime;
  private String updateBy;
  private java.sql.Timestamp updateTime;
  private String remark;


}
