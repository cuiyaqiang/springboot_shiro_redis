package com.guye.controller.html;

import com.alibaba.fastjson.JSONObject;
import com.guye.utils.JacksonUtil;
import com.guye.utils.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

@RestController
//@Api(value = "城市管理",tags = "城市管理",description = "城市接口类")
public class HomeController {

    @RequestMapping(value = "/")
    public ModelAndView index(){
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/webSocketTest")
    public ModelAndView webSocketTest(){
        return new ModelAndView("webSocketTest");
    }

    @RequestMapping(value = "/layui")
    public ModelAndView layui(){
        return new ModelAndView("layui");
    }

    @RequestMapping(value = "/fileUpAndDown")
    public ModelAndView fileUpAndDown(){
        return new ModelAndView("fileUpload");
    }

    @RequestMapping(value = "/userInfoAdd")
    public ModelAndView userInfoAdd(){
        return new ModelAndView("userInfoAdd");
    }

    @RequestMapping(value = "/userInfoDel")
    public ModelAndView userInfoDel(){
        return new ModelAndView("userInfoDel");
    }

    @RequestMapping(value = "/welcome")
    public ModelAndView welcome(){
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ResultMsg login(String username, String password, Boolean rememberMe) {
        System.out.println("HomeController.login()");
//        JSONObject jsonObject = new JSONObject();
        ResultMsg msg = new ResultMsg();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        if (rememberMe != null){
            token.setRememberMe(rememberMe);
        }
        try {
            subject.login(token);
            msg.setCode("1");
            msg.setMsg("登录成功");
//            jsonObject.put("token", subject.getSession().getId());
//            jsonObject.put("msg", "登录成功");
        } catch (IncorrectCredentialsException e) {
            msg.setCode("0");
            msg.setMsg("密码错误");
//            jsonObject.put("msg", "密码错误");
        } catch (LockedAccountException e) {
            msg.setCode("0");
            msg.setMsg("登录失败，该用户已被冻结");
//            jsonObject.put("msg", "登录失败，该用户已被冻结");
        } catch (AuthenticationException e) {
            msg.setCode("0");
            msg.setMsg("该用户不存在");
//            jsonObject.put("msg", "该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
//        if (subject.isAuthenticated()){
//            Object object =  subject.getPrincipal();
//            System.out.println("2222");
//            System.out.println(object);
//            return new ModelAndView("login");
//        } else {
//            return new ModelAndView("index");
//        }
        /*if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> "+exception;
                System.out.println("else -- >" + exception);
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return map;*/
    }

    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @RequestMapping(value = "/unauth")
    @ResponseBody
    public Object unauth() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1000000");
        map.put("msg", "未登录");
        return map;
    }

}
