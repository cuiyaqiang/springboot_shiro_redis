package com.guye.dao.master;

import com.guye.model.easypoi.SyspermissionT;
import com.guye.model.easypoi.SysroleT;
import com.guye.model.easypoi.UserInfoT;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by suneee on 2018/5/15.
 */
@Repository
public interface PoiDao {

    List<UserInfoT> getUserList();

    List<SysroleT> getRoleList();

    List<SyspermissionT> getPermissionList();
}
