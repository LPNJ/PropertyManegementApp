package task;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import json.ErrorJson;
import task.AsyncTaskListener.CallbackListener;
import task.request.EditPropertyRequest;

public class EditPropertyTask extends ServerTask<EditPropertyRequest, String> {

    private static final String TAG = "EditPropertyTask";

    public EditPropertyTask(CallbackListener<String> listener) {
        super(listener,RequestType.EDIT);
    }

    @Override
    JSONObject createJson(EditPropertyRequest editPropertyRequest) {
        org.json.JSONObject json = new org.json.JSONObject();
        try {
            json.put("userId",editPropertyRequest.getUserId());
            json.put("assetId",editPropertyRequest.getAssetId());
            json.put("data",editPropertyRequest.getPropertyInfo().toString());
        } catch (JSONException e) {
            Log.e(TAG, "JSONException occurred.", e);
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
            Log.e(TAG, "IOException occurred.", e);
            returnCode = "1";
            return returnCode;
        }
        return returnCode;
    }

    @Override
    String returnErrorCode() {
        String returnCode = "1";
        return returnCode;
    }

}
