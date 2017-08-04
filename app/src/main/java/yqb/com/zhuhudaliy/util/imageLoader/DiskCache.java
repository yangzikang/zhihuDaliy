package yqb.com.zhuhudaliy.util.imageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by yangzikang on 2017/8/3.
 */

public class DiskCache {
    private static DiskCache diskCache = new DiskCache();
    private DiskCache(){}

    public static DiskCache getInstance(){
        return diskCache;
    }

    public void put(String key,Bitmap bitmap) {
        try {
            saveToSDCard(String.valueOf(key.hashCode()),convertIconToString(bitmap));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void saveToSDCard(String filename, String filecontent)throws Exception{
        File file = new File(Environment.getExternalStorageDirectory(),filename);
        FileOutputStream outStream = new FileOutputStream(file);
        outStream.write(filecontent.getBytes());
        outStream.close();
    }

    public Bitmap get(String key) throws Exception{
        String iconString = "";
        File file = new File(Environment.getExternalStorageDirectory(),String.valueOf(key.hashCode()));
        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                iconString+=tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        Log.d("cache","本地缓存取");
        return convertStringToIcon(iconString);
    }

    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    public String convertIconToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public Bitmap convertStringToIcon(String st)
    {
        // OutputStream out;
        Bitmap bitmap = null;
        try
        {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public boolean isExsit(String key){
        try {
            get(key);
            return  true;
        } catch (FileNotFoundException e) {
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
