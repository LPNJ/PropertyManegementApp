package task.serialize;

public class PropertyDeleteRequest {

    private final String mUserId;
    private final String mControlNumber;

    public PropertyDeleteRequest(String mUserId, String mControlNumber) {
        this.mUserId = mUserId;
        this.mControlNumber = mControlNumber;
    }

    public String getmUserId() {
        return mUserId;
    }

    public String getmControlNumber() {
        return mControlNumber;
    }

}
