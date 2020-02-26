package task.mock;

import java.util.ArrayList;

import entity.PropertyInfo;
import task.GetTargetNamePropertyInfoTask;
import task.ResultListener;
import task.serialize.PropertyInfoResponse;

public class GetTargetNamePropertyInfoTaskMock implements GetTargetNamePropertyInfoTask {

    ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();

    @Override
    public void execute(String s, ResultListener resultListener) {

        propertyInfos.add(new PropertyInfo("komiyama","maeda","新横浜","10001","ディスプレイ","レンタル","ディスプレイ",""));
        propertyInfos.add(new PropertyInfo("maeda","kimura","海老名","10002","マウス","レンタル","周辺機器",""));
        propertyInfos.add(new PropertyInfo("kimura","komiyama","大森","10003","SDカード","レンタル","USB・SD",""));

        resultListener.onResult(propertyInfos);

    }
}
