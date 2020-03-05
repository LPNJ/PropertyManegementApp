// TODO ここもパッケージ名が他と違う、ほかはぜんぶこもじなので統一する
package task.Request;

public class DeletePropertyRequest {

    private final String mUserId;

    private final int mAssetId;

    // TODO 引数にm付けない、thisいらない、ほかも同様
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
