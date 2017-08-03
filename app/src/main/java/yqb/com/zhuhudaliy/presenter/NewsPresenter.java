package yqb.com.zhuhudaliy.presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import yqb.com.zhuhudaliy.engine.Api;
import yqb.com.zhuhudaliy.model.NewsEvent;
import yqb.com.zhuhudaliy.model.NewsList;
import yqb.com.zhuhudaliy.model.NewsModel;
import yqb.com.zhuhudaliy.view.INewsView;


/**
 * Created by yangzikang on 2017/7/31.
 */

public class NewsPresenter {
    private INewsView mNewsView;

    public NewsPresenter(INewsView view) {
        mNewsView = view;
    }

    public void loadNews(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<NewsModel> news = NewsList.getListFromJson(url);
                NewsEvent event = new NewsEvent();
                event.setNewsModelList(news);
                EventBus.getDefault().post(event);
            }
        }).start();
    }

    public void loadNews() {
        loadNews(Api.getInstance().getDaliyUrl());
    }

    public void loadNewsFromTheme(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<NewsModel> news = NewsList.getListFromThemeUrl(url);
                NewsEvent event = new NewsEvent();
                event.setNewsModelList(news);
                EventBus.getDefault().post(event);
            }
        }).start();
    }

    public void loadNewsFromSqlite(Context mContext) {
        List<NewsModel> news = NewsList.getListFromSqlite("select * from news", mContext);
        mNewsView.setList(news);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NewsEvent event) {

        mNewsView.setList(event.getNewsModelList());
    }

}
