package task;

import org.junit.Test;

import response.GetPropertyResponse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetPropertyInfoTaskTest {

    @Test
    public void parseJsonGetErrorCodeSuccess() {
        GetPropertyInfoTask task = new GetPropertyInfoTask(null);

        String readSd =
                "{"+"\"error\""+":"+0+","+"\"assetList\""+":"+"["+"{"+"\"userId\""+":"+"\"komiyama\""+","+"\"assetId\""+":"+10001+","+"\"data\""+":"+"\"{\\\"mPropertyManager\\\":\\\"komiyama\\\",\\\"mPropertyUser\\\":\\\"komiyama\\\",\\\"mLocation\\\":\\\"shinyoko\\\",\\\"mControlNumber\\\":\\\"\\\",\\\"mProductName\\\":\\\"handyprinter\\\",\\\"mPurchaseCategory\\\":\\\"レンタル\\\",\\\"mPropertyCategory\\\":\\\"ディスプレイ\\\",\\\"mComplement\\\":\\\"\\\"}\"}]}";
        GetPropertyResponse errorCode = task.parseJson(readSd);
        assertThat(errorCode.getError() , is("0"));
    }

    @Test
    public void parseJsonGetUserIdSuccess() {
        GetPropertyInfoTask task = new GetPropertyInfoTask(null);

        String readSd =
                "{"+"\"error\""+":"+0+","+"\"assetList\""+":"+"["+"{"+"\"userId\""+":"+"\"komiyama\""+","+"\"assetId\""+":"+10001+","+"\"data\""+":"+"\"{\\\"mPropertyManager\\\":\\\"komiyama\\\",\\\"mPropertyUser\\\":\\\"komiyama\\\",\\\"mLocation\\\":\\\"shinyoko\\\",\\\"mControlNumber\\\":\\\"\\\",\\\"mProductName\\\":\\\"handyprinter\\\",\\\"mPurchaseCategory\\\":\\\"レンタル\\\",\\\"mPropertyCategory\\\":\\\"ディスプレイ\\\",\\\"mComplement\\\":\\\"\\\"}\"}]}";
        GetPropertyResponse errorCode = task.parseJson(readSd);
        assertThat(errorCode.getInfos().get(0).mUserId , is("komiyama"));
    }

    @Test
    public void parseJsonGetAssetIdSuccess() {
        GetPropertyInfoTask task = new GetPropertyInfoTask(null);

        String readSd =
                "{"+"\"error\""+":"+0+","+"\"assetList\""+":"+"["+"{"+"\"userId\""+":"+"\"komiyama\""+","+"\"assetId\""+":"+10001+","+"\"data\""+":"+"\"{\\\"mPropertyManager\\\":\\\"komiyama\\\",\\\"mPropertyUser\\\":\\\"komiyama\\\",\\\"mLocation\\\":\\\"shinyoko\\\",\\\"mControlNumber\\\":\\\"\\\",\\\"mProductName\\\":\\\"handyprinter\\\",\\\"mPurchaseCategory\\\":\\\"レンタル\\\",\\\"mPropertyCategory\\\":\\\"ディスプレイ\\\",\\\"mComplement\\\":\\\"\\\"}\"}]}";
        GetPropertyResponse errorCode = task.parseJson(readSd);
        assertThat(errorCode.getInfos().get(0).mAssetId.toString() , is("10001"));
    }

    @Test
    public void parseJsonGetPropertyInfoSuccess() {
        GetPropertyInfoTask task = new GetPropertyInfoTask(null);

        String readSd =
                "{"+"\"error\""+":"+0+","+"\"assetList\""+":"+"["+"{"+"\"userId\""+":"+"\"komiyama\""+","+"\"assetId\""+":"+10001+","+"\"data\""+":"+"\"{\\\"mPropertyManager\\\":\\\"komiyama\\\",\\\"mPropertyUser\\\":\\\"komiyama\\\",\\\"mLocation\\\":\\\"shinyoko\\\",\\\"mControlNumber\\\":\\\"\\\",\\\"mProductName\\\":\\\"handyprinter\\\",\\\"mPurchaseCategory\\\":\\\"レンタル\\\",\\\"mPropertyCategory\\\":\\\"ディスプレイ\\\",\\\"mComplement\\\":\\\"\\\"}\"}]}";
        GetPropertyResponse errorCode = task.parseJson(readSd);
        assertThat(errorCode.getInfos().get(0).mProperty , is("{"+"\"mPropertyManager\""+":"+"\"komiyama\""+","+"\"mPropertyUser\""+":"+"\"komiyama\""+","+"\"mLocation\""+":"+"\"shinyoko\""+","+"\"mControlNumber\""+":"+"\"\""+","+"\"mProductName\""+":"+"\"handyprinter\""+","+"\"mPurchaseCategory\""+":"+"\"レンタル\""+","+"\"mPropertyCategory\""+":"+"\"ディスプレイ\""+","+"\"mComplement\""+":"+"\"\""+"}"));
    }

}