package task.AsyncTaskListener;

import task.response.GetNameResponse;

public interface CallbackListener_GetNames {
    void onPostExecute(GetNameResponse response);
}
