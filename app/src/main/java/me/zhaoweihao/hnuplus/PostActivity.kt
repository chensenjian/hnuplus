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
        // Open a Fragment transaction
        val transaction = (fragmentManager as FragmentManager?)!!.beginTransaction()
        if (postFragment == null) {
            // If PostFragment is empty, create one and add to the screen
            postFragment = PostFragment()
            transaction.add(R.id.fl_post, postFragment)
        } else {
            // If PostFragment is not empty, it will be displayed directly
            transaction.show(postFragment)
        }
        transaction.commit()

    }

}
