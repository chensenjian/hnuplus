package me.zhaoweihao.hnuplus

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.florent37.viewtooltip.ViewTooltip
import kotlinx.android.synthetic.main.post_layout.*

/**
 * Created by Administrator on 2017/11/9.
 */

class PostFragment :  Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val postLayout = inflater!!.inflate(R.layout.post_layout, container,
                false)
        return postLayout
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        using kotlin-android-extensions in fragment
        thanks to this post:https://antonioleiva.com/kotlin-android-extensions/
         */
        ViewTooltip
                .on(et_post)
                .autoHide(true,3000)
                .corner(30)
                .position(ViewTooltip.Position.BOTTOM)
                .text("Share what you see today")
                .show()

        btn_submit!!.setOnClickListener {
            val intent = Intent()
            intent.putExtra("data_return", et_post?.text.toString())
            activity.setResult(RESULT_OK,intent)
            activity.finish()
        }
    }

}