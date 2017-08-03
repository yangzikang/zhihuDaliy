package yqb.com.zhuhudaliy.activity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import yqb.com.zhuhudaliy.R;
import yqb.com.zhuhudaliy.base.BaseActivity;
import yqb.com.zhuhudaliy.engine.Api;

public class NewsConetentActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_news_conetent);
        webView = (WebView) findViewById(R.id.webview_news_content);
        progressBar = (ProgressBar)findViewById(R.id.progressBar_load_webview);
    }

    @Override
    protected void initBussiness() {
        Intent intent = getIntent();
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }

            }
        });
        webView.loadUrl(Api.getInstance().getContentUrl() + intent.getStringExtra("url"));
        webView.getSettings().setJavaScriptEnabled(false);
        //去除广告，在代码中加载JavaScript，用JavaScript移除广告
    }
}
