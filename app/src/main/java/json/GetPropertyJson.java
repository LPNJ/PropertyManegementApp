package json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetPropertyJson {

    @JsonProperty("userId")
    public String mUserId;
    @JsonProperty("assetId")
    public Integer mAssetId;
    @JsonProperty("data")
    public String mProperty;

    String mUserID;
    int mControlNumber;
    String mData;

    public GetPropertyJson() {

    }

    public GetPropertyJson(String userID, int mControlNumber, String mData) {
        mUserID = userID;
        mAssetId = mControlNumber;
        mProperty = mData;
    }

    public String getUserId() {
        return mUserID;
    }

    public int getAssetId() {
        return mAssetId;
    }

    public String getProperty() {
        return mProperty;
    }

}
