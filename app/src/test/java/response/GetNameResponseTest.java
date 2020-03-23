package response;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetNameResponseTest {

    @Test
    public void getErrorSuccess() {
        ArrayList<String> list = new ArrayList<>();
        list.add("komiyama");
        GetNameResponse response = new GetNameResponse("0",list);
        assertThat(response.getError() , is("0"));
    }

    @Test
    public void getNameSuccess() {
        ArrayList<String> list = new ArrayList<>();
        list.add("komiyama");
        GetNameResponse response = new GetNameResponse("0",list);
        assertThat(response.getNames().get(0) , is("komiyama"));
    }

}