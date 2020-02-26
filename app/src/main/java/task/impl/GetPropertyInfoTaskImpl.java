package task.impl;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import entity.PropertyInfo;
import task.AsyncTaskListener.CallbackListener_GetNames;
import task.AsyncTaskListener.CallbackListener_getProperties;
import task.GetPropertyInfoTask;
import task.response.GetNameResponse;
import task.response.GetPropertyEntity;
import task.response.GetPropertyResponse;

public class GetPropertyInfoTaskImpl extends AsyncTask<String, Void, GetPropertyResponse> {

    private CallbackListener_getProperties listener = null;

    public GetPropertyInfoTaskImpl(CallbackListener_getProperties listener) {
        this.listener = listener;
    }


    @Override
    protected GetPropertyResponse doInBackground(String... strings) {

        //        org.json.JSONObject json = new org.json.JSONObject();
//        try {
//            json.put("userId",params[0].getUserId());
//            json.put("password",params[0].getPassword());

//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        String data = json.toString();

        HttpURLConnection con = null;
        URL url = null;

        String urlSt = "http://133.139.115.154:8080/assets/api/assetlist";
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

            String readSt = readInputStream(in);
            Log.i("JSON",readSt);



            GetPropertyResponse response = null;
            String returnCode = null;
            ArrayList<GetPropertyEntity> properties = null;

            ObjectMapper mapper = new ObjectMapper();
            try {
                Info_GetProperties info = mapper.readValue(readSt,Info_GetProperties.class);
                returnCode = info.mError;
                Log.i("RETURNCODE",returnCode);
                properties = info.mProperties;
                Log.i("RETURNPROPERTIES",properties.get(0).getProperty().toString());
                response = new GetPropertyResponse(returnCode,properties);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("RETURNCODE",returnCode);
            Log.i("RETURNPROPERTIES",properties.get(0).getProperty());

            Log.i("ここまでできてる？","できてるかも");
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

    @Override
    protected void onPostExecute(GetPropertyResponse response) {
        super.onPostExecute(response);

        listener.onPostExecute(response);

    }
}

class Info_GetProperties {
    @JsonProperty("error")
    public String mError;

    @JsonProperty("assetList")
    public ArrayList<GetPropertyEntity> mProperties;
//    public ArrayList<String> mProperties;

    public String getError() {
        return mError;
    }

    public ArrayList<GetPropertyEntity> getProperties() {
        return mProperties;
    }

//    public ArrayList<String> getProperties() {
//        return mProperties;
//    }

}
