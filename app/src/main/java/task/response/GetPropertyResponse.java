package task.response;

import java.util.ArrayList;

import entity.PropertyInfo;

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
