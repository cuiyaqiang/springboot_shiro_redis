package com.guye.service.impl;

import com.guye.dao.cluster.PictureDao;
import com.guye.dao.master.CityDao;
import com.guye.model.City;
import com.guye.model.Pic;
import com.guye.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 城市业务逻辑实现类
 *
 * Created by bysocket on 07/02/2017.
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Autowired
    private PictureDao pictureDao;

    @Override
    public List<City> findAllCity(){
        return cityDao.findAllCity();
    }

    @Override
    public City findCityById(Long id) {
        City city = cityDao.findById(id);
        Pic pic = pictureDao.findOnePic(1);
        city.setPic(pic);
        return city;
    }

    @Override
    public Long saveCity(City city) {
        return cityDao.saveCity(city);
    }

    @Override
    public Long updateCity(City city) {
        return cityDao.updateCity(city);
    }

    @Override
    public Long deleteCity(Long id) {
        return cityDao.deleteCity(id);
    }

}
