package response;

import org.junit.Test;

import json.PropertyInfoJson;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetReferencePropertyResponseTest {

    @Test
    public void getErrorSuccess() {
        PropertyInfoJson json = new PropertyInfoJson();
        json.setPropertyManager("komiyama");
        GetReferencePropertyResponse response = new GetReferencePropertyResponse("0",json);
        assertThat(response.getError() , is("0"));
    }

    @Test
    public void getPropertyManagerSuccess() {
        PropertyInfoJson json = new PropertyInfoJson();
        json.setPropertyManager("komiyama");
        GetReferencePropertyResponse response = new GetReferencePropertyResponse("0",json);
        assertThat(response.getInfo().mPropertyManager , is("komiyama"));
    }

    @Test
    public void getPropertyUserSuccess() {
        PropertyInfoJson json = new PropertyInfoJson();
        json.setPropertyUser("User");
        GetReferencePropertyResponse response = new GetReferencePropertyResponse("0",json);
        assertThat(response.getInfo().mPropertyUser , is("User"));
    }

    @Test
    public void getPropertyLocationSuccess() {
        PropertyInfoJson json = new PropertyInfoJson();
        json.setLocation("新横浜");
        GetReferencePropertyResponse response = new GetReferencePropertyResponse("0",json);
        assertThat(response.getInfo().mLocation , is("新横浜"));
    }

    @Test
    public void getPropertyProductNameSuccess() {
        PropertyInfoJson json = new PropertyInfoJson();
        json.setProductName("ハンディプリンタ");
        GetReferencePropertyResponse response = new GetReferencePropertyResponse("0",json);
        assertThat(response.getInfo().mProductName , is("ハンディプリンタ"));
    }

    @Test
    public void getPropertyPurchaseCategorySuccess() {
        PropertyInfoJson json = new PropertyInfoJson();
        json.setPurchaseCategory("レンタル");
        GetReferencePropertyResponse response = new GetReferencePropertyResponse("0",json);
        assertThat(response.getInfo().mPurchaseCategory , is("レンタル"));
    }

    @Test
    public void getPropertyPropertyCategorySuccess() {
        PropertyInfoJson json = new PropertyInfoJson();
        json.setPropertyCategory("機器");
        GetReferencePropertyResponse response = new GetReferencePropertyResponse("0",json);
        assertThat(response.getInfo().mPropertyCategory , is("機器"));
    }

    @Test
    public void getPropertyCompleteSuccess() {
        PropertyInfoJson json = new PropertyInfoJson();
        json.setComplement("特になし");
        GetReferencePropertyResponse response = new GetReferencePropertyResponse("0",json);
        assertThat(response.getInfo().mComplement , is("特になし"));
    }

}