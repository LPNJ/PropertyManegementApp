package task.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import entity.PropertyInfo;

public class GetPropertyEntity {

    @JsonProperty("userId")
    private String mUserId;
    @JsonProperty("assetId")
    private Integer mAssetId;
    @JsonProperty("data")
    private String mProperty;

    public String getUserId() {
        return mUserId;
    }

    public Integer getAssetId() {
        return mAssetId;
    }

    public String getProperty() {
        return mProperty;
    }


}
