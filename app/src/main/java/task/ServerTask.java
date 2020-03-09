package task;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Target;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import task.AsyncTaskListener.CallbackListener;

abstract class ServerTask<V, R> extends AsyncTask<V, Void, R> {
    private final CallbackListener<R> mListener;

    private final String mMethod;

    private final String mUrl;

    private final String mGET = "GET";

    private static final String TAG = "ServerTask";

    public ServerTask(CallbackListener<R> listener, String method , String Url) {
        mMethod = method;
        mListener = listener;
        mUrl = Url;
    }

    public ServerTask(CallbackListener<R> listener, RequestType type) {
        mMethod = type.getMethod();
        mListener = listener;
        mUrl = type.getUrl();
    }

    @Override
    protected R doInBackground(V... params) {
        JSONObject json = null;
        if(!mMethod.equals(mGET)) {
            json = createJson(params[0]);
        }

        HttpURLConnection con = null;

        try {
                // URLの作成
            con = setup(mMethod,mUrl);
            if(!mMethod.equals(mGET) && json != null) {
                dataOutPut(con, json.toString());
            }
            // 接続
            con.connect();

            if(con.getResponseCode() != 200){
                return returnErrorCode();
            }

            return parseJson(dataInput(con));
        } catch (MalformedURLException e) {
            Log.e(TAG, "Illegal url :" + mUrl, e);
            throw new IllegalStateException("Illegal url :" + mUrl);
        } catch (IOException e) {
            Log.e(TAG, "IOException occurred." , e);
            return returnErrorCode();
        }
        finally {
            con.disconnect();
        }
    }

    @Override
    protected void onPostExecute(R result) {
        mListener.onPostExecute(result);
    }

    abstract JSONObject createJson(V v);

    abstract R parseJson(String readSd);

    abstract R returnErrorCode();

    public String dataInput(HttpURLConnection con) throws IOException {
        InputStream in = con.getInputStream();
        try {
            String readSt = readInputStream(in);
            return readSt;
        } catch (IOException e) {
            Log.e(TAG, "IOException occurred." , e);
        }
        return null;
    }

    public void dataOutPut(HttpURLConnection con, String data) throws IOException {
        OutputStream os = con.getOutputStream();
        // 送信するデータの設定
        os.write(data.getBytes());
        os.flush();
        os.close();
    }

    public HttpURLConnection setup(String method, String URL) throws IOException {

        HttpURLConnection con = null;
        java.net.URL url = null;
        url = new URL(URL);
        // 接続用HttpURLConnectionオブジェクト作成
        con = (HttpURLConnection)url.openConnection();
        // リクエストメソッドの設定
        con.setRequestMethod(method);
        // URL接続からデータを読み取る場合はtrue
        con.setDoInput(true);
        // URL接続にデータを書き込む場合はtrue
        if(!method.equals(mGET)){
            con.setDoOutput(true);
        }
        con.setRequestProperty("Content-Type", "application/json");
        return con;
    }

    public String readInputStream(InputStream in) throws IOException {
        StringBuffer sb = new StringBuffer();
        String st = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        while((st = br.readLine()) != null) {
            sb.append(st);
        }
        try {
            in.close();
        } catch(Exception e) {
            Log.e(TAG, "Exception occurred." , e);
        }

        return sb.toString();
    }
}
