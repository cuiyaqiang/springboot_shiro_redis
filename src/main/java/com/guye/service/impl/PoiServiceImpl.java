package com.guye.service.impl;

import com.guye.dao.master.PoiDao;
import com.guye.model.easypoi.SyspermissionT;
import com.guye.model.easypoi.SysroleT;
import com.guye.model.easypoi.UserInfoT;
import com.guye.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by suneee on 2018/5/15.
 */
@Service("poiServiceImpl")
public class PoiServiceImpl implements PoiService {

    @Autowired
    private PoiDao poiDao;

    @Override
    public List<UserInfoT> getUserList() {
        List<UserInfoT> userList = poiDao.getUserList();
        List<SysroleT> roleList = poiDao.getRoleList();
//        List<SyspermissionT> permissionList = poiDao.getPermissionList();
        for (UserInfoT userInfo: userList){
//            for (SysroleT sysrole: roleList){
//                sysrole.setSyspermissionList(permissionList);
//            }
            userInfo.setSysroleList(roleList);
        }
        return userList;
    }
}
