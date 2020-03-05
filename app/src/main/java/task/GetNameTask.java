package task;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import jsonclass.ErrorAndUserListJson;
import task.AsyncTaskListener.CallbackListener;
import task.response.GetNameResponse;

public class GetNameTask extends ServerTask<String, GetNameResponse> {

    public GetNameTask(CallbackListener<GetNameResponse> listener) {
        super(listener , "GET",new Urls().getName());
    }

    @Override
    JSONObject createJson(String s) {
        return null;
    }

    @Override
    GetNameResponse parseJson(String readSd) {

        GetNameResponse response = null;
        String returnCode = null;
        ArrayList<String> names;

        ObjectMapper mapper = new ObjectMapper();
        try {
            ErrorAndUserListJson info = mapper.readValue(readSd, ErrorAndUserListJson.class);
            returnCode = info.mError;
            names = info.mNames;
            response = new GetNameResponse(returnCode,names);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}


