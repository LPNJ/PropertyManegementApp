package task.impl;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import JSONCLASS.ErrorAndAssetidJSON;
import task.AsyncTaskListener.CallbackListener;
import task.Request.RegisterPropertyRequest;
import task.response.RegisterPropertyResponse;

public class RegisterPropertyInfoTaskImpl extends AsyncTask<RegisterPropertyRequest, Void, RegisterPropertyResponse> {

    private CallbackListener<RegisterPropertyResponse> listener = null;

    public RegisterPropertyInfoTaskImpl(CallbackListener<RegisterPropertyResponse> listener) {
        this.listener = listener;
    }

    @Override
    protected RegisterPropertyResponse doInBackground(RegisterPropertyRequest... params) {

        org.json.JSONObject json = new org.json.JSONObject();
        try {
            json.put("userId",params[0].getError());
            json.put("data",params[0].getInfos().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionSetUp cs = new ConnectionSetUp();

        String data = json.toString();

        HttpURLConnection con = null;

        try {

            try {
                con = cs.setup("POST",new URLList().getRegist());
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

            RegisterPropertyResponse response = null;
            String returnCode = null;
            String assetId = null;

            ObjectMapper mapper = new ObjectMapper();
            try {
                ErrorAndAssetidJSON info = mapper.readValue(readSt,ErrorAndAssetidJSON.class);
                returnCode = info.mError;
                assetId = info.mControlNumber;
                response = new RegisterPropertyResponse(returnCode,assetId);
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
    protected void onPostExecute(RegisterPropertyResponse result) {

        listener.onPostExecute(result);

    }

}
