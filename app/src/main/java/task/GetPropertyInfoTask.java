package task;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import json.ErrorAndAssetsListJson;
import task.AsyncTaskListener.CallbackListener;
import task.response.GetPropertyEntity;
import task.response.GetPropertyResponse;

public class GetPropertyInfoTask extends ServerTask<String, GetPropertyResponse> {

    private static final String TAG = "GetPropertyInfoTask";

    public GetPropertyInfoTask(CallbackListener<GetPropertyResponse> listener) {
        super(listener,RequestType.GET_PROPERTIES);
    }

    @Override
    JSONObject createJson(String s) {
        return null;
    }

    @Override
    GetPropertyResponse parseJson(String readSd) {
        GetPropertyResponse response = null;
        String returnCode = null;
        ArrayList<GetPropertyEntity> properties = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            ErrorAndAssetsListJson info = mapper.readValue(readSd, ErrorAndAssetsListJson.class);
            returnCode = info.mError;
            properties = info.mProperties;
            response = new GetPropertyResponse(returnCode,properties);
        } catch (IOException e) {
            // TODO e.printStackTraceじゃなくてログ出す　ほかも全部
            // e.printStackTraceで検索
            Log.e(TAG, "IOException occurred." , e);
            response = new GetPropertyResponse("1",properties);
            return response;
        }
        // TODO IOExceptinoが発生したらnullを返すので問題ない？

        return response;
    }

    @Override
    GetPropertyResponse returnErrorCode() {
        ArrayList<GetPropertyEntity> properties = null;
        GetPropertyResponse response = new GetPropertyResponse("1",properties);
        return response;
    }
}
