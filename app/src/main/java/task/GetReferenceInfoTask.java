package task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import jsonclass.ErrorAndAssetsListJson;
import task.AsyncTaskListener.CallbackListener;
import task.response.GetPropertyEntity;
import task.response.GetReferencePropertyResponse;

public class GetReferenceInfoTask extends ServerTask<String, GetReferencePropertyResponse> {

    private CallbackListener<GetReferencePropertyResponse> listener = null;

    public GetReferenceInfoTask(CallbackListener<GetReferencePropertyResponse> listener , String assetId) {
        super(listener,"GET",new Urls().getReference(assetId));
    }

    @Override
    JSONObject createJson(String s) {
        return null;
    }

    @Override
    GetReferencePropertyResponse parseJson(String readSd) {
        String returnCode = null;
        ArrayList<GetPropertyEntity> preferenseProperty;
        GetReferencePropertyResponse response = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            ErrorAndAssetsListJson info = mapper.readValue(readSd, ErrorAndAssetsListJson.class);
            returnCode = info.mError;
            preferenseProperty = info.mProperties;
            response = new GetReferencePropertyResponse(returnCode,preferenseProperty.get(0).getProperty());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}