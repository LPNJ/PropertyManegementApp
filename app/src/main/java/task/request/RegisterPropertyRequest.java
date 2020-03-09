package task.request;

import entity.PropertyInfo;

public class RegisterPropertyRequest {

    private final String mUserId;

    private final PropertyInfo mInfos;

    public RegisterPropertyRequest(String userId, PropertyInfo infos) {
        this.mUserId = userId;
        this.mInfos = infos;
    }

    public String getError() {
        return mUserId;
    }

    public PropertyInfo getInfos() {
        return mInfos;
    }

}
