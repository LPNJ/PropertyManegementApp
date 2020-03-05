package task.Request;

import entity.PropertyInfo;

public class EditPropertyRequest {

    private final String mUserId;
    
    private final int mAssetId;

    private final PropertyInfo mPropertyInfo;
    
    public EditPropertyRequest(String userId, int assetId, PropertyInfo propertyInfo) {
        mUserId = userId;
        mAssetId = assetId;
        mPropertyInfo = propertyInfo;
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
