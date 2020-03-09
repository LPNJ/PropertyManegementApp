package task;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import json.ErrorJson;
import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;

public class NewAccountTask extends ServerTask<UserInfo,String> {

    // TODO 必要ない消す
    private static final String TAG = "NewAccountTask";

    public NewAccountTask(CallbackListener<String> listener) {
        super(listener,RequestType.NEW_ACCOUNT);
    }

    @Override
    JSONObject createJson(UserInfo userInfo) {
        JSONObject json = new org.json.JSONObject();
        try {
            json.put("userId", userInfo.getUserId());
            json.put("password", userInfo.getPassword());

        } catch (JSONException e) {
            Log.e(TAG, "JSONException occurred." , e);
        }
        return json;
    }

    @Override
    String parseJson(String readSd) {
        String returnCode = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            ErrorJson info = mapper.readValue(readSd, ErrorJson.class);
            returnCode = info.mError;
        } catch (IOException e) {
            Log.e(TAG, "IOException occurred." , e);
            returnCode = "1";
            return returnCode;
        }
        return returnCode;
    }

    @Override
    String returnErrorCode() {
        String errorCode = "1";
        return errorCode;
    }

}
