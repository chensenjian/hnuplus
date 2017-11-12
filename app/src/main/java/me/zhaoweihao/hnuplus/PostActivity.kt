package me.zhaoweihao.hnuplus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager

class PostActivity : AppCompatActivity() {

    private var fragmentManager: FragmentManager? = null
    private var postFragment: PostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        fragmentManager = supportFragmentManager

        // 开启一个Fragment事务
        val transaction = (fragmentManager as FragmentManager?)!!.beginTransaction()

        if (postFragment == null) {
            // 如果MessageFragment为空，则创建一个并添加到界面上
            postFragment = PostFragment()
            transaction.add(R.id.fl_post, postFragment)
        } else {
            // 如果MessageFragment不为空，则直接将它显示出来
            transaction.show(postFragment)
        }
        transaction.commit()

    }

}
