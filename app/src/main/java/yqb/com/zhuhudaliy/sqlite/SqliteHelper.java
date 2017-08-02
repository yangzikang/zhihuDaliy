package yqb.com.zhuhudaliy.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yangzikang on 2017/7/31.
 */

public class SqliteHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "gac.db";
    private static int VERSION = 1;
    private static final String USER_TABLE_CREATE = "create table if not exists "
            + "news" + " ("
            + "images" + " TEXT,"
            + "title" + " TEXT,"
            + "id" + " TEXT);";
    private Context context;
    private static SqliteHelper instance;

    public static SqliteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SqliteHelper(context.getApplicationContext());
        }
        return instance;
    }

    public SqliteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void closeDB() {
        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = null;

        }
    }
}
