package task.mock;

import task.AsyncTaskListener.CallbackListener;
import task.Executer;
import task.Request.DeletePropertyRequest;

public class DeletePropertyInfoTaskMock implements Executer<DeletePropertyRequest> {

    private CallbackListener<String> listener;

    DeletePropertyInfoTaskMock(CallbackListener<String> listener){
        listener = listener;
    }

    @Override
    public void execute(DeletePropertyRequest propertyDeleteRequest) {
        listener.onPostExecute("0");
    }

}
