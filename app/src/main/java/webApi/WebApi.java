package webApi;

import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;
import task.Request.DeletePropertyRequest;
import task.Request.EditPropertyRequest;
import task.Request.RegisterPropertyRequest;
import task.response.GetNameResponse;
import task.response.GetPropertyResponse;
import task.response.GetReferencePropertyResponse;
import task.response.RegisterPropertyResponse;

public interface WebApi {
    void login(UserInfo userInfo, CallbackListener<String>  listener);
    void newAccount(UserInfo userInfo, CallbackListener<String>  listener);
    void getName(CallbackListener<GetNameResponse> listener);
    void propertyRegister(RegisterPropertyRequest request, CallbackListener<RegisterPropertyResponse>  listener);
    void getProperty(CallbackListener<GetPropertyResponse> listener);
    void getReferenceProperty(CallbackListener<GetReferencePropertyResponse> listener, String assetId);
    void editProperty(EditPropertyRequest request , CallbackListener<String> listener);
    void deleteProperty(DeletePropertyRequest request, CallbackListener<String>  listener);

}
