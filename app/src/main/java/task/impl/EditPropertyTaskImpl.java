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

import JSONCLASS.ErrorJSON;
import task.AsyncTaskListener.CallbackListener;
import task.Request.DeletePropertyRequest;
import task.Request.EditPropertyRequest;

public class EditPropertyTaskImpl extends AsyncTask<EditPropertyRequest, Void, String> {

    private CallbackListener<String> listener = null;

    public EditPropertyTaskImpl(CallbackListener<String> listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(EditPropertyRequest... params) {

        ConnectionSetUp cs = new ConnectionSetUp();

        org.json.JSONObject json = new org.json.JSONObject();
        try {
            json.put("userId",params[0].getUserId());
            json.put("assetId",params[0].getAssetId());
            json.put("data",params[0].getmPropertyInfo().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String data = json.toString();

        HttpURLConnection con = null;

        try {

            try {
                con = cs.setup("PUT",new URLList().getEdit());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //
            OutputStream os = con.getOutputStream();
            // 送信するデータの設定
            os.write(data.getBytes());
            os.flush();
            os.close();

            // 接続
            con.connect(); // ①

            InputStream in = con.getInputStream();
            String readSt = cs.readInputStream(in);
            Log.i("JSON",readSt);

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
        super.onPostExecute(result);

        listener.onPostExecute(result);
    }

}
