package yqb.com.zhuhudaliy.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by yangzikang on 2017/7/28.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initBussiness();

    }

    protected void initView() {

    }

    protected void initBussiness() {

    }

}
