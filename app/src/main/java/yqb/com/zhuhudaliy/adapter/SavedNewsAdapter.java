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

import java.util.List;

import yqb.com.zhuhudaliy.R;
import yqb.com.zhuhudaliy.activity.NewsConetentActivity;
import yqb.com.zhuhudaliy.util.imageLoader.ImageManager;
import yqb.com.zhuhudaliy.model.NewsModel;
import yqb.com.zhuhudaliy.sqlite.NewsDao;
import yqb.com.zhuhudaliy.util.ImageUrlRepair;


/**
 * Created by yangzikang on 2017/7/31.
 */

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.ViewHolder> {

    private List<NewsModel> newses;
    private Context mContext;

    public SavedNewsAdapter(List newses, Context context) {
        this.newses = newses;
        this.mContext = context;
    }

    @Override
    public SavedNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_style1, parent, false);
        SavedNewsAdapter.ViewHolder viewHolder = new SavedNewsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SavedNewsAdapter.ViewHolder holder, int position) {
        NewsModel news = newses.get(position);
        holder.title.setText(news.getTitle());
        //Glide.with(mContext).load(ImageUrlRepair.handleImageUrl(news.getImage())).placeholder(R.drawable.process).error(R.drawable.icon).into(holder.image);
        new ImageManager().setImage(holder.image,ImageUrlRepair.handleImageUrl(news.getImage()));
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
            title = (TextView) itemView.findViewById(R.id.textview_saved_title);
            image = (ImageView) itemView.findViewById(R.id.imageview_saved_image);

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
                    normalDialog.setTitle("删除");
                    normalDialog.setMessage("确定要删除吗?");
                    normalDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int position = getPosition();
                                    NewsDao newsDao = new NewsDao(mContext);
                                    newsDao.deleteNews(newses.get(position).getId());
                                    newses.remove(position);
                                    SavedNewsAdapter.this.notifyItemRemoved(position);
                                    SavedNewsAdapter.this.notifyItemRangeChanged(0, newses.size() - position);
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
