package yqb.com.zhuhudaliy.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import yqb.com.zhuhudaliy.R;
import yqb.com.zhuhudaliy.activity.NewsConetentActivity;
import yqb.com.zhuhudaliy.model.NewsModel;
import yqb.com.zhuhudaliy.sqlite.NewsDao;
import yqb.com.zhuhudaliy.util.ImageUrlRepair;

/**
 * create by yangzikang 2017/7/31
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsModel> newses;
    private Context mContext;
    private final static int BUTTOMVIEW = 0;
    private final static int CONTENTVIEW = 1;

    public NewsAdapter(List newses, Context context) {
        this.newses = newses;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getItemCount() - 1;
        if (position >= (dataItemCount)) {
            return BUTTOMVIEW;
        } else {
            return CONTENTVIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        if (viewType == BUTTOMVIEW) {
            return new ButtomViewHolder(mLayoutInflater.inflate(R.layout.footer_load, parent, false));
        } else if (viewType == CONTENTVIEW) {
            return new NewsViewHolder(mLayoutInflater.inflate(R.layout.recycler_style2, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsViewHolder) {
            NewsViewHolder viewHolder = (NewsViewHolder) holder;
            NewsModel news = newses.get(position);
            viewHolder.title.setText(news.getTitle());
            Glide.with(mContext).load(ImageUrlRepair.handleImageUrl(news.getImage())).placeholder(R.drawable.process).error(R.drawable.icon).into(viewHolder.image);
        } else if (holder instanceof ButtomViewHolder) {
            ButtomViewHolder footerHolder = (ButtomViewHolder) holder;
            footerHolder.footer.setText("加载中...");
        }
    }

    @Override
    public int getItemCount() {
        return newses.size() + 1;//一个ButtomView
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public NewsViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getPosition();
                    String id = newses.get(position).getId();//获取正文
                    //页面跳转
                    Intent intent = new Intent(mContext, NewsConetentActivity.class);
                    intent.putExtra("url", id);
                    mContext.startActivity(intent);

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog.Builder normalDialog =
                            new AlertDialog.Builder(mContext);
                    normalDialog.setIcon(R.drawable.icon);
                    normalDialog.setTitle("添加喜欢");
                    normalDialog.setMessage("确定添加喜欢吗?");
                    normalDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int position = getPosition();
                                    NewsDao newsDao = new NewsDao(mContext);
                                    newsDao.addNews(newses.get(position));
                                    Toast.makeText(mContext, "添加喜欢", Toast.LENGTH_LONG).show();
                                }
                            });
                    normalDialog.setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //...To-do
                                }
                            });
                    normalDialog.show();
                    return true;
                }
            });
        }
    }

    class ButtomViewHolder extends RecyclerView.ViewHolder {
        TextView footer;

        public ButtomViewHolder(View itemView) {
            super(itemView);
            footer = (TextView) itemView.findViewById(R.id.load_news);
        }
    }

}
