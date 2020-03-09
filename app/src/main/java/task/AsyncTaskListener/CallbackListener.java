package task.AsyncTaskListener;

public interface CallbackListener<V> {
    //onResult
    void onPostExecute(V v);
}
