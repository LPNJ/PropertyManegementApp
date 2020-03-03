package task;

import entity.UserInfo;

/**
 * TODO モックのためだけのクラスは必要ない。他も同様。
 * このインターフェスを実装して「実際にサーバーに通信するクラス」と「サーバー通信をモックするクラス」という
 * のが正しい。
 */
public interface LoginTask extends Executer<UserInfo>{
}
