package yqb.com.zhuhudaliy.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yqb.com.zhuhudaliy.engine.Network;
import yqb.com.zhuhudaliy.sqlite.NewsDao;

/**
 * Created by yangzikang on 2017/7/31.
 */

public class NewsList {

    public static List<NewsModel> getListFromJson(String url) {
        List<NewsModel> news = new ArrayList<>();
        Network network = new Network(url);
        JSONObject jsonObject = network.doNetWork();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("stories");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject newsJson = jsonArray.getJSONObject(i);
                String image = newsJson.optString("images");
                String id = newsJson.getString("id");
                String title = newsJson.getString("title");
                NewsModel model = new NewsModel();
                model.setImage(image);
                model.setTitle(title);
                model.setId(id);
                news.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }

    public static List<NewsModel> getListFromSqlite(String sql, Context mContext) {
        List<NewsModel> news = new ArrayList<>();
        NewsDao newsDao = new NewsDao(mContext);
        news = newsDao.getSavedNewsList(sql);
        return news;
    }

    public static List<NewsModel> getListFromThemeUrl(String url) {
        List<NewsModel> news = new ArrayList<>();
        Network network = new Network(url);
        JSONObject jsonObject = network.doNetWork();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("stories");
            for (int i = 1; i < jsonArray.length(); i++) {
                JSONObject newsJson = jsonArray.getJSONObject(i);
                String image = newsJson.optString("images");
                String id = newsJson.getString("id");
                String title = newsJson.getString("title");
                NewsModel model = new NewsModel();
                model.setImage(image);
                model.setTitle(title);
                model.setId(id);
                news.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }

}
