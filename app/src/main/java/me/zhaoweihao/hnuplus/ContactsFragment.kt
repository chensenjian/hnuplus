package me.zhaoweihao.hnuplus

import android.os.Bundle


import android.support.v4.app.Fragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.contacts_layout.*



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

    }

}


