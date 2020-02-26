package task.mock;

import task.DeletePropertyInfoTask;
import task.ResultListener;
import task.serialize.PropertyDeleteRequest;
import task.serialize.PropertyInfoRequest;

public class DeletePropertyInfoTaskMock implements DeletePropertyInfoTask {

    @Override
    public void execute(PropertyDeleteRequest propertyDeleteRequest, ResultListener listener) {
        listener.onResult(0);
    }
}
