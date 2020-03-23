package task.mock;

import task.AsyncTaskListener.CallbackListener;
import request.EditPropertyRequest;

public class PropertyEditTaskMock {

    private CallbackListener<String> listener = null;

    public PropertyEditTaskMock(CallbackListener<String> listener) {
        this.listener = listener;
    }

    public void execute(EditPropertyRequest request) {
        if(request.getUserId().equals("komiyama")) {
            listener.onPostExecute("0");
        }
        if(request.getUserId().equals("failure")) {
            listener.onPostExecute("1");
        }
        if(request.getUserId().equals("nakayama")) {
            listener.onPostExecute("2");
        }
        if(request.getUserId().equals("katayama")) {
            listener.onPostExecute("12");
        }
        if(request.getUserId().equals("takayama")) {
            listener.onPostExecute("31");
        }
    }

}
