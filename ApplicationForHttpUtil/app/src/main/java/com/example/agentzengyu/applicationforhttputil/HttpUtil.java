package com.example.agentzengyu.applicationforhttputil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZengYu on 2017/5/3.
 */

public class HttpUtil{
    private HttpExecute httpExecute = new HttpExecute();
    private String urlString = "";
    private int CONNECT_TIME_OUT = 10000;
    private int READ_TIME_OUT = 2000;
    Map<String, Object> requestParamsMap = new HashMap<>();
    HttpURLConnection httpUrlConnection = null;

    public HttpUtil setUrl(String urlString) {
        this.urlString = urlString;
        return this;
    }

    public HttpUtil setConnectTimeOut(int time) {
        this.CONNECT_TIME_OUT = time;
        return this;
    }

    public HttpUtil setReadTimeOut(int time) {
        this.READ_TIME_OUT = time;
        return this;
    }

    public HttpUtil addParams(String key, Object value) {
        requestParamsMap.put(key, value);
        return this;
    }

    public void execute(final CallBack callBack) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(urlString);
                        URLConnection urlConnection = url.openConnection();
                        httpUrlConnection = (HttpURLConnection) urlConnection;
                        httpUrlConnection.setRequestMethod("POST");
                        httpUrlConnection.setConnectTimeout(CONNECT_TIME_OUT);
                        httpUrlConnection.setReadTimeout(READ_TIME_OUT);
                        //是否向httpUrlConnection输出
                        httpUrlConnection.setDoOutput(true);
                        //是否从httpUrlConnection读入
                        httpUrlConnection.setDoInput(true);
                        //是否使用缓存
                        httpUrlConnection.setUseCaches(false);
                        httpUrlConnection.setRequestProperty("accept", "*/*");
                        httpUrlConnection.setRequestProperty("connection", "Keep-Alive");
                        //设定传送的内容类型是可序列化的java对象
                        httpUrlConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
                        httpUrlConnection.setRequestProperty("Charset", "UTF-8");

                        httpExecute.getResponse(callBack,httpUrlConnection,requestParamsMap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }
}