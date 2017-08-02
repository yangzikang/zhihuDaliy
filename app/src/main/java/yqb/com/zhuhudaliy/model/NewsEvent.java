package yqb.com.zhuhudaliy.model;

import java.util.List;

/**
 * Created by yangzikang on 2017-7-31.
 */

public class NewsEvent {
    private List<NewsModel> NewsModelList;
    private int eventType = 0;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public List<NewsModel> getNewsModelList() {
        return NewsModelList;
    }

    public void setNewsModelList(List<NewsModel> newsModelList) {
        NewsModelList = newsModelList;
    }
}
