package task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import jsonclass.ErrorJson;
import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;

public class LoginTask extends ServerTask<UserInfo,String> {

    public LoginTask(CallbackListener<String> listener) {
        super(listener, RequestType.LOGIN);
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
    String parseJson(String readSt){
        String returnCode = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            ErrorJson info = mapper.readValue(readSt, ErrorJson.class);
            returnCode = info.error;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnCode;
    }

}

