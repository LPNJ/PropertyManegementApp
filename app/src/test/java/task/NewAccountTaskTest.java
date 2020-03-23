package task;

import org.json.JSONObject;
import org.junit.Test;

import entity.UserInfo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class NewAccountTaskTest {

    @Test
    public void createJsonSuccess() {
        NewAccountTask task = new NewAccountTask(null);
        String jsonSt = "{"+"\"password\":" + "\""+"komiyama"+"\""+","+"\"userId\":"+"\""+"komiyama"+"\""+"}";
        JSONObject json;
        json = task.createJson(new UserInfo("komiyama","komiyama"));
        assertThat(json.toString() , containsString(jsonSt));
    }

    @Test
    public void parseJsonSuccess() {
        NewAccountTask task = new NewAccountTask(null);

        String readSd = "{\"error\":0}";
        String errorCode = task.parseJson(readSd);
        assertThat(errorCode , is("0"));

    }

}