package validator;

import org.junit.Test;

import entity.EditableUserInfo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static validator.Validator.SUCCESS;

public class NewAccountValidatorTest {

    @Test
    public void validateIdNull() {
        EditableUserInfo info = new EditableUserInfo(null,"komikomi","komikomi");
        Validator validator = new NewAccountValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validatePasswordNull() {
        EditableUserInfo info = new EditableUserInfo("komiyama",null,"komikomi");
        Validator validator = new NewAccountValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validateConfirmationPasswordNull() {
        EditableUserInfo info = new EditableUserInfo("komiyama","komikomi",null);
        Validator validator = new NewAccountValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validateNull() {
        EditableUserInfo info = new EditableUserInfo(null,null,null);
        Validator validator = new NewAccountValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validateIdEmpty() {
        EditableUserInfo info = new EditableUserInfo("","komikomi","komikomi");
        Validator validator = new NewAccountValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validatePasswordEmpty() {
        EditableUserInfo info = new EditableUserInfo("komiyama",null,"komikomi");
        Validator validator = new NewAccountValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validateConfirmationPasswordEmpty() {
        EditableUserInfo info = new EditableUserInfo("komiyama","komikomi",null);
        Validator validator = new NewAccountValidator();
        int num = validator.validate(info);
        assertEquals(num , 1);
    }

    @Test
    public void validateSuccess() {
        EditableUserInfo info = new EditableUserInfo("komiyama","komikomi","komikomi");
        Validator validator = new NewAccountValidator();
        int num = validator.validate(info);
        assertThat(num , is(SUCCESS));
    }

    @Test
    public void validateIdInputTooShort() {
        EditableUserInfo info = new EditableUserInfo("komi","komikomi","komikomi");
        Validator validator = new NewAccountValidator();
        int num = validator.validate(info);
        assertThat(num , is(3));
    }

    @Test
    public void validatePassInputTooShort() {
        EditableUserInfo info = new EditableUserInfo("komiyama","komi","komikomi");
        Validator validator = new NewAccountValidator();
        int num = validator.validate(info);
        assertThat(num , is(4));
    }

    @Test
    public void validatePassNotMatch() {
        EditableUserInfo info = new EditableUserInfo("komiyama","komikomi","komihiro");
        Validator validator = new NewAccountValidator();
        int num = validator.validate(info);
        assertThat(num , is(5));
    }

}