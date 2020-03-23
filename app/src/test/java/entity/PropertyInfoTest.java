package entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PropertyInfoTest {

    @Test
    public void getPropertyManagerSuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "10001",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        assertThat(info.getPropertyManager() , is("komiyama"));
    }

    @Test
    public void getPropertyUserSuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "10001",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        assertThat(info.getPropertyUser() , is("komiyama"));
    }

    @Test
    public void getPropertyLocationSuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "10001",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        assertThat(info.getLocation() , is("新横浜"));
    }

    @Test
    public void getPropertyAssetIdSuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "10001",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        assertThat(info.getControlNumber() , is("10001"));
    }

    @Test
    public void getPropertyProductNameSuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "10001",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        assertThat(info.getProductName() , is("ハンディプリンタ"));
    }

    @Test
    public void getPropertyPurchaseCategorySuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "10001",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        assertThat(info.getPurchaseCategory() , is("レンタル"));
    }

    @Test
    public void getPropertyPropertyCategorySuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "10001",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        assertThat(info.getPropertyCategory() , is("機器"));
    }

    @Test
    public void getPropertyPropertyCompletementSuccess() {
        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "10001",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        assertThat(info.getComplement() , is("特になし"));
    }

}