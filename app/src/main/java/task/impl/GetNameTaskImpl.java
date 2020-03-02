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

import JSONCLASS.ErrorAndUserListJSON;
import task.AsyncTaskListener.CallbackListener;
import task.response.GetNameResponse;

public class GetNameTaskImpl extends AsyncTask<String, Void, GetNameResponse> {

    private CallbackListener<GetNameResponse> listener = null;

    public GetNameTaskImpl(CallbackListener<GetNameResponse> listener) {
        this.listener = listener;
    }

    @Override
    protected GetNameResponse doInBackground(String... strings) {

        ConnectionSetUp cs = new ConnectionSetUp();
        HttpURLConnection con = null;

        try {

            try {
                con = cs.setup("GET",new URLList().getName());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 接続
            con.connect(); // ①

            InputStream in = null;
            in = con.getInputStream();
            String readSt = cs.readInputStream(in);

            GetNameResponse response = null;
            String returnCode = null;
            ArrayList<String> names;

            ObjectMapper mapper = new ObjectMapper();
            try {
                ErrorAndUserListJSON info = mapper.readValue(readSt, ErrorAndUserListJSON.class);
                returnCode = info.mError;
                names = info.mNames;
                response = new GetNameResponse(returnCode,names);
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
    protected void onPostExecute(GetNameResponse result) {

        listener.onPostExecute(result);

    }

}


