package org.mhacks.zss.model;

public class FieldData {
    private String date;
    private double LAST_PRICE;
    private double OPEN;
    private long VOLUME;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLAST_PRICE() {
        return LAST_PRICE;
    }

    public void setLAST_PRICE(double LAST_PRICE) {
        this.LAST_PRICE = LAST_PRICE;
    }

    public double getOPEN() {
        return OPEN;
    }

    public void setOPEN(double OPEN) {
        this.OPEN = OPEN;
    }

    public long getVOLUME() {
        return VOLUME;
    }

    public void setVOLUME(long VOLUME) {
        this.VOLUME = VOLUME;
    }
}
