package yqb.com.zhuhudaliy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.LinearInterpolator;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import yqb.com.zhuhudaliy.R;
import yqb.com.zhuhudaliy.adapter.NewsAdapter;
import yqb.com.zhuhudaliy.adapter.SavedNewsAdapter;
import yqb.com.zhuhudaliy.base.BaseActivity;
import yqb.com.zhuhudaliy.engine.Api;
import yqb.com.zhuhudaliy.presenter.NewsPresenter;
import yqb.com.zhuhudaliy.view.INewsView;

public class ThemeActivity extends BaseActivity implements INewsView {

    RecyclerView recyclerView;
    NewsPresenter presenter = new NewsPresenter(this);

    @Override
    protected void initView() {
        setContentView(R.layout.activity_theme);
        recyclerView = (RecyclerView) findViewById(R.id.news_theme);
    }

    @Override
    protected void initBussiness() {
        EventBus.getDefault().register(presenter);
        Intent intent = getIntent();
        String themeId = intent.getStringExtra("theme");
        presenter.loadNewsFromTheme(Api.getInstance().getThemeUrl() + themeId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(presenter);
    }

    @Override
    public void setList(List news) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new NewsAdapter(news, this));
    }
}
