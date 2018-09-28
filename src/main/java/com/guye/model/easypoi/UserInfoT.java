package com.guye.model.easypoi;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
@ExcelTarget("userInfoT")
public class UserInfoT implements Serializable {

  private long user_id;
  @Excel(name = "用户名",needMerge = true)
  private String username;
  @Excel(name = "密码")
  private String password;
  @Excel(name = "姓名")
  private String name;
  @Excel(name = "密码")
  private String salt;
  @Excel(name = "状态", replace = { "可_0", "不可_1" }, suffix = "用")
  private String state;

  //一个用户对应多个角色
  @ExcelCollection(name = "角色",orderNum = "4")
  private List<SysroleT> sysroleList;

  /**
   * 密码盐,重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解
   * @return
   */
  public String getCredentialsSalt(){
    return this.username+this.salt;
  }

  public long getUser_id() {
    return user_id;
  }

  public void setUser_id(long user_id) {
    this.user_id = user_id;
  }

  public List<SysroleT> getSysroleList() {
    return sysroleList;
  }

  public void setSysroleList(List<SysroleT> sysroleList) {
    this.sysroleList = sysroleList;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }


  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

}
