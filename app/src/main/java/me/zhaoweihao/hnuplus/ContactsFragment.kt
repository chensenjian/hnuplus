package me.zhaoweihao.hnuplus

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle


import android.support.v4.app.Fragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.contacts_layout.*
import com.zhihu.matisse.engine.impl.GlideEngine
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log

import com.yoavst.kotlin.`KotlinPackage$Toasts$53212cf1`.toast
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType


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

        btn_upload.setOnClickListener {
            // request permission
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                showImageSelector()
                }
            }

        }

    private fun showImageSelector(){
        Matisse.from(activity)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(GlideEngine())
                .forResult(0)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageSelector()
            } else {
                toast(activity,"denied")
            }
        }
    }

    }






