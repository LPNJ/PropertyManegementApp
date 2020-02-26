package task.Request;

import entity.PropertyInfo;

public class EditPropertyRequest {

    private final String mUserId;

    private final int mAssetId;

    private final PropertyInfo mPropertyInfo;

    public EditPropertyRequest(String mUserId, int mAssetId, PropertyInfo mPropertyInfo) {
        this.mUserId = mUserId;
        this.mAssetId = mAssetId;
        this.mPropertyInfo = mPropertyInfo;
    }

    public String getUserId() {
        return mUserId;
    }

    public int getAssetId() {
        return mAssetId;
    }

    public PropertyInfo getmPropertyInfo() {
        return mPropertyInfo;
    }

}
