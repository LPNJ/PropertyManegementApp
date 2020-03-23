package task;

import org.json.JSONObject;
import org.junit.Test;

import entity.PropertyInfo;
import request.EditPropertyRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EditPropertyTaskTest {

    @Test
    public void createJsonSuccess() {
        EditPropertyTask task = new EditPropertyTask(null);
        String jsonSt = "{\"data\":\"{\\\""+"mPropertyManager\\\""+":"+"\\\""+"komiyama\\\""+","+"\\\""+"mPropertyUser\\\""+":"+"\\\""+"komiyama\\\""+","+"\\\""+"mLocation\\\""+":"+"\\\""+"新横浜\\\""+","+"\\\""+"mControlNumber\\\""+":"+"\\\""+"\\\""+","+"\\\""+"mProductName\\\""+":"+"\\\""+"ハンディプリンタ\\\""+","+"\\\""+"mPurchaseCategory\\\""+":"+"\\\""+"レンタル\\\""+","+"\\\""+"mPropertyCategory\\\""+":"+"\\\""+"機器\\\""+","+"\\\""+"mComplement\\\""+":"+"\\\""+"特になし\\\""+"}"+"\",\"assetId\":10001,\"userId\":\"komiyama\"}";
        JSONObject json = task.createJson(new EditPropertyRequest("komiyama", 10001, new PropertyInfo("komiyama", "komiyama", "新横浜", "", "ハンディプリンタ", "レンタル", "機器", "特になし")));
        assertThat(json.toString() ,is(jsonSt));
    }

    @Test
    public void parseJsonSuccess() {
        EditPropertyTask task = new EditPropertyTask(null);
        String readSd = "{\"error\":0}";
        String errorCode = task.parseJson(readSd);
        assertThat(errorCode , is("0"));
    }

}