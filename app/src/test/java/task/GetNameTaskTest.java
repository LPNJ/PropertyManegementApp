package task;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import json.ErrorAndUserListJson;
import response.GetNameResponse;

import static android.support.constraint.Constraints.TAG;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
public class GetNameTaskTest {

    @Test
    public void parseJsonSuccess() {
        GetNameTask task = new GetNameTask(null);

        String readSd = "{\"error\":0,\"userList\":[\"takayama\",\"kawasaki\",\"kanayama\",\"komiyama\"]}";
        GetNameResponse errorCode = task.parseJson(readSd);
        assertThat(errorCode.getError() , is("0"));
    }

    @Test
    public void parseJsonForGetNamesSuccess() {
        GetNameTask task = new GetNameTask(null);

        String readSd = "{\"error\":0,\"userList\":[\"takayama\",\"kawasaki\",\"kanayama\",\"komiyama\"]}";
        GetNameResponse errorCode = task.parseJson(readSd);
        assertThat(errorCode.getNames().get(0) , is("takayama"));
        assertThat(errorCode.getNames().get(1) , is("kawasaki"));
        assertThat(errorCode.getNames().get(2) , is("kanayama"));
        assertThat(errorCode.getNames().get(3) , is("komiyama"));
    }

    GetNameResponse parseJson(String readSd) {

        GetNameResponse response = null;
        String returnCode = null;
        ArrayList<String> names = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            ErrorAndUserListJson info = mapper.readValue(readSd, ErrorAndUserListJson.class);
            returnCode = info.mError;
            names = info.mNames;
            response = new GetNameResponse(returnCode,names);
        } catch (IOException e) {
            Log.e(TAG, "IOException occurred." , e);
            response = new GetNameResponse("1",names);
            return response;
        }
        return response;
    }

}