package json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class ErrorAndAssetsListJson {

    @JsonProperty("error")
    public String mError;

    @JsonProperty("assetList")
    public ArrayList<GetPropertyJson> mProperties;

}