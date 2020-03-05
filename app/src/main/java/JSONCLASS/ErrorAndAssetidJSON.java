package jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ErrorAndAssetidJSON {

    @JsonProperty("error")
    public String mError;

    @JsonProperty("assetId")
    public String mControlNumber;

}
