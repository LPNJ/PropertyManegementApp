package task.mock;

import java.util.ArrayList;

import entity.PropertyInfo;
import json.PropertyInfoJson;
import task.AsyncTaskListener.CallbackListener;
import response.GetReferencePropertyResponse;

public class GetReferenceInfoTaskMock{

    private CallbackListener<GetReferencePropertyResponse> mListener = null;

    public GetReferenceInfoTaskMock(CallbackListener<GetReferencePropertyResponse> listener) {
        mListener= listener;
    }

    ArrayList<PropertyInfo> mPropertyInfo = new ArrayList<>();

    public void execute() {

        GetReferencePropertyResponse response = null;

        PropertyInfoJson json = new PropertyInfoJson();
        json.setPropertyManager("komiyama");
        json.setPropertyUser("komiyama");
        json.setLocation("新横浜");
        json.setControlNumber("10001");
        json.setProductName("ハンディプリンタ");
        json.setPurchaseCategory("レンタル");
        json.setPropertyCategory("機器");
        json.setComplement("");

        response = new GetReferencePropertyResponse("0",json);

        mListener.onPostExecute(response);

    }
}
