package com.guye.service.impl;

import com.guye.dao.master.TestDao;
import com.guye.model.City;
import com.guye.model.Syspermission;
import com.guye.model.Sysrole;
import com.guye.model.UserInfo;
import com.guye.service.TestService;
import com.guye.utils.SerializationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service("testServiceImpl")
public class TestServiceImpl implements TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    private TestDao testDao;

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<City> getCityList(){
        String key = "city_" + 138;
        Jedis jedis = new Jedis();
        byte[] bs = jedis.get(key.getBytes());
        List<City> cityList = (List<City>) SerializationUtil.deserialize(bs);
        if (cityList != null){
            LOGGER.info("TestServiceImpl.getCityList() : 从缓存中获取了城市 >> " + cityList.toString());
        }else {
            cityList = testDao.getCityList();
            jedis.set(key.getBytes(),SerializationUtil.serialize(cityList));
            LOGGER.info("TestServiceImpl.getCityList() : 城市插入缓存 >> " + cityList.toString());
        }
        return cityList;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public City getCityById(int id) {
        return testDao.getCityById(id);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public int isExitUserName(String username) {
        return testDao.isExitUserName(username);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public int isExitUser(String username, String password) {
        return testDao.isExitUser(username,password);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int insertCity(City city) {
        return testDao.insertCity(city);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public UserInfo findByUserName(String username, String password) {
        UserInfo userInfo = testDao.findUserInfoByUserName(username,password);
        List<Sysrole> sysroleList = testDao.findSysRoleByUserId(username,password);
        for (Sysrole sysrole:sysroleList) {
            List<Syspermission> syspermissionList = testDao.findSysPermissionByRoleId(sysrole);
            sysrole.setSyspermissionList(syspermissionList);
        }
        userInfo.setSysroleList(sysroleList);
        return userInfo;
    }
}
