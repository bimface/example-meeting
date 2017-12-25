package com.bimface.meeting.bean;

public class Message {
    private User user;
    private String msg;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "user=" + user +
                ", msg='" + msg + '\'' +
                '}';
    }
}
