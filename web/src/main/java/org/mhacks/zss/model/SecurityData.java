package org.mhacks.zss.model;

import java.util.List;

public class SecurityData {
    private String security;
    private int sequenceNumber;
    private List<FieldData> fieldData;

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public List<FieldData> getFieldData() {
        return fieldData;
    }

    public void setFieldData(List<FieldData> fieldData) {
        this.fieldData = fieldData;
    }
}
