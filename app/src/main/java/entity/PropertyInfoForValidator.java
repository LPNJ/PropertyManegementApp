package entity;

public class PropertyInfoForValidator {

    private final String mLocation;
    private final String mProductName;

    public PropertyInfoForValidator(String mLocation,String mProductName) {
        this.mLocation = mLocation;
        this.mProductName = mProductName;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getProductName() {
        return mProductName;
    }

}
