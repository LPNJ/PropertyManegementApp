package task.mock;

import entity.UserInfo;
import task.LoginTask;
import task.ResultListener;

public class LoginTaskMock implements LoginTask {
    @Override
    public void execute(UserInfo userInfo, ResultListener resultListener) {
        if("failure".equals(userInfo.getUserId())) {
            resultListener.onResult(1);
        } else {
            resultListener.onResult(0);
        }
    }
}
