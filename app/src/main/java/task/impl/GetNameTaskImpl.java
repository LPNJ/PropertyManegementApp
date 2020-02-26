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

import task.AsyncTaskListener.CallbackListener_GetNames;
import task.AsyncTaskListener.CallbackListener_OneElement;
import task.response.GetNameResponse;

public class GetNameTaskImpl extends AsyncTask<String, Void, GetNameResponse> {

    private CallbackListener_GetNames listener = null;

    public GetNameTaskImpl(CallbackListener_GetNames listener) {
        this.listener = listener;
    }

    @Override
    protected GetNameResponse doInBackground(String... strings) {

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

        String urlSt = "http://133.139.115.154:8080/assets/api/userlist";
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



            GetNameResponse response = null;
            String returnCode = null;
            ArrayList<String> names;

            ObjectMapper mapper = new ObjectMapper();
            try {
                Info_Names info = mapper.readValue(readSt,Info_Names.class);
                returnCode = info.mError;
                names = info.mNames;
                response = new GetNameResponse(returnCode,names);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("RETURNCODE",returnCode);
            Log.i("RETURNNAMES",response.getNames().get(0));
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
    protected void onPostExecute(GetNameResponse result) {

        // doInBackground後処理
//        Log.i("RESULT",result);
        listener.onPostExecute(result);

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

class Info_Names {
    @JsonProperty("error")
    public String mError;

    @JsonProperty("userList")
    public ArrayList<String> mNames;

    public String getError() {
        return mError;
    }

    public ArrayList<String> getControlNumber() {
        return mNames;
    }

}



