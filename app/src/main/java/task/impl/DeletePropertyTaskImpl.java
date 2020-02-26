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

import task.AsyncTaskListener.CallbackListener_Delete;
import task.Request.DeletePropertyRequest;
import task.response.GetNameResponse;

public class DeletePropertyTaskImpl extends AsyncTask<DeletePropertyRequest, Void, String> {

    private CallbackListener_Delete listener = null;

    public DeletePropertyTaskImpl(CallbackListener_Delete listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(DeletePropertyRequest... params) {

        org.json.JSONObject json = new org.json.JSONObject();
        try {
            json.put("userId",params[0].getUserId());
            json.put("assetId",params[0].getAssetId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String data = json.toString();

        HttpURLConnection con = null;
        URL url = null;

        String urlSt = "http://133.139.115.154:8080/assets/api/asset";
//        String urlSt = "http://133.139.115.154:8080/event/api/user";

        try {
            // URLの作成
            url = new URL(urlSt);
            // 接続用HttpURLConnectionオブジェクト作成
            con = (HttpURLConnection)url.openConnection();
            // リクエストメソッドの設定
            con.setRequestMethod("DELETE");
            // リダイレクトを自動で許可しない設定
//            con.setInstanceFollowRedirects(false);
            // URL接続からデータを読み取る場合はtrue
            con.setDoInput(true);
            // URL接続にデータを書き込む場合はtrue
            con.setDoOutput(true);
            //
            con.setRequestProperty("Content-Type", "application/json");
            //
            OutputStream os = con.getOutputStream();
            // 送信するデータの設定
            os.write(data.getBytes());
            //
            os.flush();
            //
            os.close();

            // 接続
            con.connect(); // ①

            Log.i("ステータスコードは？",String.valueOf(con.getResponseCode()));


            InputStream in = con.getInputStream();
            String readSt = readInputStream(in);
            Log.i("JSON",readSt);

            String returnCode = null;

            ObjectMapper mapper = new ObjectMapper();
            try {
                Info_Delete info = mapper.readValue(readSt,Info_Delete.class);
                returnCode = info.error;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("RETURNCODE",returnCode);
            return returnCode;

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
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

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

class Info_Delete {
    @JsonProperty("error")
    public String error;

    public String getError() {
        return error;
    }

}
