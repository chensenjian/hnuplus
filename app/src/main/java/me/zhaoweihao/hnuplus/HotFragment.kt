package me.zhaoweihao.hnuplus

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast

import java.util.Collections

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import me.zhaoweihao.hnuplus.Adapter.PostAdapter
import me.zhaoweihao.hnuplus.JavaBean.MyUser
import me.zhaoweihao.hnuplus.JavaBean.Post

import android.app.Activity.RESULT_OK
import android.preference.PreferenceManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.hot_layout.*

/**
 * Created by Administrator on 2017/11/9.
 */

class HotFragment : Fragment() {

    private var layoutManager: LinearLayoutManager? = null
    private var adapter: PostAdapter? = null
    private var userInfo: MyUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val hotLayout = inflater!!.inflate(R.layout.hot_layout,
                container, false)

        return hotLayout
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshRecyclerView()

        pull_to_refresh!!.setOnRefreshListener { refreshRecyclerView() }

        fb!!.setOnClickListener {
            userInfo = BmobUser.getCurrentUser(MyUser::class.java)

            if (userInfo != null) {
                val intent = Intent(activity, PostActivity::class.java)
                startActivityForResult(intent, 1)
            } else {
                Snackbar.make(fb!!, "You are not signin", Snackbar.LENGTH_SHORT)
                        .setAction("Sign in") {
                            val intent = Intent(activity, SigninActivity::class.java)
                            startActivity(intent)
                        }.show()

            }
        }

        rv_posts!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0)
                    fb!!.hide()
                else if (dy < 0)
                    fb!!.show()
            }
        })

    }

    private fun refreshRecyclerView() {

        pull_to_refresh!!.setRefreshing(true)
        val query = BmobQuery<Post>()
//        query.setLimit(15)
        query.include("author")
        query.findObjects(object : FindListener<Post>() {

            override fun done(`object`: List<Post>, e: BmobException?) {
                if (e == null) {
                    Collections.reverse(`object`)

                    Log.d("HF",`object`.size.toString())

                    val appSharedPrefs = PreferenceManager
                            .getDefaultSharedPreferences(activity.applicationContext)
                    val prefsEditor = appSharedPrefs.edit()
                    val gson = Gson()
                    val json = gson.toJson(`object`)

                    prefsEditor.putString("MyObject", json)
                    prefsEditor.commit()

                    //                    Toast.makeText(getActivity(),Integer.toString(object.size()), Toast.LENGTH_SHORT).show();
                    layoutManager = LinearLayoutManager(activity)
                    rv_posts!!.layoutManager = layoutManager
                    adapter = PostAdapter(`object`)
                    rv_posts!!.adapter = adapter
                    Snackbar.make(rv_posts!!, "refresh successfully", Snackbar.LENGTH_SHORT).show()
                    pull_to_refresh!!.setRefreshing(false)

                } else {
                    Log.i("bmob", "失败：" + e.message)
                }
            }

        })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {

            1 -> if (resultCode == RESULT_OK) {
                val returnedData = data!!.getStringExtra("data_return")

                if (returnedData == "") {
                    Toast.makeText(activity, "empty text", Toast.LENGTH_SHORT).show()
                } else {
                    val user = BmobUser.getCurrentUser(MyUser::class.java)
                    val post = Post()
                    post.content = returnedData
                    post.author = user
                    post.save(object : SaveListener<String>() {

                        override fun done(objectId: String, e: BmobException?) {
                            if (e == null) {
                                Toast.makeText(activity, "post successfully", Toast.LENGTH_SHORT).show()
                                refreshRecyclerView()
                            } else {
                                Toast.makeText(activity, "post failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }
            }
        }
    }

}