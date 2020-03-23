package task;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import java.io.IOException;

import json.ErrorAndAssetsListJson;
import json.PropertyInfoJson;
import task.AsyncTaskListener.CallbackListener;
import json.GetPropertyJson;
import response.GetReferencePropertyResponse;

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

        ObjectMapper mapper = new ObjectMapper();
        try {
            ErrorAndAssetsListJson info = mapper.readValue(readSd, ErrorAndAssetsListJson.class);
            PropertyInfoJson propertyInfoJson = createPropertyInfoJson(info.mProperties.get(0));
            return new GetReferencePropertyResponse(info.mError, propertyInfoJson);
        } catch (IOException e) {
            Log.e(TAG, "IOException occurred." , e);
            return returnErrorCode();
        }
    }

    private PropertyInfoJson createPropertyInfoJson(GetPropertyJson getPropertyJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(getPropertyJson.getProperty(), PropertyInfoJson.class);
        } catch (IOException e) {
            Log.e(TAG, "IOException occurred." , e);
            return new PropertyInfoJson();
        }
    }

    @Override
    GetReferencePropertyResponse returnErrorCode() {
        return new GetReferencePropertyResponse("1",new PropertyInfoJson());
    }

}