package me.zhaoweihao.hnuplus.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import butterknife.BindView
import butterknife.ButterKnife
import me.zhaoweihao.hnuplus.JavaBean.Comment
import me.zhaoweihao.hnuplus.R

/**
 * Created by Administrator on 2017/11/10.
 */

class CommentAdapter(private val mCommentList: List<Comment>) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var commentatorName: TextView = view.findViewById(R.id.tv_commentator_name) as TextView
        var commentatorContent: TextView = view.findViewById(R.id.tv_commentator_content) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.comments_item, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = mCommentList[position]
        holder.commentatorName.text = comment.user!!.username
        holder.commentatorContent.text = comment.content
    }

    override fun getItemCount(): Int {
        return mCommentList.size
    }
}
