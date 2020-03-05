package task;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import jsonclass.ErrorAndAssetidJSON;
import task.AsyncTaskListener.CallbackListener;
import task.Request.RegisterPropertyRequest;
import task.response.RegisterPropertyResponse;

public class RegisterPropertyInfoTask extends ServerTask<RegisterPropertyRequest, RegisterPropertyResponse> {

    public RegisterPropertyInfoTask(CallbackListener<RegisterPropertyResponse> listener) {
        super(listener,"POST",new Urls().getRegist());
    }

    @Override
    JSONObject createJson(RegisterPropertyRequest registerPropertyRequest) {
        org.json.JSONObject json = new org.json.JSONObject();
        try {
            json.put("userId",registerPropertyRequest.getError());
            json.put("data",registerPropertyRequest.getInfos().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    RegisterPropertyResponse parseJson(String readSd) {
        RegisterPropertyResponse response = null;
        String returnCode = null;
        String assetId = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            ErrorAndAssetidJSON info = mapper.readValue(readSd,ErrorAndAssetidJSON.class);
            returnCode = info.mError;
            assetId = info.mControlNumber;
            response = new RegisterPropertyResponse(returnCode,assetId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
