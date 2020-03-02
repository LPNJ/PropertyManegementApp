package task.impl;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import JSONCLASS.ErrorAndAssetslistJSON;
import task.AsyncTaskListener.CallbackListener;
import task.response.GetPropertyEntity;
import task.response.GetPropertyResponse;

public class GetPropertyInfoTaskImpl extends AsyncTask<String, Void, GetPropertyResponse> {

    private CallbackListener<GetPropertyResponse> listener = null;

    public GetPropertyInfoTaskImpl(CallbackListener<GetPropertyResponse> listener) {
        this.listener = listener;
    }


    @Override
    protected GetPropertyResponse doInBackground(String... strings) {

        HttpURLConnection con = null;
        ConnectionSetUp cs = new ConnectionSetUp();

        try {

            try {
                con = cs.setup("GET",new URLList().getPropertyInfo());
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 接続
            con.connect(); // ①

            InputStream in = null;

            in = con.getInputStream();

            String readSt = cs.readInputStream(in);
            Log.i("JSON",readSt);

            GetPropertyResponse response = null;
            String returnCode = null;
            ArrayList<GetPropertyEntity> properties = null;

            ObjectMapper mapper = new ObjectMapper();
            try {
                ErrorAndAssetslistJSON info = mapper.readValue(readSt, ErrorAndAssetslistJSON.class);
                returnCode = info.mError;
                properties = info.mProperties;
                response = new GetPropertyResponse(returnCode,properties);
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
    protected void onPostExecute(GetPropertyResponse response) {
        super.onPostExecute(response);

        listener.onPostExecute(response);

    }
}
