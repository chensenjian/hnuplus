package me.zhaoweihao.hnuplus.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.zhaoweihao.hnuplus.JavaBean.Comment;
import me.zhaoweihao.hnuplus.R;

/**
 * Created by Administrator on 2017/11/10.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> mCommentList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView commentatorName,commentatorContent;

        public ViewHolder(View itemView) {

            super(itemView);
            commentatorName = (TextView) itemView.findViewById(R.id.tv_commentator_name);
            commentatorContent = (TextView) itemView.findViewById(R.id.tv_commentator_content);

        }
    }

    public CommentAdapter(List<Comment> commentList){
        mCommentList = commentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Comment comment = mCommentList.get(position);
        holder.commentatorName.setText(comment.getUser().getUsername());
        holder.commentatorContent.setText(comment.getContent());

    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }
}
