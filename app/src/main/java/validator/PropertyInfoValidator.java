package validator;

import entity.PropertyInfoForValidator;

public class PropertyInfoValidator implements Validator<PropertyInfoForValidator>{

    public static final int ERROR_NOT_INPUT = 1;

    @Override
    public int validate(PropertyInfoForValidator data) {
        if (data == null) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " data is null.");
        }
        // 入力がない
        if (data.getLocation() == null || data.getProductName() == null || data.getLocation().isEmpty() || data.getProductName().isEmpty()) {
            return ERROR_NOT_INPUT;
        }
        return 0;
    }
}
