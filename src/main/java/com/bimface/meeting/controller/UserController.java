package com.bimface.meeting.controller;

import com.bimface.meeting.bean.Command;
import com.bimface.meeting.bean.GeneralResponse;
import com.bimface.meeting.bean.User;
import com.bimface.meeting.holder.UserHolder;
import com.bimface.meeting.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author dup, 2017-11-22
 */
@Controller
public class UserController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @PostMapping("login")
    @ResponseBody
    public GeneralResponse<User> login(@RequestBody User paramUser, HttpServletRequest request) {
        Assert.hasText(paramUser.getName(), "user name must not null");

        String sessionId = request.getSession().getId();
        User user = UserHolder.userMap.get(sessionId);
        if (user != null) {
            return new GeneralResponse<>("user has login!", user);
        }
        User newUser = new User();
        newUser.setId(IdGenerator.generateId());

        newUser.setName(paramUser.getName());
        newUser.setCreateTime(new Date());
        if (UserHolder.userMap.isEmpty()) {
            newUser.setRole(1);
            UserHolder.compereMap.put(UserHolder.COMPERE, newUser);
            messagingTemplate.convertAndSend("/command", new Command<>("refreshRoleCommand", newUser));
            messagingTemplate.convertAndSend("/systemMessage", newUser.getName() + "  成为主持人");
        } else {
            newUser.setRole(0);
        }
        UserHolder.userMap.put(sessionId, newUser);

        messagingTemplate.convertAndSend("/command", new Command<>("refreshUserCommand", UserHolder.getUserList()));
        return new GeneralResponse<>(newUser);
    }

    @GetMapping("user")
    @ResponseBody
    public GeneralResponse<User> getUser(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        return new GeneralResponse<>(UserHolder.userMap.get(sessionId));
    }


    @GetMapping("user/list")
    @ResponseBody
    public GeneralResponse<List<User>> getList() {
        return new GeneralResponse<>(UserHolder.getUserList());
    }

    @PostMapping("logout")
    @ResponseBody
    public GeneralResponse<String> logout(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        User user = UserHolder.userMap.get(sessionId);
        if (user != null) {
            if (user.getRole() == 1) {
                User compere = UserHolder.compereMap.get(UserHolder.COMPERE);
                UserHolder.compereMap.remove(UserHolder.COMPERE);
                messagingTemplate.convertAndSend("/command", new Command<>("noCompereCommand", compere));
            }
            UserHolder.userMap.remove(sessionId);
            messagingTemplate.convertAndSend("/command", new Command<>("refreshUserCommand", UserHolder.getUserList()));
        }
        return new GeneralResponse<>("");
    }

    @PutMapping("compere")
    @ResponseBody
    public GeneralResponse<User> role(@RequestParam Long id, HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        User oldUser = UserHolder.userMap.get(sessionId);
        if (oldUser == null || oldUser.getRole() == null || !oldUser.getRole().equals(1)) {
            return new GeneralResponse<>("have no auth!", null);
        }

        User newUser = getList().getData().stream().filter(p -> id.equals(p.getId())).findFirst().orElse(null);
        if (newUser == null) {
            return new GeneralResponse<>("role user not exist!", null);
        }

        oldUser.setRole(0);
        newUser.setRole(1);
        newUser.setCreateTime(new Date());
        UserHolder.compereMap.put(UserHolder.COMPERE, newUser);

        messagingTemplate.convertAndSend("/command", new Command<>("refreshRoleCommand", newUser));
        messagingTemplate.convertAndSend("/systemMessage", newUser.getName() + " 成为主持人");

        return new GeneralResponse<>(oldUser);
    }

    @PostMapping("compere")
    @ResponseBody
    public GeneralResponse<User> applyRole(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        User applyUser = UserHolder.userMap.get(sessionId);

        if (applyUser == null || applyUser.getRole().equals(1)) {
            return new GeneralResponse<>("have no auth!", null);
        }

        synchronized (this) {
            if (!UserHolder.compereMap.containsKey(UserHolder.COMPERE)) {
                applyUser.setRole(1);
                applyUser.setCreateTime(new Date());
                UserHolder.compereMap.put(UserHolder.COMPERE, applyUser);

                messagingTemplate.convertAndSend("/command", new Command<>("refreshRoleCommand", applyUser));
                messagingTemplate.convertAndSend("/systemMessage", applyUser.getName() + " 成为主持人");

                return new GeneralResponse<>("apply approved", applyUser);
            } else {
                return new GeneralResponse<>("failed to apply", null);
            }
        }
    }
}
