package task.response;

import java.util.ArrayList;

public class GetNameResponse {

    private final String mError;

    private final ArrayList<String> mNames;

    public GetNameResponse(String mError, ArrayList<String> mNames) {
        this.mError = mError;
        this.mNames = mNames;
    }

    public String getError() {
        return mError;
    }

    public ArrayList<String> getNames() {
        return mNames;
    }



}
