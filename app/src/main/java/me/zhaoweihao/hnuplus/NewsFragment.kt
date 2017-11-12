package me.zhaoweihao.hnuplus

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Administrator on 2017/11/9.
 */
/**
 * There will be something here
 * so I call it news first,not real news function in the future
 */


class NewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val newsLayout = inflater!!.inflate(R.layout.news_layout, container,
                false)
        return newsLayout

    }

}