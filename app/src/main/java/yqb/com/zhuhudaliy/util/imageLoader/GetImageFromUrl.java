package yqb.com.zhuhudaliy.util.imageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yangzikang on 2017/8/3.
 */

public class GetImageFromUrl {
    public static Bitmap getResponsebitmap(String urlPath) {
        URL url;
        Bitmap map = null;
        try {
            url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10 * 1000);
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                map = BitmapFactory.decodeStream(inputStream);

                CompressImage.getInstance().getPicTypeByUrl(urlPath);
                map = CompressImage.getInstance().comp(map);

                MemoryCache.getInstance().put(urlPath,map);
                DiskCache.getInstance().put(urlPath,map);
                inputStream.close();
            }
        } catch (MalformedURLException e) {
            Log.e("cache", "URL不正确");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
