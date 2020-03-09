package task.mock;

import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;

public class LoginTaskMock{


    CallbackListener<String> resultListener;

    public LoginTaskMock(CallbackListener<String> resultListener) {
        resultListener = resultListener;
    }

    public void execute(UserInfo userInfo) {

        if("failure".equals(userInfo.getUserId())) {
            resultListener.onPostExecute("1");
        } else {
            resultListener.onPostExecute("0");
        }

    }
}
