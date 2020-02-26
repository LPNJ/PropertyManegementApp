package task.response;

public class RegisterPropertyResponse {

    private final String mError;

    private final String mControlNumber;

    public RegisterPropertyResponse(String mError, String mControlNumber) {
        this.mError = mError;
        this.mControlNumber = mControlNumber;
    }

    public String getmError() {
        return mError;
    }

    public String getmControlNumber() {
        return mControlNumber;
    }

}
