package validator;

import java.util.regex.Pattern;
import entity.EditableUserInfo;

public class NewAccountValidator implements Validator<EditableUserInfo> {

    public static final int ERROR_NOT_INPUT = 1;
    public static final int ERROR_INVALID_CHARACTER = 2;
    public static final int ERROR_ID_TOO_LONG_OR_TOO_SHORT = 3;
    public static final int ERROR_PASS_TOO_LONG_OR_TOO_SHORT = 4;
    public static final int ERROR_PASS_NOT_MATCH = 5;

    // 入力長の閾値
    private static final int MAX_ID_LENGTH = 128;
    private static final int MAX_PASS_LENGTH = 128;
    private static final int MIN_ID_LENGTH = 6;
    private static final int MIN_PASS_LENGTH = 8;

    private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");

    @Override
    public int validate(EditableUserInfo data) {
        if (data == null) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " data is null.");
        }
        // 入力がない
        if (data.getUserId() == null || data.getPassword() == null || data.getConfirmationPassword() == null ||
                data.getUserId().isEmpty() || data.getPassword().isEmpty() || data.getConfirmationPassword().isEmpty()) {
            return ERROR_NOT_INPUT;
        }
        // 文字チェック
        if (!pattern.matcher(data.getUserId()).find() || !pattern.matcher(data.getPassword()).find()) {
            return ERROR_INVALID_CHARACTER;
        }
        // IDとパスワードの入力が超過または不足していないかチェックする
        int idError = validateLength(data.getUserId(), MIN_ID_LENGTH, MAX_ID_LENGTH, ERROR_ID_TOO_LONG_OR_TOO_SHORT);
        if (idError != SUCCESS) {
            return idError;
        }
        int passwordError = validateLength(data.getPassword(), MIN_PASS_LENGTH, MAX_PASS_LENGTH, ERROR_PASS_TOO_LONG_OR_TOO_SHORT);
        if (passwordError != SUCCESS) {
            return passwordError;
        }
        // 確認パスワードがずれてないかのチェック
        if (!data.getPassword().equals(data.getConfirmationPassword())) {
            return ERROR_PASS_NOT_MATCH;
        }

        return SUCCESS;
    }

    private int validateLength(String value, int min, int max, int Error) {
        if (value.length() < min) {
            return Error;
        }
        if (value.length() > max) {
            return Error;
        }
        return SUCCESS;
    }

}
