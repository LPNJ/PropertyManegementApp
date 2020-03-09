package entity;

public class EditableUserInfo extends UserInfo {

    private final String mConfirmationPassword;

    /**
     * コンストラクタ
     * @param userId
     * @param password
     * @param mConfirmationPassword
     */
    public EditableUserInfo(String userId, String password, String mConfirmationPassword) {
        super(userId, password);
        this.mConfirmationPassword = mConfirmationPassword;
    }

    public String getConfirmationPassword() {
        return mConfirmationPassword;
    }
}
