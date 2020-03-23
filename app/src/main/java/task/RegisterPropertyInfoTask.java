package task;

import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import json.ErrorAndAssetIdJson;
import task.AsyncTaskListener.CallbackListener;
import request.RegisterPropertyRequest;
import response.RegisterPropertyResponse;

public class RegisterPropertyInfoTask extends ServerTask<RegisterPropertyRequest, RegisterPropertyResponse> {

    private static final String TAG = "RegisterInfoTask";

    public RegisterPropertyInfoTask(CallbackListener<RegisterPropertyResponse> listener) {
        super(listener,RequestType.REGISTER);
    }

    @Override
    JSONObject createJson(RegisterPropertyRequest registerPropertyRequest) {
        org.json.JSONObject json = new org.json.JSONObject();
        try {
            json.put("userId",registerPropertyRequest.getUserId());
            json.put("data",registerPropertyRequest.getInfo().toString());
        } catch (JSONException e) {
            Log.e(TAG, "JSONException occurred." , e);
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
            ErrorAndAssetIdJson info = mapper.readValue(readSd, ErrorAndAssetIdJson.class);
            returnCode = info.mError;
            assetId = info.mControlNumber;
            response = new RegisterPropertyResponse(returnCode,assetId);
        } catch (IOException e) {
            Log.e(TAG, "IOException occurred." , e);
            response = new RegisterPropertyResponse("1",assetId);
            return response;
        }
        return response;
    }

    @Override
    RegisterPropertyResponse returnErrorCode() {
        RegisterPropertyResponse response = new RegisterPropertyResponse("1","00000");;
        return response;
    }

}
