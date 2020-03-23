package request;

import org.junit.Test;

import entity.PropertyInfo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class EditPropertyRequestTest {

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
        EditPropertyRequest request = new EditPropertyRequest("komiyama",10001,info);
        assertThat(request.getUserId() , is("komiyama"));
    }

    @Test
    public void getAssetIdSuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        EditPropertyRequest request = new EditPropertyRequest("",10001,info);
        assertEquals(request.getAssetId() , 10001);
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
        EditPropertyRequest request = new EditPropertyRequest("",10001,info);
        assertThat(request.getPropertyInfo().getPropertyManager() , is("komiyama"));
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
        EditPropertyRequest request = new EditPropertyRequest("",10001,info);
        assertThat(request.getPropertyInfo().getPropertyUser() , is("komiyama"));
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
        EditPropertyRequest request = new EditPropertyRequest("",10001,info);
        assertThat(request.getPropertyInfo().getLocation() , is("新横浜"));
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
        EditPropertyRequest request = new EditPropertyRequest("",10001,info);
        assertThat(request.getPropertyInfo().getProductName() , is("ハンディプリンタ"));
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
        EditPropertyRequest request = new EditPropertyRequest("",10001,info);
        assertThat(request.getPropertyInfo().getPurchaseCategory() , is("レンタル"));
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
        EditPropertyRequest request = new EditPropertyRequest("",10001,info);
        assertThat(request.getPropertyInfo().getPropertyCategory() , is("機器"));
    }

    @Test
    public void getPropertyCompleteSuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        EditPropertyRequest request = new EditPropertyRequest("",10001,info);
        assertThat(request.getPropertyInfo().getComplement() , is("特になし"));
    }

}