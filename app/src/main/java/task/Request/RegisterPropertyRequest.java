package task.Request;

import entity.PropertyInfo;

public class RegisterPropertyRequest {

    private final String mUserId;

    private final PropertyInfo mInfos;

    public RegisterPropertyRequest(String mUserId, PropertyInfo mInfos) {
        this.mUserId = mUserId;
        this.mInfos = mInfos;
    }

    public String getError() {
        return mUserId;
    }

    public PropertyInfo getInfos() {
        return mInfos;
    }

}
