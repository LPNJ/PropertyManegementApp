package task.mock;

import java.util.ArrayList;

import json.GetPropertyJson;
import task.AsyncTaskListener.CallbackListener;
import response.GetPropertyResponse;

public class GetPropertyInfoTaskMock{

    private CallbackListener<GetPropertyResponse> mListener;

    public GetPropertyInfoTaskMock(CallbackListener<GetPropertyResponse> listener) {
        mListener = listener;
    }

    ArrayList<GetPropertyJson> propertyInfo = new ArrayList<>();

    public void execute() {

        GetPropertyResponse response = null;

        String productManager = "komiyama";
        String productUser = "komiyama";
        String location = "新横浜";
        String controlNumber = "10001";
        String productNmae = "ハンディプリンタ";
        String purchaseCategory = "レンタル";
        String propertyCategory = "機器";
        String complete = "";

        GetPropertyJson json = new GetPropertyJson("komiyama", 10001, "{" +
                "\"mPropertyManager\":" + "\""+ productManager+ "\"" +
                ","+"\"mPropertyUser\":" + "\""+ productUser + "\"" +
                ","+"\"mLocation\":" + "\""+location + "\"" +
                ","+"\"mControlNumber\":" + "\""+controlNumber + "\"" +
                ","+"\"mProductName\":" + "\""+productNmae + "\"" +
                ","+"\"mPurchaseCategory\":" +"\""+ purchaseCategory + "\"" +
                ","+"\"mPropertyCategory\":" + "\""+propertyCategory + "\"" +
                ","+"\"mComplement\":" + "\""+ complete + "\"" +
                '}');

        propertyInfo.add(json);
        propertyInfo.add(json);

        response = new GetPropertyResponse("1",propertyInfo);

        mListener.onPostExecute(response);
    }
}
