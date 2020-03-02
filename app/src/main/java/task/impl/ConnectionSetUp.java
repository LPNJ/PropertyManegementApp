package task.impl;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class ConnectionSetUp<V> {
    public JSONObject createOneJson(V v){
        JSONObject json = null;

        return json;
    }


    public HttpURLConnection setup(String method, String URL) throws IOException {

        HttpURLConnection con = null;
        URL url = null;

        url = new URL(URL);
        // 接続用HttpURLConnectionオブジェクト作成
        con = (HttpURLConnection)url.openConnection();
        // リクエストメソッドの設定
        con.setRequestMethod(method);
        // リダイレクトを自動で許可しない設定
//            con.setInstanceFollowRedirects(false);
        // URL接続からデータを読み取る場合はtrue
        con.setDoInput(true);
        // URL接続にデータを書き込む場合はtrue
        if(!method.equals("GET")){
            con.setDoOutput(true);
        }
        con.setRequestProperty("Content-Type", "application/json");

        return con;
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
