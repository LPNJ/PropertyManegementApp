package JSONCLASS;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ErrorAndUserListJSON {

    @JsonProperty("error")
    public String mError;

    @JsonProperty("userList")
    public ArrayList<String> mNames;

}
