package net.xwdoor.mobilesafe.net;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by XWdoor on 2016/2/25 025 9:55.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class HttpRequest {

    private final Handler mHandler;
    private RequestCallback mCallback;

    public HttpRequest() {
        mHandler = new Handler();
    }

    /** GET请求 */
    public void requestGet(final String urlStr,RequestCallback callback){
        mCallback = callback;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(urlStr);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(2000);
                    conn.setReadTimeout(2000);
                    conn.connect();

                    int responseCode = conn.getResponseCode();
                    if(responseCode == 200){
                        InputStream inStream = conn.getInputStream();
                        final String content = readString(inStream);
                        if(mCallback!=null){
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mCallback.onSuccess(content);
                                }
                            });
                        }
                    }
                } catch (MalformedURLException e) {
                    //url异常
                    handlerErrorMsg("url异常");
                    e.printStackTrace();
                } catch (IOException e) {
                    //网络异常
                    handlerErrorMsg("网络异常");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /** 请求失败处理 */
    private void handlerErrorMsg(final String errorMsg) {
        if(mCallback!=null){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onFaile(errorMsg);
                }
            });
        }
    }

    /** 从网络流中读取字符串 */
    private String readString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = bufferedReader.readLine())!=null){
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
