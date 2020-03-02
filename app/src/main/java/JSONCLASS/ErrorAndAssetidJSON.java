package JSONCLASS;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorAndAssetidJSON {

    @JsonProperty("error")
    public String mError;

    @JsonProperty("assetId")
    public String mControlNumber;

}
