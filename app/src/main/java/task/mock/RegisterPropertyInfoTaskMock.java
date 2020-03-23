package task.mock;

import task.AsyncTaskListener.CallbackListener;
import request.RegisterPropertyRequest;
import response.RegisterPropertyResponse;

public class RegisterPropertyInfoTaskMock{

    private CallbackListener<RegisterPropertyResponse> listener = null;

    public RegisterPropertyInfoTaskMock(CallbackListener<RegisterPropertyResponse> listener) {
        this.listener = listener;
    }

    public void execute(RegisterPropertyRequest request) {
        if(request.getUserId().equals("komiyama")) {
            listener.onPostExecute(new RegisterPropertyResponse("0", "10001"));
        }
        if(request.getUserId().equals("failure")) {
            listener.onPostExecute(new RegisterPropertyResponse("1", "10001"));
        }
        if(request.getInfo().getLocation().equals("failure")) {
            listener.onPostExecute(new RegisterPropertyResponse("2", "10001"));
        }
        if(request.getUserId().isEmpty()) {
            listener.onPostExecute(new RegisterPropertyResponse("12", "10001"));
        }
        if(request.getUserId().equals("komiyama") && request.getInfo().getLocation().equals("MAX")) {
            listener.onPostExecute(new RegisterPropertyResponse("32", "10001"));
        }
    }
}

