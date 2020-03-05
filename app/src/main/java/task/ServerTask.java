package task;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import task.AsyncTaskListener.CallbackListener;

abstract class ServerTask<V, R> extends AsyncTask<V, Void, R> {
    private final CallbackListener<R> mListener;

    private final String mMethod;

    private final String mUrl;

    public ServerTask(CallbackListener<R> listener, RequestType type) {
        mMethod = type.getMethod();
        mListener = listener;
        mUrl = type.getUrl();
    }

    public ServerTask(CallbackListener<R> listener, String url, String method) {
        mMethod = method;
        mListener = listener;
        mUrl = url;
    }

    @Override
    protected R doInBackground(V... params) {
        JSONObject json = null;
        if(!mMethod.equals("GET")) {
            json = createJson(params[0]);
        }

        HttpURLConnection con = null;

        try {
            try {
                // URLの作成
                con = setup(mMethod,mUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!mMethod.equals("GET") && json != null) {
                dataOutPut(con, json.toString());
            }
            // 接続
            con.connect();
            // TODO 200以外ならサーバーエラーコードを持つレスポンスクラスを返す
            // TODO エラーコードをもつレスポンスクラスはabstract methodで生成

            if(con.getResponseCode() != 200){
                return ooo();
            }

            // TODO parseJsonでnullが返ってくる場合を考慮して実装する
            return parseJson(dataInput(con));
        } catch (MalformedURLException e) {
            Log.e("ServTask", "Illegal url :" + mUrl, e);
            throw new IllegalStateException("Illegal url :" + mUrl);
        } catch (IOException e) {
            Log.e("ServTask", "IOException occurred." , e);
            // TODO サーバーエラーコードを持つレスポンスクラスを返す
        }
        finally {
            con.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(R result) {
        mListener.onPostExecute(result);
    }

    abstract JSONObject createJson(V v);

    abstract R parseJson(String readSd);



    public String dataInput(HttpURLConnection con) throws IOException {
        InputStream in = con.getInputStream();
        try {
            String readSt = readInputStream(in);
            return readSt;
        } catch (IOException e) {
            e.printStackTrace();
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
