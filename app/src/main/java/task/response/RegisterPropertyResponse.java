package task.response;

public class RegisterPropertyResponse {

    private final String mError;

    private final String mControlNumber;

    public RegisterPropertyResponse(String error, String controlNumber) {
        this.mError = error;
        this.mControlNumber = controlNumber;
    }

    public String getError() {
        return mError;
    }
    
    public String getControlNumber() {
        return mControlNumber;
    }

}
