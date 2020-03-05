package task;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import jsonclass.ErrorJson;
import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;

public class NewAccountTask extends ServerTask<UserInfo,String> {

    // TODO 必要ない消す
    private CallbackListener<String> listener = null;

    public NewAccountTask(CallbackListener<String> listener) {
        super(listener,"POST",new Urls().getNewAccount());
        this.listener = listener;
    }

    @Override
    JSONObject createJson(UserInfo userInfo) {
        JSONObject json = new org.json.JSONObject();
        try {
            json.put("userId", userInfo.getUserId());
            json.put("password", userInfo.getPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    String parseJson(String readSd) {
        String returnCode = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            ErrorJson info = mapper.readValue(readSd, ErrorJson.class);
            returnCode = info.error;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnCode;
    }

}
