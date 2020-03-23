package entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PropertyInfoForValidatorTest {

    @Test
    public void getPropertyLocationSuccess() {
        PropertyInfoForValidator info = new PropertyInfoForValidator(
                "新横浜",
                "ハンディプリンタ");
        assertThat(info.getLocation() , is("新横浜"));
    }

    @Test
    public void getPropertyProductNameSuccess() {
        PropertyInfoForValidator info = new PropertyInfoForValidator(
                "新横浜",
                "ハンディプリンタ");
        assertThat(info.getProductName() , is("ハンディプリンタ"));
    }



}