package json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import task.response.GetPropertyEntity;

// TODO ファイル名がキャメルケースになってない
public class ErrorAndAssetsListJson {

    @JsonProperty("error")
    public String mError;

    @JsonProperty("assetList")
    public ArrayList<GetPropertyEntity> mProperties;

}