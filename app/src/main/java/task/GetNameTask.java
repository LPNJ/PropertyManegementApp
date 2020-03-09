package task;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import json.ErrorAndUserListJson;
import task.AsyncTaskListener.CallbackListener;
import task.response.GetNameResponse;

public class GetNameTask extends ServerTask<String, GetNameResponse> {

    private static final String TAG = "GetNameTask";

    public GetNameTask(CallbackListener<GetNameResponse> listener) {
        super(listener , RequestType.GET_NAMES);
    }

    @Override
    JSONObject createJson(String s) {
        return null;
    }

    @Override
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

    @Override
    GetNameResponse returnErrorCode() {
        GetNameResponse response;
        ArrayList<String> names = null;
        response = new GetNameResponse("1",names);
        return response;
    }

}


