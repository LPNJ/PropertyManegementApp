package task.mock;

import java.util.ArrayList;

import entity.PropertyInfo;
import task.GetPropertyInfoTask;
import task.ResultListener;
import task.serialize.PropertyInfoRequest;
import task.serialize.PropertyInfoResponse;

public class GetPropertyInfoTaskMock implements GetPropertyInfoTask {

    ArrayList<PropertyInfo> mPropertyInfo = new ArrayList<>();
    int mNumber = 0;

    @Override
    public void execute(String propertyInfoRequest, ResultListener resultListener) {


        mPropertyInfo.add(new PropertyInfo("komiyama","maeda","新横浜","10001","ディスプレイ","レンタル","ディスプレイ",""));
        mPropertyInfo.add(new PropertyInfo("maeda","kimura","海老名","10002","マウス","レンタル","周辺機器",""));
        mPropertyInfo.add(new PropertyInfo("kimura","komiyama","大森","10003","SDカード","レンタル","USB・SD",""));

        for(int i=0;i<mPropertyInfo.size();i++){
            if(propertyInfoRequest.equals(mPropertyInfo.get(i).getControlNumber())){
                mNumber = i;
            }
        }

        resultListener.onResult(new PropertyInfoResponse(mPropertyInfo.get(mNumber)));

    }
}
