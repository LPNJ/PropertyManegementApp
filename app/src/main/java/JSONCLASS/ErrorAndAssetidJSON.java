// TODO パッケージ名ここだけなぜか全部大文字なので他と統一する
package JSONCLASS;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO iを大文字にするキャメルケース、JSONもキャメルケースに当てはめる
public class ErrorAndAssetidJSON {

    @JsonProperty("error")
    public String mError;

    // TODO wannt ほかのところもやけどフィールド名assetIdに統一したほうがいいんじゃない？
    @JsonProperty("assetId")
    public String mControlNumber;

}
