package task.mock;

import java.util.ArrayList;

import entity.PropertyInfo;
import task.AsyncTaskListener.CallbackListener;
import task.Executer;
import task.response.GetPropertyResponse;

public class GetPropertyInfoTaskMock implements Executer<String> {

    private CallbackListener<GetPropertyResponse> listener = null;

    public GetPropertyInfoTaskMock(CallbackListener<GetPropertyResponse> listener) {
        this.listener = listener;
    }

    ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();

    @Override
    public void execute(String s) {

        GetPropertyResponse response = null;

        propertyInfos.add(new PropertyInfo("komiyama","maeda","新横浜","10001","ディスプレイ","レンタル","ディスプレイ",""));
        propertyInfos.add(new PropertyInfo("maeda","kimura","海老名","10002","マウス","レンタル","周辺機器",""));
        propertyInfos.add(new PropertyInfo("kimura","komiyama","大森","10003","SDカード","レンタル","USB・SD",""));

        listener.onPostExecute(response);
    }
}
