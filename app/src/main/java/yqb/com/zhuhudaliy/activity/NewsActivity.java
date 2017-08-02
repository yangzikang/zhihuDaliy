package yqb.com.zhuhudaliy.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    Toolbar toolbar;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_news);
        recyclerView = (RecyclerView) findViewById(R.id.news_list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_toToday) {
                    presenter.loadNews();
                    BeforeOneDay.nowDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
                    toolbar.setTitle("知道日报");
                } else if (id == R.id.nav_toToday) {

                } else if (id == R.id.nav_toToday) {

                } else if (id == R.id.nav_toToday) {

                } else if (id == R.id.nav_toToday) {

                } else if (id == R.id.nav_toToday) {

                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
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
                        toolbar.setTitle("知道日报");
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
                                toolbar.setTitle(date);
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
