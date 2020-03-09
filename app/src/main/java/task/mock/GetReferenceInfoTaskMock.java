package task.mock;

import java.util.ArrayList;

import entity.PropertyInfo;
import task.AsyncTaskListener.CallbackListener;
import task.response.GetPropertyResponse;

public class GetReferenceInfoTaskMock{

    private CallbackListener<GetPropertyResponse> listener = null;

    public GetReferenceInfoTaskMock(CallbackListener<GetPropertyResponse> listener) {
        listener = listener;
    }

    ArrayList<PropertyInfo> mPropertyInfo = new ArrayList<>();
    int mNumber = 0;

    public void execute(String propertyInfoRequest) {

        GetPropertyResponse response = null;

        mPropertyInfo.add(new PropertyInfo("komiyama","maeda","新横浜","10001","ディスプレイ","レンタル","ディスプレイ",""));
        mPropertyInfo.add(new PropertyInfo("maeda","kimura","海老名","10002","マウス","レンタル","周辺機器",""));
        mPropertyInfo.add(new PropertyInfo("kimura","komiyama","大森","10003","SDカード","レンタル","USB・SD",""));

        for(int i=0;i<mPropertyInfo.size();i++){
            if(propertyInfoRequest.equals(mPropertyInfo.get(i).getControlNumber())){
                mNumber = i;
            }
        }

        listener.onPostExecute(response);

    }
}
