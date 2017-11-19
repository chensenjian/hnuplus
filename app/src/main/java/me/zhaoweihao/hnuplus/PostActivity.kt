package me.zhaoweihao.hnuplus

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.FragmentManager
import android.util.Log

import com.zhihu.matisse.Matisse


class PostActivity : AppCompatActivity() {

    private var fragmentManager: FragmentManager? = null
    private var postFragment: PostFragment? = null
    public var path: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        fragmentManager = supportFragmentManager
        // Open a Fragment transaction
        val transaction = (fragmentManager as FragmentManager?)!!.beginTransaction()
        if (postFragment == null) {
            // If PostFragment is empty, create one and add to the screen
            postFragment = PostFragment()
            transaction.add(R.id.fl_post, postFragment)
        } else {
            // If PostFragment is not empty, it will be displayed directly
            transaction.show(postFragment)
        }
        transaction.commit()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
        //receive data from PostActivity
            2 -> if (resultCode == RESULT_OK) {
                val uri = Matisse.obtainResult(data)[0]
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
                if (cursor!!.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    path = cursor.getString(columnIndex)
                    Log.d("PA",path)
                } else {
                    //boooo, cursor doesn't have rows ...
                }
                cursor.close()

            }


        }
    }

}
