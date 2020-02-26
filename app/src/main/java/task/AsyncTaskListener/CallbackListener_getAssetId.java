package task.AsyncTaskListener;

import task.response.GetNameResponse;
import task.response.RegisterPropertyResponse;

public interface CallbackListener_getAssetId {
    void onPostExecute(RegisterPropertyResponse response);
}
