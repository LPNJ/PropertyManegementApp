package task.mock;

import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;

public class NewAccountTaskMock{

    CallbackListener<String> mResultListener;

    public NewAccountTaskMock(CallbackListener<String> resultListener) {
        mResultListener = resultListener;
    }

    public void execute(UserInfo userInfo) {

        if("failure".equals(userInfo.getUserId())) {
            mResultListener.onPostExecute("1");
        } else if("nakayama".equals(userInfo.getUserId())){
            mResultListener.onPostExecute("11");
        } else {
            mResultListener.onPostExecute("0");
        }

    }
}


