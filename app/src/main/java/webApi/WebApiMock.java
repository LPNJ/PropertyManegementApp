package webApi;

import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;
import task.mock.DeletePropertyInfoTaskMock;
import task.mock.GetNameTaskMock;
import task.mock.GetPropertyInfoTaskMock;
import task.mock.GetReferenceInfoTaskMock;
import task.mock.LoginTaskMock;
import task.mock.NewAccountTaskMock;
import task.mock.PropertyEditTaskMock;
import task.mock.RegisterPropertyInfoTaskMock;
import request.DeletePropertyRequest;
import request.EditPropertyRequest;
import request.RegisterPropertyRequest;
import response.GetNameResponse;
import response.GetPropertyResponse;
import response.GetReferencePropertyResponse;
import response.RegisterPropertyResponse;

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
        new RegisterPropertyInfoTaskMock(listener).execute(request);
    }

    @Override
    public void getProperty(CallbackListener<GetPropertyResponse> listener) {
        new GetPropertyInfoTaskMock(listener).execute();
    }

    @Override
    public void getReferenceProperty(CallbackListener<GetReferencePropertyResponse> listener , String assetId) {
        new GetReferenceInfoTaskMock(listener).execute();
    }

    @Override
    public void editProperty(EditPropertyRequest request, CallbackListener<String> listener) {
        new PropertyEditTaskMock(listener).execute(request);
    }

    @Override
    public void deleteProperty(DeletePropertyRequest request, CallbackListener<String> listener) {
        new DeletePropertyInfoTaskMock(listener).execute(request);
    }
}
