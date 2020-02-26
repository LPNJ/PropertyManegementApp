package task.AsyncTaskListener;

import task.response.GetNameResponse;
import task.response.GetPropertyResponse;

public interface CallbackListener_getProperties {
    void onPostExecute(GetPropertyResponse response);
}
