package com.guye.dao.master;

import com.guye.model.City;
import com.guye.model.Syspermission;
import com.guye.model.Sysrole;
import com.guye.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDao {

    List<City> getCityList();

    int isExitUserName(String username);

    int isExitUser(@Param("username") String username, @Param("password") String password);

    int insertCity(City city);

    UserInfo findUserInfoByUserName(@Param("username") String username, @Param("password") String password);

    List<Sysrole> findSysRoleByUserId(@Param("username") String username, @Param("password") String password);

    List<Syspermission> findSysPermissionByRoleId(Sysrole sysrole);

    City getCityById(int id);
}
