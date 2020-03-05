package jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorJson {

    @JsonProperty("error")
    // TODO キャメルケース
    public String error;

}
