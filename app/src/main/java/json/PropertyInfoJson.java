package json;

import com.fasterxml.jackson.annotation.JsonProperty;

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


    public void setPropertyManager(String mPropertyManager) {
        this.mPropertyManager = mPropertyManager;
    }

    public void setPropertyUser(String mPropertyUser) {
        this.mPropertyUser = mPropertyUser;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public void setControlNumber(String mControlNumber) {
        this.mControlNumber = mControlNumber;
    }

    public void setProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public void setPurchaseCategory(String mPurchaseCategory) {
        this.mPurchaseCategory = mPurchaseCategory;
    }

    public void setPropertyCategory(String mPropertyCategory) {
        this.mPropertyCategory = mPropertyCategory;
    }

    public void setComplement(String mComplement) {
        this.mComplement = mComplement;
    }
}
