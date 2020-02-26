package task.serialize;

public class PropertyRegistResponse {

    private final int mError;

    private final String mControlNumber;


    public PropertyRegistResponse(int mError, String mControlNumber) {
        this.mError = mError;
        this.mControlNumber = mControlNumber;
    }

    public int getError() {
        return mError;
    }

    public String getControlNumber() {
        return mControlNumber;
    }

}
