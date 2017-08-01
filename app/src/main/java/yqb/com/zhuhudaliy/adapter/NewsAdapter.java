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

import com.bumptech.glide.Glide;

import java.util.List;

import yqb.com.zhuhudaliy.R;
import yqb.com.zhuhudaliy.activity.NewsConetentActivity;
import yqb.com.zhuhudaliy.model.NewsModel;
import yqb.com.zhuhudaliy.sqlite.NewsDao;

/**
 * create by yangzikang 2017/7/31
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsModel> newses;
    Context mContext;

    public NewsAdapter(List newses, Context context) {
        this.newses = newses;
        this.mContext = context;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        NewsAdapter.ViewHolder viewHolder = new NewsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsModel news = newses.get(position);
        holder.title.setText(news.getTitle());
        Glide.with(mContext).load(handleImageUrl(news.getImage())).placeholder(R.drawable.process).error(R.drawable.icon).into(holder.image);
    }

    private String handleImageUrl(String imageUrl) {
        imageUrl = imageUrl.substring(2);
        String[] realImageUrl = imageUrl.split("\"");
        imageUrl = realImageUrl[0];
        imageUrl = imageUrl.replace("\\", "");
        return imageUrl;
    }

    @Override
    public int getItemCount() {
        return newses.size();
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


}
