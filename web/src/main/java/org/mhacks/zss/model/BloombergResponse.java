package org.mhacks.zss.model;

import java.util.List;

public class BloombergResponse {

    private List<BloombergData> data;
    private int status;
    private String message;

    public List<BloombergData> getData() {
        return data;
    }

    public void setData(List<BloombergData> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
