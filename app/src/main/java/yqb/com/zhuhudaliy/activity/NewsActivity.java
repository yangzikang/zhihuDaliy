package yqb.com.zhuhudaliy.activity;

import android.app.ProgressDialog;
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
import android.util.Log;
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
    private NewsPresenter presenter = new NewsPresenter(this);
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private final int BUTTOMCOUNT = 1;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_news);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_news_list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initBussiness() {
        EventBus.getDefault().register(presenter);
        presenter.loadNews();
    }

    @Override
    protected void initListener() {
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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent = new Intent(NewsActivity.this, ThemeActivity.class);
                if (id == R.id.nav_toToday) {
                    presenter.loadNews();
                    BeforeOneDay.nowDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
                    toolbar.setTitle("知道日报");
                } else if (id == R.id.nav_movie) {
                    intent.putExtra("theme", getString(R.string.movie));
                    startActivity(intent);
                } else if (id == R.id.nav_music) {
                    intent.putExtra("theme", getString(R.string.music));
                    startActivity(intent);
                } else if (id == R.id.nav_sport) {
                    intent.putExtra("theme", getString(R.string.sport));
                    startActivity(intent);
                } else if (id == R.id.nav_design) {
                    intent.putExtra("theme", getString(R.string.design));
                    startActivity(intent);
                } else if (id == R.id.nav_animation) {
                    intent.putExtra("theme", getString(R.string.animation));
                    startActivity(intent);
                } else if (id == R.id.nav_finance) {
                    intent.putExtra("theme", getString(R.string.finance));
                    startActivity(intent);
                } else if (id == R.id.nav_game) {
                    intent.putExtra("theme", getString(R.string.game));
                    startActivity(intent);
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            EventBus.getDefault().register(presenter);
        } catch (Exception e) {
            Log.d("news", "首次进入");
            //首次进入进入异常
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(presenter);
    }

    @Override
    public void setList(List news) {
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        final NewsAdapter adapter = new NewsAdapter(news, this,BUTTOMCOUNT);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    final ProgressDialog dialog = new ProgressDialog(NewsActivity.this);
                    dialog.setMessage("加载中...");
                    dialog.show();
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
                                toolbar.setTitle(date);
                                dialog.cancel();
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
