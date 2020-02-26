package task.mock;

import task.LogoutTask;
import task.ResultListener;

public class LogoutTaskMock implements LogoutTask {
    @Override
    public void execute(String s, ResultListener listener) {
        listener.onResult(0);
    }
}
