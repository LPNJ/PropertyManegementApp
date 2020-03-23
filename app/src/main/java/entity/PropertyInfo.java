package entity;

public class PropertyInfo {

    private final String mPropertyManager;
    private final String mPropertyUser;
    private final String mLocation;
    private final String mControlNumber;
    private final String mProductName;
    private final String mPurchaseCategory;
    private final String mPropertyCategory;
    private final String mComplement;

    /**
     * コンストラクタ
     */
    public PropertyInfo(String propertyManager, String propertyUser, String location, String controlNumber, String productName, String purchaseCategory, String propertyCategory, String complement) {
        this.mPropertyManager = propertyManager;
        this.mPropertyUser = propertyUser;
        this.mLocation = location;
        this.mControlNumber = controlNumber;
        this.mProductName = productName;
        this.mPurchaseCategory = purchaseCategory;
        this.mPropertyCategory = propertyCategory;
        this.mComplement = complement;
    }

    public String getPropertyManager() {
        return mPropertyManager;
    }

    public String getPropertyUser() {
        return mPropertyUser;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getControlNumber() {
        return mControlNumber;
    }

    public String getProductName() {
        return mProductName;
    }

    public String getPurchaseCategory() {
        return mPurchaseCategory;
    }

    public String getPropertyCategory() {
        return mPropertyCategory;
    }

    public String getComplement() {
        return mComplement;
    }

    @Override
    public String toString() {
        return "{" +
                "\"mPropertyManager\":" + "\""+mPropertyManager + "\"" +
                ","+"\"mPropertyUser\":" + "\""+mPropertyUser + "\"" +
                ","+"\"mLocation\":" + "\""+mLocation + "\"" +
                ","+"\"mControlNumber\":" + "\""+mControlNumber + "\"" +
                ","+"\"mProductName\":" + "\""+mProductName + "\"" +
                ","+"\"mPurchaseCategory\":" +"\""+ mPurchaseCategory + "\"" +
                ","+"\"mPropertyCategory\":" + "\""+mPropertyCategory + "\"" +
                ","+"\"mComplement\":" + "\""+mComplement + "\"" +
                '}';
    }

}

