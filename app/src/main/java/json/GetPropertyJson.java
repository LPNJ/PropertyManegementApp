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

    public GetPropertyJson() {

    }

    public GetPropertyJson(String userID, int controlNumber, String data) {
        mUserID = userID;
        mAssetId = controlNumber;
        mProperty = data;
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
