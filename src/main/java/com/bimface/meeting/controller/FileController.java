package com.bimface.meeting.controller;

import com.bimface.meeting.bean.Command;
import com.bimface.meeting.bean.CurrentFile;
import com.bimface.meeting.bean.GeneralResponse;
import com.bimface.meeting.bean.User;
import com.bimface.meeting.holder.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dup, 2017-11-22
 */
@Controller
public class FileController {
    private String currentFileId;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @PutMapping("file/nonce")
    @ResponseBody
    public GeneralResponse<String> changeFile(@RequestParam(value = "fileId") String fileId, HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        User user = UserHolder.userMap.get(sessionId);

        if (user == null || user.getRole() == null || user.getRole() != 1) {
            return new GeneralResponse<>("has no auth", null);
        }

        currentFileId = fileId;
        messagingTemplate.convertAndSend("/command", new Command<>("refreshFileCommand", fileId));
        return new GeneralResponse<>(fileId);
    }

    @GetMapping("file/nonce")
    @ResponseBody
    public GeneralResponse<CurrentFile> currentFile(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        User user = UserHolder.userMap.get(sessionId);
        if (user == null || user.getRole() == null) {
            return new GeneralResponse<>("has no auth", null);
        }

        CurrentFile currentFile = new CurrentFile(currentFileId, WebSocketController.previousCameraStatus);
        return new GeneralResponse<>(currentFile);
    }
}
