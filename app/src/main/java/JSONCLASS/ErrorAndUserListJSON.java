package jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

// TODO ファイル名がキャメルケースになってない
public class ErrorAndUserListJson {

    @JsonProperty("error")
    public String mError;

    @JsonProperty("userList")
    public ArrayList<String> mNames;

}
