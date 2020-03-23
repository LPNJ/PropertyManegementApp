package task;

import org.json.JSONObject;
import org.junit.Test;

import entity.PropertyInfo;
import request.RegisterPropertyRequest;
import response.RegisterPropertyResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RegisterPropertyInfoTaskTest {

    @Test
    public void createJsonSuccess() {
        RegisterPropertyInfoTask task = new RegisterPropertyInfoTask(null);

        String jsonSt = "{\"data\":\"{\\\""+"mPropertyManager\\\""+":"+"\\\""+"komiyama\\\""+","+"\\\""+"mPropertyUser\\\""+":"+"\\\""+"komiyama\\\""+","+"\\\""+"mLocation\\\""+":"+"\\\""+"新横浜\\\""+","+"\\\""+"mControlNumber\\\""+":"+"\\\""+"\\\""+","+"\\\""+"mProductName\\\""+":"+"\\\""+"ハンディプリンタ\\\""+","+"\\\""+"mPurchaseCategory\\\""+":"+"\\\""+"レンタル\\\""+","+"\\\""+"mPropertyCategory\\\""+":"+"\\\""+"機器\\\""+","+"\\\""+"mComplement\\\""+":"+"\\\""+"特になし\\\""+"}"+"\",\"userId\":\"komiyama\"}";

        PropertyInfo info = new PropertyInfo("komiyama",
                "komiyama",
                "新横浜",
                "",
                "ハンディプリンタ",
                "レンタル",
                "機器",
                "特になし");
        RegisterPropertyRequest request = new RegisterPropertyRequest("komiyama",info);

        JSONObject json;
        json = task.createJson(request);
        assertThat(json.toString() , is(jsonSt));
    }

    @Test
    public void parseJsonSuccess() {
        RegisterPropertyInfoTask task = new RegisterPropertyInfoTask(null);

        String readSd = "{\"error\":0,\"assetId\":10011}";
        RegisterPropertyResponse errorCode = task.parseJson(readSd);
        assertThat(errorCode.getError() , is("0"));
    }

}