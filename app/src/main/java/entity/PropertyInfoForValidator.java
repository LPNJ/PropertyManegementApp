package entity;

public class PropertyInfoForValidator {

    private final String mLocation;
    private final String mProductName;

    public PropertyInfoForValidator(String location,String productName) {
        this.mLocation = location;
        this.mProductName = productName;
    }

    public String getLocation() {
        return mLocation;
    }
    
    public String getProductName() {
        return mProductName;
    }

}
