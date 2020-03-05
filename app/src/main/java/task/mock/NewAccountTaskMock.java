package task.mock;

import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;
import task.Executer;

public class NewAccountTaskMock implements Executer<UserInfo> {

    CallbackListener<String> resultListener;

    public NewAccountTaskMock(CallbackListener<String> resultListener) {
        resultListener = resultListener;
    }


    @Override
    public void execute(UserInfo userInfo) {

        if("failure".equals(userInfo.getUserId())) {
            resultListener.onPostExecute("1");
        } else {
            resultListener.onPostExecute("0");
        }

    }
}


