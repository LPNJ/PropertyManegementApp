package task;

import task.AsyncTaskListener.CallbackListener;

// TODO　このクラス必要ないから消す
public interface Executer<V> {

    /**
     * メインスレッドとは異なる非同期処理（通信など）を実施し、ResultListener に対して
     * 実行結果を渡す処理。
     */
    void execute(V v);

}
