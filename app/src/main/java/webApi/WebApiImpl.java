package webApi;

import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;
import task.DeletePropertyTask;
import task.EditPropertyTask;
import task.GetNameTask;
import task.GetPropertyInfoTask;
import task.GetReferenceInfoTask;
import task.LoginTask;
import task.NewAccountTask;
import task.RegisterPropertyInfoTask;
import task.Request.DeletePropertyRequest;
import task.Request.EditPropertyRequest;
import task.Request.RegisterPropertyRequest;
import task.response.GetNameResponse;
import task.response.GetPropertyResponse;
import task.response.GetReferencePropertyResponse;
import task.response.RegisterPropertyResponse;
import webApi.WebApi;

public class WebApiImpl implements WebApi {


    @Override
    public void login(UserInfo userInfo, CallbackListener<String> listener) {
        new LoginTask(listener).execute(userInfo);
    }

    @Override
    public void newAccount(UserInfo userInfo, CallbackListener<String> listener) {
        new NewAccountTask(listener).execute(userInfo);
    }

    @Override
    public void getName(CallbackListener<GetNameResponse> listener) {
        new GetNameTask(listener).execute();
    }

    @Override
    public void propertyRegister(RegisterPropertyRequest request, CallbackListener<RegisterPropertyResponse> listener) {
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
