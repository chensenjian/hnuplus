package me.zhaoweihao.hnuplus

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.florent37.viewtooltip.ViewTooltip
import com.yoavst.kotlin.`KotlinPackage$Toasts$53212cf1`.toast
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
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
        ViewTooltip
                .on(et_post)
                .autoHide(true,3000)
                .corner(30)
                .position(ViewTooltip.Position.BOTTOM)
                .text("Share what you see today")
                .show()
        btn_submit!!.setOnClickListener {
            val intent = Intent()
            if((activity as PostActivity).path == null){
                intent.putExtra("data_return", et_post?.text.toString())
                activity.setResult(RESULT_OK,intent)
                activity.finish()
            }else{
                Log.d("PF","have photo")
                intent.putExtra("data_return", et_post?.text.toString())
                intent.putExtra("data_return_2",(activity as PostActivity).path)
                activity.setResult(RESULT_OK,intent)
                activity.finish()
            }

        }
        btn_pic!!.setOnClickListener {
            // request runtime permission
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                showImageSelector()
            }

        }
    }

    private fun showImageSelector(){
        Matisse.from(activity)
                .choose(setOf(MimeType.JPEG,MimeType.PNG))
                .countable(true)
                .maxSelectable(1)
                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(GlideEngine())
                .forResult(2)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        //request runtime permission successfully
        when (requestCode) {
            1 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageSelector()
            } else {
                toast(activity, "denied")
            }
        }
    }

}