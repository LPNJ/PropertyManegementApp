package task.AsyncTaskListener;

/**
 * TODO {@link task.ResultListener}と全く一緒。
 * {@link task.ResultListener}かこのクラスどちらか必要ない。
 */
public interface CallbackListener<V> {
    //onResult
    void onPostExecute(V v);
}
