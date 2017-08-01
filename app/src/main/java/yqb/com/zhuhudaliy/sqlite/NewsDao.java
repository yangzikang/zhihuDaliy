package yqb.com.zhuhudaliy.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import yqb.com.zhuhudaliy.model.NewsModel;

/**
 * Created by yangzikang on 2017/7/31.
 */
public class NewsDao {

    private SqliteHelper helper = null;

    public NewsDao(Context context) {
        helper = new SqliteHelper(context);
    }

    public boolean addNews(NewsModel news) {

        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            database.execSQL("insert into news(images,title,id) values('" + news.getImage() + "', '" + news.getTitle()
                    + "','" + news.getId() + "')");
            if (database != null) {
                database.close();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //查询
    public List<NewsModel> getSavedNewsList(String sql) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<NewsModel> mList = null;
        if (cursor != null) {
            mList = new ArrayList<>();
            while (cursor.moveToNext()) {
                NewsModel newsModel = new NewsModel();
                newsModel.setImage(cursor.getString(0));
                newsModel.setTitle(cursor.getString(1));
                newsModel.setId(cursor.getString(2));
                mList.add(newsModel);
            }
        }
        return mList;
    }

    public boolean deleteNews(String id){
        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            database.execSQL("delete from news where id = "+id+"");
            if (database != null) {
                database.close();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
