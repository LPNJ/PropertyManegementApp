package validator;

import org.junit.Test;

import entity.PropertyInfoForValidator;

import static org.junit.Assert.assertEquals;

public class PropertyInfoValidatorTest {

    @Test
    public void validateIdSuccess() {
        PropertyInfoForValidator info = new PropertyInfoForValidator("新横浜","ハンディプリンタ");
        Validator validator = new PropertyInfoValidator();
        int num = validator.validate(info);
        assertEquals(num , 0);
    }

    @Test
    public void validateLocationNull() {
        PropertyInfoForValidator info = new PropertyInfoForValidator(null,"ハンディプリンタ");
        Validator validator = new PropertyInfoValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validateProductNameNull() {
        PropertyInfoForValidator info = new PropertyInfoForValidator("新横浜",null);
        Validator validator = new PropertyInfoValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validateNull() {
        PropertyInfoForValidator info = new PropertyInfoForValidator(null,null);
        Validator validator = new PropertyInfoValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validateLocationEmpty() {
        PropertyInfoForValidator info = new PropertyInfoForValidator("","ハンディプリンタ");
        Validator validator = new PropertyInfoValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validateProductNameEmpty() {
        PropertyInfoForValidator info = new PropertyInfoForValidator("新横浜","");
        Validator validator = new PropertyInfoValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validateEmpty() {
        PropertyInfoForValidator info = new PropertyInfoForValidator("","");
        Validator validator = new PropertyInfoValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }



}