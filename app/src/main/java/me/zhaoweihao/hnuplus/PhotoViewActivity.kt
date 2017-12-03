package me.zhaoweihao.hnuplus

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View

class PhotoViewActivity : AppCompatActivity() {

    private var fragmentManager: FragmentManager? = null
    private var photoViewFragment: PhotoViewFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(android.R.id.content).systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        setContentView(R.layout.activity_photo_view)

        fragmentManager = supportFragmentManager

        // 开启一个Fragment事务
        val transaction = (fragmentManager as FragmentManager?)!!.beginTransaction()

        if (photoViewFragment == null) {
            // 如果MessageFragment为空，则创建一个并添加到界面上
            photoViewFragment = PhotoViewFragment()
            transaction.add(R.id.fl_photoview, photoViewFragment)
        } else {
            // 如果MessageFragment不为空，则直接将它显示出来
            transaction.show(photoViewFragment)
        }
        transaction.commit()

    }
}
