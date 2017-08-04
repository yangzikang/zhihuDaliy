package yqb.com.zhuhudaliy.util.imageLoader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by yangzikang on 2017/6/27.
 */

public class MemoryCache {
    private static MemoryCache instance = new MemoryCache();
    private MemoryCache(){}
    public static  MemoryCache getInstance(){
        return instance;
    }

    private LruCache<String,Bitmap> cache = new LruCache(getMaxCacheNumber());

    private int getMaxCacheNumber(){
        return (int)Runtime.getRuntime().maxMemory()/8;
    }

    public void put(String key, Bitmap bitmap){
        cache.put(key,bitmap);
    }

    public Bitmap get(String key) {
        return cache.get(key);
    }

    public boolean isExsit(String key){
        if(cache.get(key)==null){
            return false;
        }
        else{
            return true;
        }
    }


}
