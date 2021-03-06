package com.guye.model.easypoi;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.List;

public class SyspermissionT implements Serializable {
  private Long id;
  @Excel(name = "权限名称")
  private String name;
  private String resourcetype;
  private String url;
  private String permission;
  private Long parentid;
  private String parentids;
  @Excel(name = "是否可用", replace = { "可_1", "不可_2" }, suffix = "用")
  private String available;

  //一个权限对应多个角色
  private List<SysroleT> sysroleList;

  public List<SysroleT> getSysroleList() {
    return sysroleList;
  }

  public void setSysroleList(List<SysroleT> sysroleList) {
    this.sysroleList = sysroleList;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getResourcetype() {
    return resourcetype;
  }

  public void setResourcetype(String resourcetype) {
    this.resourcetype = resourcetype;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public Long getParentid() {
    return parentid;
  }

  public void setParentid(Long parentid) {
    this.parentid = parentid;
  }

  public String getParentids() {
    return parentids;
  }

  public void setParentids(String parentids) {
    this.parentids = parentids;
  }

  public String getAvailable() {
    return available;
  }

  public void setAvailable(String available) {
    this.available = available;
  }
}
