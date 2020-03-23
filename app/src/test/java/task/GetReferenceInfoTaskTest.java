package task;

import org.junit.Test;

import response.GetReferencePropertyResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetReferenceInfoTaskTest {

    @Test
    public void parseJsonGetErrorCodeSuccess() {
        GetReferenceInfoTask task = new GetReferenceInfoTask(null,null);

        String readSd =
                "{"+"\"error\""+":"+0+","+"\"assetList\""+":"+"["+"{"+"\"userId\""+":"+"\"komiyama\""+","+"\"assetId\""+":"+10006+","+"\"data\""+":"+"\"{\\\"mPropertyManager\\\":\\\"kawasaki\\\",\\\"mPropertyUser\\\":\\\"kawasaki\\\",\\\"mLocation\\\":\\\"aaa\\\",\\\"mControlNumber\\\":\\\"\\\",\\\"mProductName\\\":\\\"aaa\\\",\\\"mPurchaseCategory\\\":\\\"レンタル\\\",\\\"mPropertyCategory\\\":\\\"ディスプレイ\\\",\\\"mComplement\\\":\\\"aaa\\\"}"+"\"}"+"]"+"}";

        GetReferencePropertyResponse errorCode = task.parseJson(readSd);
        assertThat(errorCode.getError() , is("0"));
    }

    @Test
    public void parseJsonGetPropertyInfoSuccess() {
        GetReferenceInfoTask task = new GetReferenceInfoTask(null,null);

        String readSd =
                "{"+"\"error\""+":"+0+","+"\"assetList\""+":"+"["+"{"+"\"userId\""+":"+"\"komiyama\""+","+"\"assetId\""+":"+10006+","+"\"data\""+":"+"\"{\\\"mPropertyManager\\\":\\\"kawasaki\\\",\\\"mPropertyUser\\\":\\\"kawasaki\\\",\\\"mLocation\\\":\\\"aaa\\\",\\\"mControlNumber\\\":\\\"\\\",\\\"mProductName\\\":\\\"aaa\\\",\\\"mPurchaseCategory\\\":\\\"レンタル\\\",\\\"mPropertyCategory\\\":\\\"ディスプレイ\\\",\\\"mComplement\\\":\\\"aaa\\\"}"+"\"}"+"]"+"}";

        GetReferencePropertyResponse errorCode = task.parseJson(readSd);
        assertThat(errorCode.getInfo().mPropertyManager, is("kawasaki"));
        assertThat(errorCode.getInfo().mPropertyUser, is("kawasaki"));
        assertThat(errorCode.getInfo().mLocation, is("aaa"));
        assertThat(errorCode.getInfo().mProductName, is("aaa"));
        assertThat(errorCode.getInfo().mPurchaseCategory, is("レンタル"));
        assertThat(errorCode.getInfo().mPropertyCategory, is("ディスプレイ"));
        assertThat(errorCode.getInfo().mComplement, is("aaa"));


    }
}