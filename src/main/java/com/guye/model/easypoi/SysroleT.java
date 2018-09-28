package com.guye.model.easypoi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class SysroleT implements Serializable {
  private Long id;
  @Excel(name = "角色名称",needMerge = true, isImportField = "true_st")
  private String role;
  @Excel(name = "角色描述", isImportField = "true_st")
  private String description;
  @Excel(name = "是否可用", replace = { "可_0", "不可_1" }, suffix = "用", isImportField = "true_st")
  private String available;

  //一个角色对应多个用户
//  private List<UserInfoT> userInfoList;
  //一个角色对应多个权限
//  @ExcelCollection(name = "权限",orderNum = "2")
//  private List<SyspermissionT> syspermissionList;

//  public List<SyspermissionT> getSyspermissionList() {
//    return syspermissionList;
//  }

//  public void setSyspermissionList(List<SyspermissionT> syspermissionList) {
//    this.syspermissionList = syspermissionList;
//  }
//
//  public List<UserInfoT> getUserInfoList() {
//    return userInfoList;
//  }
//
//  public void setUserInfoList(List<UserInfoT> userInfoList) {
//    this.userInfoList = userInfoList;
//  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAvailable() {
    return available;
  }

  public void setAvailable(String available) {
    this.available = available;
  }
}
