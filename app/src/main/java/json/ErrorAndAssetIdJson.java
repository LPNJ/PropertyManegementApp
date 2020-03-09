// TODO 実際のパッケージフォルダ名が変わってない、なんでここだけclassってついとるん？
// TODO jsonっていうパッケージでいいんじゃない？JsonResolutionってやつもそのパッケージにまとめたらいいと思う
package json;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO キャメルケース ErrorAndAssetIdJson
public class ErrorAndAssetIdJson {

    @JsonProperty("error")
    public String mError;

    @JsonProperty("assetId")
    public String mControlNumber;

}
