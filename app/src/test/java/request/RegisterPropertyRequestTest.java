package request;

import org.junit.Test;

import entity.PropertyInfo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RegisterPropertyRequestTest {

    @Test
    public void getUserIdSuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        RegisterPropertyRequest request = new RegisterPropertyRequest("komiyama",info);
        assertThat(request.getUserId() , is("komiyama"));
    }

    @Test
    public void getPropertyManagerSuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        RegisterPropertyRequest request = new RegisterPropertyRequest("komiyama",info);
        assertThat(request.getInfo().getPropertyManager() , is("komiyama"));
    }

    @Test
    public void getPropertyUserSuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        RegisterPropertyRequest request = new RegisterPropertyRequest("komiyama",info);
        assertThat(request.getInfo().getPropertyUser() , is("komiyama"));
    }

    @Test
    public void getPropertyLocationSuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        RegisterPropertyRequest request = new RegisterPropertyRequest("komiyama",info);
        assertThat(request.getInfo().getLocation() , is("新横浜"));
    }

    @Test
    public void getPropertyProductNameSuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        RegisterPropertyRequest request = new RegisterPropertyRequest("komiyama",info);
        assertThat(request.getInfo().getProductName() , is("ハンディプリンタ"));
    }

    @Test
    public void getPropertyPurchaseCategorySuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        RegisterPropertyRequest request = new RegisterPropertyRequest("komiyama",info);
        assertThat(request.getInfo().getPurchaseCategory() , is("レンタル"));
    }

    @Test
    public void getPropertyPropertyCategorySuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        RegisterPropertyRequest request = new RegisterPropertyRequest("komiyama",info);
        assertThat(request.getInfo().getPropertyCategory() , is("機器"));
    }

    @Test
    public void getPropertyCompletementSuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        RegisterPropertyRequest request = new RegisterPropertyRequest("komiyama",info);
        assertThat(request.getInfo().getComplement() , is("特になし"));
    }

}