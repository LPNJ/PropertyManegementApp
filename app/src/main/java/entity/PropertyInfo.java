package entity;

public class PropertyInfo {

    private static final String TAG = "PropertyInfo";
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
    public PropertyInfo(String mPropertyManager, String mPropertyUser, String mLocation, String mControlNumber, String mProductName, String mPurchaseCategory, String mPropertyCategory, String mComplement) {
        this.mPropertyManager = mPropertyManager;
        this.mPropertyUser = mPropertyUser;
        this.mLocation = mLocation;
        this.mControlNumber = mControlNumber;
        this.mProductName = mProductName;
        this.mPurchaseCategory = mPurchaseCategory;
        this.mPropertyCategory = mPropertyCategory;
        this.mComplement = mComplement;
    }

    public String getControlNumber() {
        return mControlNumber;
    }

    // TODO 必要ないコードは削除、ほかのクラスも同様

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

