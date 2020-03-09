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
    // TODO ローカル変数にm付けない
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

    public String getControlNumber() {
        return mControlNumber;
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

