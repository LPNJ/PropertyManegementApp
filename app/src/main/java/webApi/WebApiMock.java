package webApi;

import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;
import task.DeletePropertyTask;
import task.EditPropertyTask;
import task.GetPropertyInfoTask;
import task.GetReferenceInfoTask;
import task.RegisterPropertyInfoTask;
import task.request.DeletePropertyRequest;
import task.request.EditPropertyRequest;
import task.request.RegisterPropertyRequest;
import task.mock.GetNameTaskMock;
import task.mock.LoginTaskMock;
import task.mock.NewAccountTaskMock;
import task.response.GetNameResponse;
import task.response.GetPropertyResponse;
import task.response.GetReferencePropertyResponse;
import task.response.RegisterPropertyResponse;

public class WebApiMock implements WebApi {
    @Override
    public void login(UserInfo userInfo, CallbackListener<String> listener) {
        new LoginTaskMock(listener).execute(userInfo);
    }

    @Override
    public void newAccount(UserInfo userInfo, CallbackListener<String> listener) {
        new NewAccountTaskMock(listener).execute(userInfo);
    }

    @Override
    public void getName(CallbackListener<GetNameResponse> listener) {
        new GetNameTaskMock(listener).execute();
    }

    @Override
    public void registerProperty(RegisterPropertyRequest request, CallbackListener<RegisterPropertyResponse> listener) {
        new RegisterPropertyInfoTask(listener).execute(request);
    }

    @Override
    public void getProperty(CallbackListener<GetPropertyResponse> listener) {
        new GetPropertyInfoTask(listener).execute();
    }

    @Override
    public void getReferenceProperty(CallbackListener<GetReferencePropertyResponse> listener , String assetId) {
        new GetReferenceInfoTask(listener , assetId).execute();
    }

    @Override
    public void editProperty(EditPropertyRequest request, CallbackListener<String> listener) {
        new EditPropertyTask(listener).execute(request);
    }

    @Override
    public void deleteProperty(DeletePropertyRequest request, CallbackListener<String> listener) {
        new DeletePropertyTask(listener).execute(request);
    }
}
