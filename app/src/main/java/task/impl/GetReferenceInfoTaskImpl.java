package task.impl;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import entity.PropertyInfo;
import task.AsyncTaskListener.CallbackListener_getProperties;
import task.AsyncTaskListener.CallbackListener_getReferenceProperty;
import task.response.GetPropertyEntity;
import task.response.GetPropertyResponse;
import task.response.GetReferencePropertyResponse;

public class GetReferenceInfoTaskImpl extends AsyncTask<String, Void, GetReferencePropertyResponse> {

    private CallbackListener_getReferenceProperty listener = null;

    public GetReferenceInfoTaskImpl(CallbackListener_getReferenceProperty listener) {
        this.listener = listener;
    }

    @Override
    protected GetReferencePropertyResponse doInBackground(String... params) {

//        org.json.JSONObject json = new org.json.JSONObject();
//        try {
//            json.put("assetId",params[0]);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String data = json.toString();

        HttpURLConnection con = null;
        URL url = null;

        String urlSt = "http://133.139.115.154:8080/assets/api/asset/"+params[0];
//        String urlSt = "http://133.139.115.154:8080/assets/api/asset/"+"10006";
//        String urlSt = "http://133.139.115.154:8080/event/api/user";

        try {
            // URLの作成
            url = new URL(urlSt);
            // 接続用HttpURLConnectionオブジェクト作成
            con = (HttpURLConnection)url.openConnection();
            // リクエストメソッドの設定
            con.setRequestMethod("GET");
            // リダイレクトを自動で許可しない設定
//            con.setInstanceFollowRedirects(false);
            // URL接続からデータを読み取る場合はtrue
            con.setDoInput(true);
            // URL接続にデータを書き込む場合はtrue
//            con.setDoOutput(true);
            //
            con.setRequestProperty("Content-Type", "application/json");

            // 接続
            con.connect(); // ①

            InputStream in = null;
            try {
                in = con.getInputStream();
            }catch (IOException e){
                e.printStackTrace();
                Log.i("nullかえしてる？","nullだね");
                return null;
            }

            Log.i("ここまでできてる？","できてるかも");

            String readSt = readInputStream(in);
            Log.i("JSON",readSt);



            String returnCode = null;
            ArrayList<GetPropertyEntity> preferenseProperty;
            GetReferencePropertyResponse response = null;

            ObjectMapper mapper = new ObjectMapper();
            try {
                Info_Preferense info = mapper.readValue(readSt,Info_Preferense.class);
                returnCode = info.error;
                preferenseProperty = info.mProperties;
//                Log.i("中身は？",preferenseProperty.get(0).getProperty());
                response = new GetReferencePropertyResponse(returnCode,preferenseProperty.get(0).getProperty());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            con.disconnect();
        }



        return null;
    }

    @Override
    protected void onPostExecute(GetReferencePropertyResponse response) {
        super.onPostExecute(response);

        listener.onPostExecute(response);

    }

    public String readInputStream(InputStream in) throws IOException {
        StringBuffer sb = new StringBuffer();
        String st = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        while((st = br.readLine()) != null)
        {
            sb.append(st);
        }
        try
        {
            in.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return sb.toString();
    }


}

class Info_Preferense {
    @JsonProperty("error")
    public String error;

    @JsonProperty("assetList")
    public ArrayList<GetPropertyEntity> mProperties;


    public String getError() {
        return error;
    }

}
