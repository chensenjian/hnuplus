package me.zhaoweihao.hnuplus

import android.os.Bundle
import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.photo_view_layout.*

/**
 * Created by Administrator on 2017/12/3.
 */
class PhotoViewFragment :  Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val photoViewLayout = inflater!!.inflate(R.layout.photo_view_layout, container,
                false)
        return photoViewLayout
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = activity.intent
        val imageUrl = intent.getStringExtra("imageUrl")
        Glide.with(activity).load(imageUrl).into(photo_view)
    }

}