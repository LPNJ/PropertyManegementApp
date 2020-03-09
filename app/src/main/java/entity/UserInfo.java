package entity;

public class UserInfo {

    private static final String TAG = "UserInfo";
    private final String mUserId;
    private final String mPassword;

    /**
     * コンストラクタ
     */
    public UserInfo(String userId, String password) {
        mUserId = userId;
        mPassword = password;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getPassword() {
        return mPassword;
    }

}
