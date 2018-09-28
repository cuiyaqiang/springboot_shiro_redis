package com.guye.controller.html;

import com.alibaba.dubbo.config.annotation.Reference;
import com.guye.model.City;
import com.guye.service.TestService;
import com.guye.utils.ResultMsg;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
//@Api(value = "城市管理",tags = "城市管理",description = "城市接口类")
@RequestMapping("/userInfo")
public class TestController {

    @Autowired
    private TestService testService;
//    @Reference(version = "1.0.0")
//    private TestProvider testProvider;

    @GetMapping(value = "/list")
    public List<City> getCityList(){
        return testService.getCityList();
    }

    @RequestMapping(value = "/getCityById",method = RequestMethod.GET)
    public City getCityById(String id){
        return testService.getCityById(Integer.valueOf(id));
    }

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public ResultMsg insertCity(City city){
        int i = testService.insertCity(city);
        if (i != 1){
            return new ResultMsg("0","插入失败");
        }
        System.out.println("city.getId()=" + city.getId());
        return new ResultMsg("1","插入成功");
    }

    /**
     * 用户查询.
     * @return
     */
    @RequestMapping("/userList")
    @RequiresPermissions("userInfo:view")//权限管理;
    public ModelAndView userInfo(){
        return new ModelAndView("userInfo");
    }

    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")//权限管理;
    public ModelAndView userInfoAdd(){
        return new ModelAndView("userInfoAdd");
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("userInfo:del")//权限管理;
    public ModelAndView userDel(){
        return new ModelAndView("userInfoDel");
    }
}
