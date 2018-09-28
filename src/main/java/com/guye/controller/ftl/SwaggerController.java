package com.guye.controller.ftl;

import com.guye.model.City;
import com.guye.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by suneee on 2018/5/7.
 */
@Controller
//@Api(value = "城市管理",tags = "城市管理0",description = "城市接口类")
public class SwaggerController {

    @Autowired
    private CityService cityService;

//    @ApiOperation(value = "城市管理", notes = "城市管理1", produces = "application/json")
    @RequestMapping(value = "/api/city2/{id}", method = RequestMethod.GET)
    public String findOneeCity(Model model, @PathVariable("id") Long id) {
        model.addAttribute("city", cityService.findCityById(id));
        return "city";
    }

//    @ApiOperation(value = "城市管理", notes = "城市管理2", produces = "application/json")
    @RequestMapping(value = "/api/city2", method = RequestMethod.GET)
    public String findAlleCity(Model model) {
        List<City> cityList = cityService.findAllCity();
        model.addAttribute("cityList",cityList);
        return "cityList";
    }
}
