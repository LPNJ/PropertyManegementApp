package task.mock;

import task.LogoutTask;
import task.ResultListener;

public class LogoutTaskMock implements LogoutTask {

    private ResultListener listener = null;

    public LogoutTaskMock(ResultListener listener){
        listener = listener;
    }

    @Override
    public void execute(String s, ResultListener listen) {
        listener.onResult(0);
    }
}
