package task.mock;

import task.AsyncTaskListener.CallbackListener;
import task.Request.RegisterPropertyRequest;
import task.response.RegisterPropertyResponse;

public class RegisterPropertyInfoTaskMock{

    private CallbackListener<RegisterPropertyResponse> listener = null;

    public RegisterPropertyInfoTaskMock(CallbackListener<RegisterPropertyResponse> listener) {
        this.listener = listener;
    }

    public void execute(RegisterPropertyRequest request) {
        listener.onPostExecute(new RegisterPropertyResponse("0","10001"));
    }
}
