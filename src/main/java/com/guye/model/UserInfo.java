package com.guye.model;


import java.io.Serializable;
import java.util.List;

public class UserInfo implements Serializable {

  private long user_id;
  private String username;
  private String password;
  private String name;
  private String salt;
  private String state;

  //一个用户对应多个角色
  private List<Sysrole> sysroleList;

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

  public List<Sysrole> getSysroleList() {
    return sysroleList;
  }

  public void setSysroleList(List<Sysrole> sysroleList) {
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
