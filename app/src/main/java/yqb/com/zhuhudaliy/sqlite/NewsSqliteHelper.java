package yqb.com.zhuhudaliy.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yangzikang on 2017/7/31.
 */

public class NewsSqliteHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "news.db";
    private static int VERSION = 1;
    private static final String NEWS_TABLE_CREATE = "create table if not exists "
            + "news" + " ("
            + "images" + " TEXT,"
            + "title" + " TEXT,"
            + "id" + " TEXT);";
    private Context context;

    public NewsSqliteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NEWS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
