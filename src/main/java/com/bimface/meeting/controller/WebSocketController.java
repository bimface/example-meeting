package com.bimface.meeting.controller;

import com.alibaba.fastjson.JSON;
import com.bimface.meeting.bean.Message;
import com.bimface.meeting.bean.User;
import com.bimface.meeting.holder.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public static String previousCameraStatus;

    @MessageMapping("/cameraStatus")
    public void cameraStatus(String cameraStatus, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        User user = UserHolder.userMap.get(sessionId);
        if (user != null && user.getRole() != null && user.getRole().equals(1) && !cameraStatus.equals(previousCameraStatus)){
            previousCameraStatus = cameraStatus;
            messagingTemplate.convertAndSend("/status", cameraStatus);
        }
    }

    @MessageMapping("/message")
    public void processMessageFromClient(String message, SimpMessageHeaderAccessor  headerAccessor) {
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        User user = UserHolder.userMap.get(sessionId);
        if (user != null && user.getRole() != null){
            Message messageBean = new Message();
            messageBean.setUser(user);
            messageBean.setMsg(message);
            String messageStr = JSON.toJSONString(messageBean);
            messagingTemplate.convertAndSend("/msg", messageStr);
        }
    }
}
