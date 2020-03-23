package task;

import org.json.JSONObject;
import org.junit.Test;

import request.DeletePropertyRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DeletePropertyTaskTest {

    @Test
    public void createJsonSuccess() {
        DeletePropertyTask task = new DeletePropertyTask(null);

        String jsonSt = "{"+"\"assetId\":" + ""+10001+""+","+"\"userId\":"+"\""+"komiyama"+"\""+"}";
        JSONObject json = task.createJson(new DeletePropertyRequest("komiyama", 10001));
        assertThat(json.toString() ,is(jsonSt));
    }

    @Test
    public void parseJsonSuccess() {
        DeletePropertyTask task = new DeletePropertyTask(null);

        String readSd = "{\"error\":0}";
        String errorCode = task.parseJson(readSd);
        assertThat(errorCode , is("0"));

    }

}