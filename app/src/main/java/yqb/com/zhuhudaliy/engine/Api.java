package yqb.com.zhuhudaliy.engine;

/**
 * Created by yangzikang on 2017/7/28.
 */

public class Api {
    private static Api api = new Api();
    private String daliyUrl = "http://news-at.zhihu.com/api/4/news/latest";
    private String beforeUrl = "http://news-at.zhihu.com/api/4/news/before/";//+date format(20130520-今)
    private String contentUrl = "http://daily.zhihu.com/story/";//+id format(9543161)
    //2 游戏 7 音乐 8 体育 3 电影 9 动漫 4 设计 6 财经 ,3784962
    private String themeUrl = "http://news-at.zhihu.com/api/4/theme/";//+themeid format(11)

    private Api() {
        //维持单例模式，封闭构造
    }

    public static Api getInstance() {
        return api;
    }
    public String getThemeUrl() {
        return themeUrl;
    }

    public String getDaliyUrl() {
        return daliyUrl;
    }

    public String getBeforeUrl() {
        return beforeUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }
}
