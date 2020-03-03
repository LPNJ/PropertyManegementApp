package task.AsyncTaskListener;

import task.response.GetNameResponse;

/**
 * TODO {@link task.ResultListener}と全く一緒。
 * {@link task.ResultListener}かこのクラスどちらか必要ない。
 */
public interface CallbackListener<V> {
    void onPostExecute(V v);
}
