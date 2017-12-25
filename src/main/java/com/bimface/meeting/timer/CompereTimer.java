package com.bimface.meeting.timer;

import com.bimface.meeting.bean.Command;
import com.bimface.meeting.bean.User;
import com.bimface.meeting.holder.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 主持时间5分钟
 * <p>
 * Created by zhangh-ak on 2017/12/5.
 */
@Component("compereTimer")
public class CompereTimer {
    private static final long COMPERE_EXPIRE_TIME = 1000 * 60 * 5;//主持人默认使用时间5分钟

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    /**
     * 每五秒清除过期主持人和过期用户
     */
    @Scheduled(cron = "*/5 * * * * *")
    public void timer() {
        clearExpireCompere();
    }

    private void clearExpireCompere() {
        Date now = new Date();
        if (UserHolder.compereMap.containsKey(UserHolder.COMPERE)) {
            User compere = UserHolder.compereMap.get(UserHolder.COMPERE);
            if ((now.getTime() - compere.getCreateTime().getTime()) > COMPERE_EXPIRE_TIME) {
                compere.setRole(0);
                UserHolder.compereMap.remove(UserHolder.COMPERE);
                messagingTemplate.convertAndSend("/command", new Command<>("noCompereCommand", compere));
            }
        } else {
            messagingTemplate.convertAndSend("/command", new Command<>("noCompereCommand", null));
        }
    }
}
