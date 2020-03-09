package task.request;

public class DeletePropertyRequest {

    private final String mUserId;

    private final int mAssetId;

    public DeletePropertyRequest(String userId, int assetId) {
        mUserId = userId;
        mAssetId = assetId;
    }

    public String getUserId() {
        return mUserId;
    }

    public int getAssetId() {
        return mAssetId;
    }

}
