package com.bimface.meeting.bean;

/**
 * @author dup, 2017-12-12
 */
public class CurrentFile {
    private String currentFileId;
    private String currentStatus;

    public CurrentFile() {
    }

    public CurrentFile(String currentFileId, String currentStatus) {
        this.currentFileId = currentFileId;
        this.currentStatus = currentStatus;
    }

    public String getCurrentFileId() {
        return currentFileId;
    }

    public void setCurrentFileId(String currentFileId) {
        this.currentFileId = currentFileId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}
