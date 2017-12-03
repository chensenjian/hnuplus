package me.zhaoweihao.hnuplus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import kotlinx.android.synthetic.main.activity_comment.*
import me.zhaoweihao.hnuplus.Interface.CommentInterface

class CommentActivity : AppCompatActivity() {

    private var fragmentManager: FragmentManager? = null
    private var commentFragment: CommentFragment? = null
    private var listener: CommentInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        fragmentManager = supportFragmentManager
        // Open a Fragment transaction
        val transaction = (fragmentManager as FragmentManager?)!!.beginTransaction()
        if (commentFragment == null) {
            // If CommentFragment is empty, create one and add to the screen
            commentFragment = CommentFragment()
            setListener(commentFragment!!)
            transaction.add(R.id.fl_comment, commentFragment)
        } else {
            // If CommentFragment is not empty, it will be displayed directly
            transaction.show(commentFragment)
        }
        transaction.commit()

        iv_comment!!.setOnClickListener { listener!!.myAction(et_comment!!.text.toString()) }
    }

    fun setListener(listener: CommentInterface) {
        this.listener = listener
    }

}
