package task.mock;

import task.AsyncTaskListener.CallbackListener;
import task.Request.DeletePropertyRequest;

public class DeletePropertyInfoTaskMock{

    private CallbackListener<String> listener;

    DeletePropertyInfoTaskMock(CallbackListener<String> listener){
        listener = listener;
    }

    public void execute(DeletePropertyRequest propertyDeleteRequest) {
        listener.onPostExecute("0");
    }

}
