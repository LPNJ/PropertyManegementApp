package task.Request;

import entity.PropertyInfo;

public class PropertyRegistRequest {

    private final String mUserId;

    private final PropertyInfo mPropertyInfo;

    public PropertyRegistRequest(String mUserId, PropertyInfo mPropertyInfo) {
        this.mUserId = mUserId;
        this.mPropertyInfo = mPropertyInfo;
    }

    public String getmUserId() {
        return mUserId;
    }

    public PropertyInfo getmPropertyInfo() {
        return mPropertyInfo;
    }

}
