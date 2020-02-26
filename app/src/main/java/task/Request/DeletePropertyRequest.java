package task.Request;

public class DeletePropertyRequest {

    private final String mUserId;

    private final int mAssetId;

    public DeletePropertyRequest(String mUserId, int mAssetId) {
        this.mUserId = mUserId;
        this.mAssetId = mAssetId;
    }

    public String getUserId() {
        return mUserId;
    }

    public int getAssetId() {
        return mAssetId;
    }

}
