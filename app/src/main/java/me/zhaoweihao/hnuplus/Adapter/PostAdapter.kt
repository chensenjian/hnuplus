package me.zhaoweihao.hnuplus.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import butterknife.BindView
import butterknife.ButterKnife
import me.zhaoweihao.hnuplus.CommentActivity
import me.zhaoweihao.hnuplus.JavaBean.Post
import me.zhaoweihao.hnuplus.MainActivity
import me.zhaoweihao.hnuplus.R

/**
 * Created by Administrator on 2017/11/9.
 */

class PostAdapter(private val mPostList: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var mContext: Context? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var postAuthor: TextView = view.findViewById(R.id.tv_author) as TextView
        var postContent: TextView = view.findViewById(R.id.tv_content) as TextView
        var postView: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.posts_item, parent, false)
        val holder = ViewHolder(view)
        holder.postView.setOnClickListener {
            val position = holder.adapterPosition
            val post = mPostList[position]
            val intent = Intent(mContext, CommentActivity::class.java)
            intent.putExtra("author", post.author!!.username)
            intent.putExtra("content", post.content)
            intent.putExtra("objectID", post.objectId)
            intent.putExtra("authorObjectID", post.author!!.objectId)
            (mContext as MainActivity).startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = mPostList[position]
        holder.postAuthor.text = post.author!!.username
        holder.postContent.text = post.content
    }

    override fun getItemCount(): Int {
        return mPostList.size
    }
}
