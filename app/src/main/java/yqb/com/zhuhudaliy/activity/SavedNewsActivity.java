package yqb.com.zhuhudaliy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import yqb.com.zhuhudaliy.R;
import yqb.com.zhuhudaliy.adapter.NewsAdapter;
import yqb.com.zhuhudaliy.adapter.SavedNewsAdapter;
import yqb.com.zhuhudaliy.base.BaseActivity;
import yqb.com.zhuhudaliy.presenter.NewsPresenter;
import yqb.com.zhuhudaliy.view.INewsView;

public class SavedNewsActivity extends BaseActivity implements INewsView {

    RecyclerView recyclerView;
    NewsPresenter presenter = new NewsPresenter(this);

    @Override
    protected void initView() {
        setContentView(R.layout.activity_saved_news);
        recyclerView = (RecyclerView) findViewById(R.id.saved_news_list);
    }

    @Override
    protected void initBussiness() {
        EventBus.getDefault().register(presenter);
        presenter.loadNewsFromSqlite(this);
    }

    @Override
    public void setList(List news) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new SavedNewsAdapter(news, this));
    }
}
