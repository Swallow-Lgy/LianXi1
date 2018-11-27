package com.example.day01;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util {
    //定义接口
        public interface CallBack<T>{
            void onSuccess(T t);
    }
    //异步实现
     @SuppressLint("StaticFieldLeak")
     public void getData(String urlStr, final Class clazz, final CallBack callBack){
            new AsyncTask<String, Void, Object>() {
                @Override
                protected Object doInBackground(String... strings) {
                    return getData(strings[0],clazz);
                }

                @Override
                protected void onPostExecute(Object o) {
                    callBack.onSuccess(o);
                }
            }.execute(urlStr);
     }
    public <T> T  getData(String urlStr,Class clazz){
        return (T) new Gson().fromJson(getData(urlStr),clazz);
    }
    public String getData(String urlStr){
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode==200){
                String result = stream2Stirng(urlConnection.getInputStream());
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String stream2Stirng(InputStream is) throws IOException {
       StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        for (String tmp = br.readLine(); tmp!=null ; tmp = br.readLine()){
          stringBuilder.append(tmp);
        }
        return stringBuilder.toString();
    }
}
