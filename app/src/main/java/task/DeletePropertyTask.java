package task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import jsonclass.ErrorJson;
import task.AsyncTaskListener.CallbackListener;
import task.Request.DeletePropertyRequest;

public class DeletePropertyTask extends ServerTask<DeletePropertyRequest, String> {

    private CallbackListener<String> listener = null;

    public DeletePropertyTask(CallbackListener<String> listener) {
        super(listener,"DELETE",new Urls().getDelete());
        this.listener = listener;
    }

    @Override
    JSONObject createJson(DeletePropertyRequest deletePropertyRequest) {
        org.json.JSONObject json = new org.json.JSONObject();
        try {
            json.put("userId",deletePropertyRequest.getUserId());
            json.put("assetId",deletePropertyRequest.getAssetId());

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

    // TODO 同じ処理がいろんなところにあるので共通化する


}