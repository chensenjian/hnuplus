package me.zhaoweihao.hnuplus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.widget.EditText
import android.widget.ImageView
import me.zhaoweihao.hnuplus.Interface.MyInterface

class CommentActivity : AppCompatActivity() {

    private var fragmentManager: FragmentManager? = null
    private var commentFragment: CommentFragment? = null
    private var commentEditText: EditText? = null
    private var commentImageView: ImageView? = null
    private var listener: MyInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        fragmentManager = supportFragmentManager

        // 开启一个Fragment事务
        val transaction = (fragmentManager as FragmentManager?)!!.beginTransaction()

        if (commentFragment == null) {
            // 如果MessageFragment为空，则创建一个并添加到界面上
            commentFragment = CommentFragment()
            setListener(commentFragment!!)
            transaction.add(R.id.fl_comment, commentFragment)
        } else {
            // 如果MessageFragment不为空，则直接将它显示出来
            transaction.show(commentFragment)
        }
        transaction.commit()

        initViews()

        commentImageView!!.setOnClickListener { listener!!.myAction(commentEditText!!.text.toString()) }

    }

    fun initViews(){
        commentEditText = findViewById(R.id.et_comment) as EditText?
        commentImageView = findViewById(R.id.iv_comment) as ImageView?
    }

    fun setListener(listener: MyInterface) {
        this.listener = listener
    }

}
