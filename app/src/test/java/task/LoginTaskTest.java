package task;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.shadows.ShadowLog;

import entity.UserInfo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LoginTaskTest {

    @Before
    public void setup() {
        // android.utils.Log を使ったログを出力する
        ShadowLog.stream = System.out;
    }

    @Test
    public void createJsonSuccess() {
        LoginTask task = new LoginTask(null);
        String jsonSt = "{"+"\"password\":" + "\""+"komiyama"+"\""+","+"\"userId\":"+"\""+"komiyama"+"\""+"}";
        JSONObject json = task.createJson(new UserInfo("komiyama","komiyama"));
        assertThat(json.toString() , is(jsonSt));
    }

    @Test
    public void parseJsonSuccess() {
        LoginTask task = new LoginTask(null);

        String readSd = "{\"error\":0}";
        String errorCode = task.parseJson(readSd);
        assertThat(errorCode , is("0"));
    }



}