package yqb.com.zhuhudaliy.engine;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yangzikang on 2017/7/28.
 */

public class Network {

    private String url = null;

    public Network(String url){
        this.url = url;
    }

    public JSONObject doNetWork(){
        JSONObject jsonObject = null;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            byte[] originResponseData = response.body().bytes();
            String responseString = new String(originResponseData);
            jsonObject = new JSONObject(responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
