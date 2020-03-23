package request;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DeletePropertyRequestTest {

    @Test
    public void getUserIdSuccess() {
        DeletePropertyRequest request = new DeletePropertyRequest("komiyama",10001);
        assertThat(request.getUserId() , is("komiyama"));
    }

    @Test
    public void getAssetIdSuccess() {
        DeletePropertyRequest request = new DeletePropertyRequest("komiyama",10001);
        assertEquals(request.getAssetId() , 10001);
    }

}