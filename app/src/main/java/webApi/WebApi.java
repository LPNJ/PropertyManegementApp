package webApi;

import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;
import request.DeletePropertyRequest;
import request.EditPropertyRequest;
import request.RegisterPropertyRequest;
import response.GetNameResponse;
import response.GetPropertyResponse;
import response.GetReferencePropertyResponse;
import response.RegisterPropertyResponse;

public interface WebApi {
    void login(UserInfo userInfo, CallbackListener<String>  listener);
    void newAccount(UserInfo userInfo, CallbackListener<String>  listener);
    void getName(CallbackListener<GetNameResponse> listener);
    void registerProperty(RegisterPropertyRequest request, CallbackListener<RegisterPropertyResponse>  listener);
    void getProperty(CallbackListener<GetPropertyResponse> listener);
    void getReferenceProperty(CallbackListener<GetReferencePropertyResponse> listener, String assetId);
    void editProperty(EditPropertyRequest request , CallbackListener<String> listener);
    void deleteProperty(DeletePropertyRequest request, CallbackListener<String>  listener);

}
