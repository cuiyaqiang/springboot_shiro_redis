package com.guye.controller.html.websocket;

import com.google.common.collect.Lists;
import com.guye.model.websocket.WiselyRequest;
import com.guye.model.websocket.WiselyResponse;
import com.guye.service.impl.websocket.WebSocketServiceImpl;
import com.guye.utils.Constant;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by suneee on 2018/5/9.
 */
@Controller
public class WsController {

    @Resource
    WebSocketServiceImpl webSocketService;

    @MessageMapping(Constant.FORETOSERVERPATH)//@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
    @SendTo(Constant.PRODUCERPATH)//如果服务器接受到了消息，就会对订阅了@SendTo括号中的地址传送消息。
    public WiselyResponse say(WiselyRequest message) throws Exception {
        List<String> users = Lists.newArrayList();
        users.add("1");//此处写死只是为了方便测试,此值需要对应页面中订阅个人消息的userId
        webSocketService.send2Users(users, new WiselyResponse("admin hello"));

        return new WiselyResponse("Welcome, " + message.getName() + "!");
    }
}
