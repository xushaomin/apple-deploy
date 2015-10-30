package com.appleframework.deploy.entity;

import java.io.Serializable;

public class RecordWithBLOBs extends Record implements Serializable {
    private String command;

    private String memo;

    private static final long serialVersionUID = 1L;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command == null ? null : command.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}