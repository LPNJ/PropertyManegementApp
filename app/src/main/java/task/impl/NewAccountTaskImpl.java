package task.impl;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import JSONCLASS.ErrorJSON;
import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;

public class NewAccountTaskImpl extends AsyncTask<UserInfo, Void, String> {

    private CallbackListener<String> listener = null;

    public NewAccountTaskImpl(CallbackListener<String> listener) {
        this.listener = listener;
    }


    @Override
    protected String doInBackground(UserInfo... params) {

        org.json.JSONObject json = new org.json.JSONObject();
        try {
            json.put("userId",params[0].getUserId());
            json.put("password",params[0].getPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionSetUp cs = new ConnectionSetUp();

        String data = json.toString();

        HttpURLConnection con = null;

        try {

            try {
                con = cs.setup("POST",new URLList().getNewAccount());
            } catch (IOException e) {
                e.printStackTrace();
            }

            OutputStream os = con.getOutputStream();
            // 送信するデータの設定
            os.write(data.getBytes());
            os.flush();
            os.close();

            // 接続
            con.connect(); // ①

            InputStream in = con.getInputStream();
            String readSt = cs.readInputStream(in);

            String returnCode = null;

            ObjectMapper mapper = new ObjectMapper();
            try {
                ErrorJSON info = mapper.readValue(readSt,ErrorJSON.class);
                returnCode = info.error;
            } catch (IOException e) {
                e.printStackTrace();
            }
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

        listener.onPostExecute(result);

    }

}
