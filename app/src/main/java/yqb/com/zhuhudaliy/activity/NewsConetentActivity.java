package yqb.com.zhuhudaliy.activity;

import android.content.Intent;
import android.webkit.WebView;

import yqb.com.zhuhudaliy.R;
import yqb.com.zhuhudaliy.base.BaseActivity;
import yqb.com.zhuhudaliy.engine.Api;

public class NewsConetentActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_news_conetent);
        webView = (WebView) findViewById(R.id.news_content_webview);
    }

    @Override
    protected void initBussiness() {
        Intent intent = getIntent();
        webView.loadUrl(Api.getInstance().getContentUrl() + intent.getStringExtra("url"));
        webView.getSettings().setJavaScriptEnabled(false);
        //去除广告，在代码中加载JavaScript，用JavaScript移除广告
    }
}
