package task.mock;

import task.AsyncTaskListener.CallbackListener;
import task.Executer;
import task.response.RegisterPropertyResponse;
import task.Request.PropertyRegistRequest;

public class RegisterPropertyInfoTaskMock implements Executer<PropertyRegistRequest> {

    private CallbackListener<RegisterPropertyResponse> listener = null;

    public RegisterPropertyInfoTaskMock(CallbackListener<RegisterPropertyResponse> listener) {
        this.listener = listener;
    }

    @Override
    public void execute(PropertyRegistRequest propertyRegistRequest) {
        listener.onPostExecute(new RegisterPropertyResponse("0","10001"));
    }
}
