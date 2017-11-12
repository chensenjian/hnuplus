package me.zhaoweihao.hnuplus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhaoweihao.hnuplus.CommentActivity;
import me.zhaoweihao.hnuplus.JavaBean.Post;
import me.zhaoweihao.hnuplus.MainActivity;
import me.zhaoweihao.hnuplus.R;

/**
 * Created by Administrator on 2017/11/9.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context mContext;
    private List<Post> mPostList;


    static class ViewHolder extends RecyclerView.ViewHolder{

        View postView;

        /**
         * Butter Knife
         */
        @BindView(R.id.tv_author) TextView postAuthor;
        @BindView(R.id.tv_content) TextView postContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            postView = itemView;

        }
    }

    public PostAdapter(List<Post> postList){
        mPostList = postList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){

            mContext=parent.getContext();

        }
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.posts_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.postView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Post post = mPostList.get(position);
                Intent intent=new Intent(mContext,CommentActivity.class);

                    intent.putExtra("author",post.getAuthor().getUsername());
                    intent.putExtra("content",post.getContent());
                    intent.putExtra("objectID",post.getObjectId());
                    intent.putExtra("authorObjectID",post.getAuthor().getObjectId());
                ((MainActivity) mContext).startActivityForResult(intent,2);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

            Post post = mPostList.get(position);
            holder.postAuthor.setText(post.getAuthor().getUsername());
            holder.postContent.setText(post.getContent());

    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }
}
