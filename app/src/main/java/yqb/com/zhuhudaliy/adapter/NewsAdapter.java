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

/**
 * create by yangzikang 2017/7/31
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsModel> newses;
    Context mContext;

    public NewsAdapter(List newses, Context context) {
        this.newses = newses;
        this.mContext = context;
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getItemCount()-1;
        if (position >= (dataItemCount)) {
            //底部View
            return 0;
        } else {
            //内容View
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_style2, parent, false);
//        NewsAdapter.ViewHolder viewHolder = new NewsAdapter.ViewHolder(view);
//        return viewHolder;
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        if (viewType == 0) {
            return new footerHolder(mLayoutInflater.inflate(R.layout.footer_load, parent, false));
        } else if (viewType == 1) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.recycler_style2, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder)holder;
            NewsModel news = newses.get(position);
            viewHolder.title.setText(news.getTitle());
            Glide.with(mContext).load(handleImageUrl(news.getImage())).placeholder(R.drawable.process).error(R.drawable.icon).into(viewHolder.image);
        } else if (holder instanceof footerHolder) {
            footerHolder footerHolder = (footerHolder) holder;
            footerHolder.footer.setText("加载中...");
        }
//        NewsModel news = newses.get(position);
//        holder.title.setText(news.getTitle());
//        Glide.with(mContext).load(handleImageUrl(news.getImage())).placeholder(R.drawable.process).error(R.drawable.icon).into(holder.image);
    }

    private String handleImageUrl(String imageUrl) {
        if (imageUrl.length() > 2) {
            imageUrl = imageUrl.substring(2);
            String[] realImageUrl = imageUrl.split("\"");
            imageUrl = realImageUrl[0];
            imageUrl = imageUrl.replace("\\", "");
            return imageUrl;
        } else {
            return imageUrl;
        }
    }

    @Override
    public int getItemCount() {
        return newses.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public ViewHolder(final View itemView) {
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
                                    Toast.makeText(mContext,"添加喜欢",Toast.LENGTH_LONG).show();
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

    class footerHolder extends RecyclerView.ViewHolder {
        TextView footer;

        public footerHolder(View itemView) {
            super(itemView);
            footer = (TextView) itemView.findViewById(R.id.load_news);

        }
    }


}
