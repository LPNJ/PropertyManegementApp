package json;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO ファイル名がキャメルケースになってない
public class ErrorJson {

    @JsonProperty("error")
    // TODO キャメルケース
    public String mError;

}
