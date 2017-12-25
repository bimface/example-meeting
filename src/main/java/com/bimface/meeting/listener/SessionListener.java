package com.bimface.meeting.listener;

import com.bimface.meeting.bean.Command;
import com.bimface.meeting.bean.User;
import com.bimface.meeting.holder.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

@Component
public class SessionListener implements HttpSessionListener {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
//        httpSessionEvent.getSession().setMaxInactiveInterval(10);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        String sessionId = httpSessionEvent.getSession().getId();
        if (UserHolder.userMap.keySet().contains(sessionId)) {
            User user = UserHolder.userMap.get(sessionId);
            System.out.println("user map contains this session id.");
            System.out.println("expire time:" + (new Date().getTime() - user.getCreateTime().getTime()));
            UserHolder.userMap.remove(sessionId);
            messagingTemplate.convertAndSend("/command", new Command<>("refreshUserCommand", UserHolder.getUserList()));
        }
    }

}