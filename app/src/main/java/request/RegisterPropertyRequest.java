package request;

import entity.PropertyInfo;

public class RegisterPropertyRequest {

    private final String mUserId;

    private final PropertyInfo mInfo;

    public RegisterPropertyRequest(String userId, PropertyInfo infos) {
        this.mUserId = userId;
        this.mInfo = infos;
    }

    public String getUserId() {
        return mUserId;
    }
    
    public PropertyInfo getInfo() {
        return mInfo;
    }

}
