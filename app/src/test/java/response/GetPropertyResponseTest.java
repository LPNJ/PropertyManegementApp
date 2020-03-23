package response;

import org.junit.Test;

import java.util.ArrayList;
import json.GetPropertyJson;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
public class GetPropertyResponseTest {

    @Test
    public void getErrorSuccess() {
        ArrayList<GetPropertyJson> list = new ArrayList<>();
        list.add(new GetPropertyJson("komiyama",10001,"json"));
        GetPropertyResponse response = new GetPropertyResponse("0",list);
        assertThat(response.getError() , is("0"));
    }

    @Test
    public void getUserIdSuccess() {
        ArrayList<GetPropertyJson> list = new ArrayList<>();
        GetPropertyJson json = new GetPropertyJson("komiyama",10001,"json");
        list.add(json);
        GetPropertyResponse response = new GetPropertyResponse("0",list);
        assertThat(response.getInfos().get(0).getUserId() , is("komiyama"));
    }

    @Test
    public void getAssetIdSuccess() {
        ArrayList<GetPropertyJson> list = new ArrayList<>();
        list.add(new GetPropertyJson("komiyama",10001,"json"));
        GetPropertyResponse response = new GetPropertyResponse("0",list);
        assertEquals(response.getInfos().get(0).getAssetId() , 10001);
    }

    @Test
    public void getDataSuccess() {
        ArrayList<GetPropertyJson> list = new ArrayList<>();
        GetPropertyJson json = new GetPropertyJson("komiyama",10001,"json");
        list.add(json);
        GetPropertyResponse response = new GetPropertyResponse("0",list);
        assertThat(response.getInfos().get(0).getProperty() , is("json"));
    }

}