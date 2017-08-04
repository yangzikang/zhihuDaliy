package yqb.com.zhuhudaliy.util.imageLoader;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by yangzikang on 2017/8/4.
 */

public class ImageManager {

    private ImageView imageView;
    private Bitmap bitmap;
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==0){
                imageView.setImageBitmap(bitmap);
            }
        }
    };

    public void setImage(ImageView imageView,final String url){
        this.imageView = imageView;
        try{
            if(MemoryCache.getInstance().isExsit(url)){
                bitmap = MemoryCache.getInstance().get(url);
                handler.sendEmptyMessage(0);
                Log.d("Mycache","缓存执行");
            } else if(DiskCache.getInstance().isExsit(url)){
                bitmap = DiskCache.getInstance().get(url);
                handler.sendEmptyMessage(0);
                Log.d("Mycache","本地缓存执行");
                MemoryCache.getInstance().put(url,bitmap);
            }else{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        bitmap = GetImageFromUrl.getResponsebitmap(url);
                        handler.sendEmptyMessage(0);

                    }
                }).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
