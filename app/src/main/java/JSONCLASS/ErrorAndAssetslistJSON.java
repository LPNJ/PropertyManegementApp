package jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import task.response.GetPropertyEntity;

// TODO キャメルケース
public class ErrorAndAssetsListJson {

    @JsonProperty("error")
    public String mError;

    @JsonProperty("assetList")
    public ArrayList<GetPropertyEntity> mProperties;

}
