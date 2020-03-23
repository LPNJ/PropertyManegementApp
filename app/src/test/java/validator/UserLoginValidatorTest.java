package validator;

import org.junit.Test;

import entity.UserInfo;

import static org.junit.Assert.assertEquals;
import static validator.Validator.SUCCESS;

public class UserLoginValidatorTest {

    @Test
    public void validateIdNull() {

        UserInfo info = new UserInfo(null,"komikomi");
        Validator validator = new UserLoginValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validatePasswordNull() {

        UserInfo info = new UserInfo("komiyama",null);
        Validator validator = new UserLoginValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }
    
    @Test
    public void validateNull() {

        UserInfo info = new UserInfo(null,null);
        Validator validator = new UserLoginValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validateSUCCESS() {

        UserInfo info = new UserInfo("komiyama","komikomi");
        Validator validator = new UserLoginValidator();
        int num = validator.validate(info);
        assertEquals(num , SUCCESS);
    }

    @Test
    public void validateIdEmpty() {
        UserInfo info = new UserInfo("","komikomi");
        Validator validator = new UserLoginValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validatePasswordEmpty() {

        UserInfo info = new UserInfo("komiyama","");
        Validator validator = new UserLoginValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validateEmpty() {
        UserInfo info = new UserInfo("","");
        Validator validator = new UserLoginValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

}