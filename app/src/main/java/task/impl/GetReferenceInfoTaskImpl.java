package task.impl;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import JSONCLASS.ErrorAndAssetslistJSON;
import task.AsyncTaskListener.CallbackListener;
import task.response.GetPropertyEntity;
import task.response.GetReferencePropertyResponse;

public class GetReferenceInfoTaskImpl extends AsyncTask<String, Void, GetReferencePropertyResponse> {

    private CallbackListener<GetReferencePropertyResponse> listener = null;

    public GetReferenceInfoTaskImpl(CallbackListener<GetReferencePropertyResponse> listener) {
        this.listener = listener;
    }

    @Override
    protected GetReferencePropertyResponse doInBackground(String... params) {

        ConnectionSetUp cs = new ConnectionSetUp();

        HttpURLConnection con = null;

        try {

            try {
                con = cs.setup("GET",new URLList().getReference(params[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 接続
            con.connect(); // ①

            InputStream in = con.getInputStream();
            String readSt = cs.readInputStream(in);

            String returnCode = null;
            ArrayList<GetPropertyEntity> preferenseProperty;
            GetReferencePropertyResponse response = null;

            ObjectMapper mapper = new ObjectMapper();
            try {
                ErrorAndAssetslistJSON info = mapper.readValue(readSt,ErrorAndAssetslistJSON.class);
                returnCode = info.mError;
                preferenseProperty = info.mProperties;
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

}