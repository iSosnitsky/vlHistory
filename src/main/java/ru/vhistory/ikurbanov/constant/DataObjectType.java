package ru.vhistory.ikurbanov.constant;

public enum DataObjectType {
    IGABUSMAILOUTBOX("IGABusMailOutbox");

    private String objectTypeName;

    DataObjectType(String objectTypeName) {
        this.objectTypeName = objectTypeName;
    }

    public String getObjectTypeName() {
        return objectTypeName;
    }
}
