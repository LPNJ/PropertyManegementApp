package task.response;

import java.util.ArrayList;

public class GetNameResponse {

    private final String mError;

    private final ArrayList<String> mNames;

    public GetNameResponse(String error, ArrayList<String> names) {
        this.mError = error;
        this.mNames = names;
    }

    public String getError() {
        return mError;
    }

    public ArrayList<String> getNames() {
        return mNames;
    }



}
