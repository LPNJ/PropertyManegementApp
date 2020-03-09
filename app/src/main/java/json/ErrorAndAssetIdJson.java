package json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorAndAssetIdJson {

    @JsonProperty("error")
    public String mError;

    @JsonProperty("assetId")
    public String mControlNumber;

}
