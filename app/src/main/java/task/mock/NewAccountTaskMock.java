package task.mock;

import com.example.z00s600149.propertymanegementapp.NewAccountActivity;

import entity.UserInfo;
import task.NewAccountTask;
import task.ResultListener;

public class NewAccountTaskMock implements NewAccountTask {
    @Override
    public void execute(UserInfo userInfo, ResultListener resultListener) {
        if("failure".equals(userInfo.getUserId())) {
            resultListener.onResult(1);
        } else {
            resultListener.onResult(0);
        }
    }
}
