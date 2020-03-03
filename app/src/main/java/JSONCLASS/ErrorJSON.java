package JSONCLASS;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorJSON {

    @JsonProperty("error")
    // TODO キャメルケース
    public String error;

}
