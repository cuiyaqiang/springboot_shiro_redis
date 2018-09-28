package com.guye.service;



import com.guye.model.City;
import com.guye.model.UserInfo;

import java.util.List;

public interface TestService {

    List<City> getCityList();

    City getCityById(int id);

    int isExitUserName(String username);

    int isExitUser(String username, String password);

    int insertCity(City city);

    UserInfo findByUserName(String username, String password);
}
