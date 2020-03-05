// TODO 実際のパッケージフォルダ名が変わってない、なんでここだけclassってついとるん？
// TODO jsonっていうパッケージでいいんじゃない？JsonResolutionってやつもそのパッケージにまとめたらいいと思う
package jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

// TODO キャメルケース ErrorAndAssetIdJson
public class ErrorAndAssetidJSON {

    @JsonProperty("error")
    public String mError;

    @JsonProperty("assetId")
    public String mControlNumber;

}
