package json;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO ファイル名がキャメルケースになってない
public class PropertyInfoJson {

    @JsonProperty("mPropertyManager")
    public String mPropertyManager;
    @JsonProperty("mPropertyUser")
    public String mPropertyUser;
    @JsonProperty("mLocation")
    public String mLocation;
    @JsonProperty("mControlNumber")
    public String mControlNumber;
    @JsonProperty("mProductName")
    public String mProductName;
    @JsonProperty("mPurchaseCategory")
    public String mPurchaseCategory;
    @JsonProperty("mPropertyCategory")
    public String mPropertyCategory;
    @JsonProperty("mComplement")
    public String mComplement;

}
