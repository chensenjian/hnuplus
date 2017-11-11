package me.zhaoweihao.hnuplus

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.github.florent37.viewtooltip.ViewTooltip
import com.rengwuxian.materialedittext.MaterialEditText

/**
 * Created by Administrator on 2017/11/9.
 */

class PostFragment :  Fragment() {

    private var postEditText:MaterialEditText? = null
    private var buttonSubmit:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val postLayout = inflater!!.inflate(R.layout.post_layout, container,
                false)
        initViews(postLayout)

        ViewTooltip
                .on(postEditText)
                .autoHide(true,3000)
                .corner(30)
                .position(ViewTooltip.Position.BOTTOM)
                .text("Share what you see today")
                .show()

        buttonSubmit!!.setOnClickListener {
                val intent = Intent()
                intent.putExtra("data_return", postEditText?.text.toString())
                activity.setResult(RESULT_OK,intent)
                activity.finish()

        }

        return postLayout
    }

    fun initViews(view:View){

        postEditText = view.findViewById(R.id.et_post) as MaterialEditText?
        buttonSubmit = view.findViewById(R.id.btn_submit) as Button?

    }


}