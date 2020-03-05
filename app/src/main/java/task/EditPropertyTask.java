package task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import jsonclass.ErrorJson;
import task.AsyncTaskListener.CallbackListener;
import task.Request.EditPropertyRequest;

public class EditPropertyTask extends ServerTask<EditPropertyRequest, String> {

    // TODO コンストラクタで設定して、その後変更しない変数にはfinalをつける
    // キャメルケース

    public EditPropertyTask(CallbackListener<String> listener) {
        super(listener,"PUT",new Urls().getEdit());
    }

    @Override
    JSONObject createJson(EditPropertyRequest editPropertyRequest) {
        org.json.JSONObject json = new org.json.JSONObject();
        try {
            json.put("userId",editPropertyRequest.getUserId());
            json.put("assetId",editPropertyRequest.getAssetId());
            json.put("data",editPropertyRequest.getmPropertyInfo().toString());

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
