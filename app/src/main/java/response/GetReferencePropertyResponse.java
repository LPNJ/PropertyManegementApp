package response;

import json.PropertyInfoJson;

public class GetReferencePropertyResponse {

    private final String mError;

    private final PropertyInfoJson mInfo;

    public GetReferencePropertyResponse(String error, PropertyInfoJson info) {
        this.mError = error;
        this.mInfo = info;
    }

    public String getError() {
        return mError;
    }

    public PropertyInfoJson getInfo() {
        return mInfo;
    }

}
