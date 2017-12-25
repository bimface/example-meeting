package com.bimface.meeting.bean;

import java.util.Date;

/**
 * @author dup, 2017-11-24
 */
public class User {
    private Long id;
    private String name;
    private Integer role;   //用户角色，1表示主持人，0表示观众
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
