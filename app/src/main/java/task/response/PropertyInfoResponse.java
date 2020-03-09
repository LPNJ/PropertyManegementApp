package task.response;

import entity.PropertyInfo;

public class PropertyInfoResponse {

    private final PropertyInfo mPropertyInfo;

    public PropertyInfoResponse(PropertyInfo propertyInfo) {
        this.mPropertyInfo = propertyInfo;
    }

    public PropertyInfo getPropertyInfo() {
        return mPropertyInfo;
    }

}
