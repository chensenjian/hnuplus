package me.zhaoweihao.hnuplus

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
import com.google.gson.reflect.TypeToken
import com.yoavst.kotlin.`KotlinPackage$SystemServices$69d7d2d0`.connectivityManager
import kotlinx.android.synthetic.main.hot_layout.*

/**
 * Created by Administrator on 2017/11/9.
 */

class HotFragment : Fragment() {

    private var layoutManager: LinearLayoutManager? = null
    private var adapter: PostAdapter? = null
    private var userInfo: MyUser? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val hotLayout = inflater!!.inflate(R.layout.hot_layout,
                container, false)
        return hotLayout
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()

        pull_to_refresh!!.setOnRefreshListener { loadData() }

        fb!!.setOnClickListener {
            //get local user data information
            userInfo = BmobUser.getCurrentUser(MyUser::class.java)
            //if userinfo is null,it means you did not sign in
            if (userInfo != null) {
                //we will get data from PostActivity
                val intent = Intent(activity, PostActivity::class.java)
                startActivityForResult(intent, 1)
            } else {
                //show a snackbar to tell you to sign in
                Snackbar.make(fb!!, "You are not signin", Snackbar.LENGTH_SHORT)
                        .setAction("Sign in") {
                            val intent = Intent(activity, SigninActivity::class.java)
                            startActivity(intent)
                        }.show()

            }
        }

        rv_posts!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                //show or hide floating button when scroll the recyclerview
                if (dy > 0)
                    fb!!.hide()
                else if (dy < 0)
                    fb!!.show()
            }
        })

    }

    /**
     * show data on recyclerview
     * @networkCode 1 -> online
     *              0 -> offline
     */

    private fun refreshRecyclerView(networkCode: Int) {

        when (networkCode) {
            //query data via bmob sdk
            1 -> {
                pull_to_refresh!!.setRefreshing(true)
                val query = BmobQuery<Post>()
                //        query.setLimit(15)
                query.include("author")
                query.findObjects(object : FindListener<Post>() {

                    override fun done(`object`: List<Post>, e: BmobException?) {
                        if (e == null) {
                            Collections.reverse(`object`)
                            //save the data to shareprefs in case of network offline status
                            saveListToPrefs(`object`)

                            layoutManager = LinearLayoutManager(activity)
                            rv_posts!!.layoutManager = layoutManager
                            adapter = PostAdapter(`object`,1)
                            rv_posts!!.adapter = adapter
                            Snackbar.make(rv_posts!!, "refresh successfully", Snackbar.LENGTH_SHORT).show()
                            pull_to_refresh!!.setRefreshing(false)

                        } else {
                            Snackbar.make(rv_posts!!, "refresh failed", Snackbar.LENGTH_SHORT).show()
                            pull_to_refresh!!.setRefreshing(false)
                        }
                    }

                })}
            //show data from shareprefs
            0 -> {
                val appSharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(activity.getApplicationContext())
                val gson = Gson()
                val json = appSharedPrefs.getString("MyObject", "")
                val type = object : TypeToken<List<Post>>() {
                }.type
                val postList: List<Post> = gson.fromJson(json,type)

                layoutManager = LinearLayoutManager(activity)
                rv_posts!!.layoutManager = layoutManager
                adapter = PostAdapter(postList,0)
                rv_posts!!.adapter = adapter
                Snackbar.make(rv_posts!!, "please check your network status", Snackbar.LENGTH_SHORT).show()
                pull_to_refresh!!.setRefreshing(false)
            }

        }

    }

    private fun loadData(){
        //check network status
        val conMgr = connectivityManager(context)
        val activeNetwork = conMgr.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected) {
            // notify user online and load online data
            refreshRecyclerView(1)
        } else {
            // notify user offline and load local data
            refreshRecyclerView(0)
        }
    }

    /**
     * save list<post> to sharepreferences
     */
    private fun saveListToPrefs(postList: List<Post>){
        val appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(activity.applicationContext)
        val prefsEditor = appSharedPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(postList)
        prefsEditor.putString("MyObject", json)
        prefsEditor.commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            //receive data from PostActivity
            1 -> if (resultCode == RESULT_OK) {
                val returnedData = data!!.getStringExtra("data_return")
                //check the receive data is empty or not
                if (returnedData == "") {
                    Toast.makeText(activity, "empty text", Toast.LENGTH_SHORT).show()
                } else {
                    //send data to server via bmob sdk
                    val user = BmobUser.getCurrentUser(MyUser::class.java)
                    val post = Post()
                    post.content = returnedData
                    post.author = user
                    post.save(object : SaveListener<String>() {

                        override fun done(objectId: String, e: BmobException?) {
                            if (e == null) {
                                Toast.makeText(activity, "post successfully", Toast.LENGTH_SHORT).show()
                                loadData()
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