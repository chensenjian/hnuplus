package me.zhaoweihao.hnuplus

import android.os.Bundle
import android.preference.PreferenceManager

import android.support.v4.app.Fragment
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.contacts_layout.*
import me.zhaoweihao.hnuplus.JavaBean.Post


/**
 * Created by Administrator on 2017/11/9.
 */

/**
 * There will be something here
 * so I call it contacts first,not real contacts function in the future
 */

class ContactsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val contactsLayout = inflater!!.inflate(R.layout.contacts_layout, container, false)

        return contactsLayout
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn1.setOnClickListener {

        }

    }

}


