package task.AsyncTaskListener;

import task.response.GetNameResponse;
import task.response.GetReferencePropertyResponse;

public interface CallbackListener_getReferenceProperty {
    void onPostExecute(GetReferencePropertyResponse response);
}
