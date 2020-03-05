package task;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import jsonclass.ErrorAndAssetsListJson;
import task.AsyncTaskListener.CallbackListener;
import task.response.GetPropertyEntity;
import task.response.GetPropertyResponse;

public class GetPropertyInfoTask extends ServerTask<String, GetPropertyResponse> {

    public GetPropertyInfoTask(CallbackListener<GetPropertyResponse> listener) {
        super(listener,"GET",new Urls().getPropertyInfo());
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
            e.printStackTrace();
        }
        // TODO IOExceptinoが発生したらnullを返すので問題ない？
        return response;
    }
}
