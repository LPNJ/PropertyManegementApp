// TODO Requestとserializeパッケージの違いは何？分ける必要はある？
package task.response;

import java.util.ArrayList;

public class GetPropertyResponse {

    private final String mError;

    private final ArrayList<GetPropertyEntity> mInfos;

    public GetPropertyResponse(String mError, ArrayList<GetPropertyEntity> mInfos) {
        this.mError = mError;
        this.mInfos = mInfos;
    }

    public String getError() {
        return mError;
    }

    public ArrayList<GetPropertyEntity> getInfos() {
        return mInfos;
    }

}
