package response;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RegisterPropertyResponseTest {

    @Test
    public void getErrorSuccess() {
        RegisterPropertyResponse response = new RegisterPropertyResponse("0","10001");
        assertThat(response.getError() , is("0"));
    }

    @Test
    public void getAssetIdSuccess() {
        RegisterPropertyResponse response = new RegisterPropertyResponse("0","10001");
        assertThat(response.getControlNumber() , is("10001"));
    }

}