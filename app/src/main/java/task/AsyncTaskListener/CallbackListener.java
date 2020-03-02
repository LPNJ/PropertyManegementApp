package task.AsyncTaskListener;

import task.response.GetNameResponse;

public interface CallbackListener<V> {
    void onPostExecute(V v);
}
