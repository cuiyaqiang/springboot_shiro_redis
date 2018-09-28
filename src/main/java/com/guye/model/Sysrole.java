package com.guye.model;

import java.io.Serializable;
import java.util.List;

public class Sysrole implements Serializable {
  private Long id;
  private String role;
  private String description;
  private String available;

  //一个角色对应多个用户
  private List<UserInfo> userInfoList;
  //一个角色对应多个权限
  private List<Syspermission> syspermissionList;

  public List<Syspermission> getSyspermissionList() {
    return syspermissionList;
  }

  public void setSyspermissionList(List<Syspermission> syspermissionList) {
    this.syspermissionList = syspermissionList;
  }

  public List<UserInfo> getUserInfoList() {
    return userInfoList;
  }

  public void setUserInfoList(List<UserInfo> userInfoList) {
    this.userInfoList = userInfoList;
  }

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
