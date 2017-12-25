package com.bimface.meeting.bean;

public class GeneralResponse<T> {
    private String code;
    private T data;

    public GeneralResponse() {
        this.code = "success";
    }

    public GeneralResponse(String status, T data){
        this.code = status;
        this.data = data;
    }

    public GeneralResponse(T data) {
        code = "success";
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}