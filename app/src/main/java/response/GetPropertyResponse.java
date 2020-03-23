package response;

import java.util.ArrayList;

import json.GetPropertyJson;

public class GetPropertyResponse {

    private final String mError;

    private final ArrayList<GetPropertyJson> mInfos;

    public GetPropertyResponse(String error, ArrayList<GetPropertyJson> infos) {
        this.mError = error;
        this.mInfos = infos;
    }

    public String getError() {
        return mError;
    }

    public ArrayList<GetPropertyJson> getInfos() {
        return mInfos;
    }

}
