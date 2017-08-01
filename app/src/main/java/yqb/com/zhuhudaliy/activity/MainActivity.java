package yqb.com.zhuhudaliy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import yqb.com.zhuhudaliy.R;
import yqb.com.zhuhudaliy.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private final static int SLEEPTIME = 2000;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SLEEPTIME);
                    Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


}
