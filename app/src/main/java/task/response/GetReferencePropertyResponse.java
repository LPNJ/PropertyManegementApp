package task.response;

import java.util.ArrayList;

public class GetReferencePropertyResponse {

    private final String mError;

    private final String mInfo;

    public GetReferencePropertyResponse(String mError, String mInfo) {
        this.mError = mError;
        this.mInfo = mInfo;
    }

    public String getError() {
        return mError;
    }

    public String getInfos() {
        return mInfo;
    }

}
