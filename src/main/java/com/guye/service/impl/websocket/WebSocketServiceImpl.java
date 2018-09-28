package com.guye.service.impl.websocket;

import com.guye.model.websocket.WiselyResponse;
import com.guye.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by suneee on 2018/5/9.
 */
@Service
public class WebSocketServiceImpl {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 广播
     * 发给所有在线用户
     *
     * @param msg
     */
    public void sendMsg(WiselyResponse msg) {
        template.convertAndSend(Constant.PRODUCERPATH, msg);
    }

    /**
     * 发送给指定用户
     * @param userInfos
     * @param msg
     */
    public void send2Users(List<String> userInfos, WiselyResponse msg) {
        userInfos.forEach(username -> {
            template.convertAndSendToUser(username, Constant.P2PPUSHPATH, msg);
        });
    }
}
