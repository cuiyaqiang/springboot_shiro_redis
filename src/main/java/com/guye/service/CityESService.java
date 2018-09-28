
package com.guye.service;

import com.guye.model.City;

import java.util.List;

/**
 * 城市 ES 业务接口类
 *
 */
public interface CityESService {

    /**
     * 搜索词搜索，分页返回城市信息
     *
     * @param pageNumber 当前页码
     * @param pageSize 每页大小
     * @param searchContent 搜索内容
     * @return
     */
    List<City> searchCity(Integer pageNumber, Integer pageSize, String searchContent);
}