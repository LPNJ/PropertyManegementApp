package task;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import json.ErrorAndAssetsListJson;
import task.AsyncTaskListener.CallbackListener;
import task.response.GetPropertyEntity;
import task.response.GetReferencePropertyResponse;

public class GetReferenceInfoTask extends ServerTask<String, GetReferencePropertyResponse> {

    private static final String TAG = "GetReferenceInfoTask";

    public GetReferenceInfoTask(CallbackListener<GetReferencePropertyResponse> listener , String assetId) {
        super(listener, RequestType.GET_REFERENCE.getMethod(), RequestType.GET_REFERENCE.getUrl() + "/" + assetId);
    }

    @Override
    JSONObject createJson(String s) {
        return null;
    }

    @Override
    GetReferencePropertyResponse parseJson(String readSd) {
        String returnCode = null;
        // TODO タイポ
        ArrayList<GetPropertyEntity> preferenceProperty = null;
        GetReferencePropertyResponse response = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            ErrorAndAssetsListJson info = mapper.readValue(readSd, ErrorAndAssetsListJson.class);
            returnCode = info.mError;
            preferenceProperty = info.mProperties;
            response = new GetReferencePropertyResponse(returnCode,preferenceProperty.get(0).getProperty());
        } catch (IOException e) {
            Log.e(TAG, "IOException occurred." , e);
            response = new GetReferencePropertyResponse("1",preferenceProperty.get(0).getProperty());
            return response;
        }
        return response;
    }

    @Override
    GetReferencePropertyResponse returnErrorCode() {
        GetReferencePropertyResponse response = new GetReferencePropertyResponse("1","NoData");
        return response;
    }

}