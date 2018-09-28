package com.guye.controller.ftl;

import com.guye.model.City;
import com.guye.service.CityESService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by suneee on 2018/5/4.
 */
@Controller
//@Api(value = "城市管理",tags = "城市管理",description = "城市接口类")
public class CityESController {

    @Autowired
    private CityESService cityESService;

    /**
     * 搜索词搜索，分页返回城市信息
     *
     * @param pageNumber 当前页码
     * @param pageSize 每页大小
     * @param searchContent 搜索内容
     * @return
     */
    @RequestMapping(value = "/api/city/search",method = RequestMethod.GET)
    public String searchCity(Model model, Integer pageNumber, Integer pageSize, String searchContent){
        List<City> cityList = cityESService.searchCity(pageNumber, pageSize,searchContent);
        model.addAttribute("cityList",cityList);
        return "cityList";
    }
}
