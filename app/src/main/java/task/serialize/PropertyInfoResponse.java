package task.serialize;

import entity.PropertyInfo;

public class PropertyInfoResponse {

    private final PropertyInfo mPropertyInfo;

    public PropertyInfoResponse(PropertyInfo mPropertyInfo) {
        this.mPropertyInfo = mPropertyInfo;
    }

    public PropertyInfo getPropertyInfo() {
        return mPropertyInfo;
    }

}
