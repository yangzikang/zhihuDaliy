package yqb.com.zhuhudaliy.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;


import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import yqb.com.zhuhudaliy.R;
import yqb.com.zhuhudaliy.adapter.NewsAdapter;
import yqb.com.zhuhudaliy.base.BaseActivity;
import yqb.com.zhuhudaliy.engine.Api;
import yqb.com.zhuhudaliy.presenter.NewsPresenter;
import yqb.com.zhuhudaliy.util.BeforeOneDay;
import yqb.com.zhuhudaliy.view.INewsView;

/**
 * create by yangzikang 2017/7/31
 */
public class NewsActivity extends BaseActivity implements INewsView {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    NewsPresenter presenter = new NewsPresenter(this);

    @Override
    protected void initView() {
        setContentView(R.layout.activity_news);
        recyclerView = (RecyclerView) findViewById(R.id.news_list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
    }

    @Override
    protected void initBussiness() {
        EventBus.getDefault().register(presenter);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        presenter.loadNews();
                        swipeRefreshLayout.setRefreshing(false);
                        BeforeOneDay.nowDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
                    }
                }
        );
        presenter.loadNews();
    }

    @Override
    public void setList(List news) {
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        final NewsAdapter adapter = new NewsAdapter(news, this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    new Handler().postDelayed(new Runnable() {
                        boolean isRefreshing = false;

                        @Override
                        public void run() {
                            if (isRefreshing) {

                            } else {
                                isRefreshing = true;
                                BeforeOneDay.nowDate = BeforeOneDay.getSpecifiedDayBefore(BeforeOneDay.nowDate);
                                String date = BeforeOneDay.nowDate;
                                presenter.loadNews(Api.getInstance().getBeforeUrl() + date);
                                isRefreshing = false;
                            }

                        }
                    }, 1000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = manager.findLastVisibleItemPosition();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_daily_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //通过调用item.getItemId()来判断菜单项
        switch (item.getItemId()) {
            case R.id.to_saved_news:
                Intent intent = new Intent(NewsActivity.this, SavedNewsActivity.class);
                startActivity(intent);
                break;
            case R.id.choose_one_day:
                intent = new Intent(NewsActivity.this, ChooseDayActivity.class);
                startActivityForResult(intent, 0);
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                String date = data.getStringExtra("date");
                presenter.loadNews(Api.getInstance().getBeforeUrl() + date);
                break;
            default:
                break;
        }
    }
}
