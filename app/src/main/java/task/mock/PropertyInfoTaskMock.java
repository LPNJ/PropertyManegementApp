package task.mock;

import entity.PropertyInfo;
import task.PropertyInfoTask;
import task.ResultListener;
import task.serialize.PropertyRegistRequest;
import task.serialize.PropertyRegistResponse;

public class PropertyInfoTaskMock implements PropertyInfoTask {

    @Override
    public void execute(PropertyRegistRequest propertyRegistRequest, ResultListener resultListener) {
        resultListener.onResult(new PropertyRegistResponse(0,"10001"));
    }

}
