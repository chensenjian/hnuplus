package me.zhaoweihao.hnuplus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import kotlinx.android.synthetic.main.activity_comment.*
import me.zhaoweihao.hnuplus.Interface.MyInterface

class CommentActivity : AppCompatActivity() {

    private var fragmentManager: FragmentManager? = null
    private var commentFragment: CommentFragment? = null
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

        /*
        using kotlin-android-extensions in activity
        thanks to this post:https://stackoverflow.com/questions/42453010/android-kotlin-with-butterknife
         */
        iv_comment!!.setOnClickListener { listener!!.myAction(et_comment!!.text.toString()) }

    }

    fun setListener(listener: MyInterface) {
        this.listener = listener
    }

}
