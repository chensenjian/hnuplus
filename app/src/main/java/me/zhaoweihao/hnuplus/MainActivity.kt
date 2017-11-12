package me.zhaoweihao.hnuplus

import android.graphics.Color
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import cn.bmob.v3.Bmob
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by zhaoweihaoChina on 2017/11/9.
 * github:https://github.com/zhaoweihaoChina
 */

class MainActivity : AppCompatActivity(){

    /**
     * 用于展示的四个Fragment
     */
    private var hotFragment: HotFragment? = null
    private var contactsFragment: ContactsFragment? = null
    private var newsFragment: NewsFragment? = null
    private var userFragment: UserFragment? = null

    /**
     * 用于对Fragment进行管理
     */
    private var fragmentManager: FragmentManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        Bmob.initialize(this, "a15e40755375ee7434e6be8c000c184b")

        bindListener()
        fragmentManager = supportFragmentManager

        // select 0 tab when first run
        setTabSelection(0)
    }

    private fun bindListener() {
        hot_layout!!.setOnClickListener{setTabSelection(0)}
        contacts_layout!!.setOnClickListener{setTabSelection(1)}
        news_layout!!.setOnClickListener{setTabSelection(2)}
        user_layout!!.setOnClickListener{setTabSelection(3)}
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     */
    private fun setTabSelection(index: Int) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection()
        // 开启一个Fragment事务
        val transaction = fragmentManager!!.beginTransaction()
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction)
        when (index) {
            0 -> {
                // 当点击了消息tab时，改变控件的图片和文字颜色
                hot_image!!.setImageResource(R.drawable.ic_home_black_24dp)
                hot_text!!.setTextColor(Color.WHITE)
                if (hotFragment == null) {
                    // 如果HotFragment为空，则创建一个并添加到界面上
                    hotFragment = HotFragment()
                    transaction.add(R.id.content, hotFragment)
                } else {
                    // 如果HotFragment不为空，则直接将它显示出来
                    transaction.show(hotFragment)
                }
            }
            1 -> {
                contacts_image!!.setImageResource(R.drawable.ic_home_black_24dp)
                contacts_text!!.setTextColor(Color.WHITE)
                if (contactsFragment == null) {
                    contactsFragment = ContactsFragment()
                    transaction.add(R.id.content, contactsFragment)
                } else {
                    transaction.show(contactsFragment)
                }
            }
            2 -> {
                news_image!!.setImageResource(R.drawable.ic_home_black_24dp)
                news_text!!.setTextColor(Color.WHITE)
                if (newsFragment == null) {
                    newsFragment = NewsFragment()
                    transaction.add(R.id.content, newsFragment)
                } else {
                    transaction.show(newsFragment)
                }
            }
            3 -> {
                user_image!!.setImageResource(R.drawable.ic_home_black_24dp)
                user_text!!.setTextColor(Color.WHITE)
                if (userFragment == null) {
                    userFragment = UserFragment()
                    transaction.add(R.id.content, userFragment)
                } else {
                    transaction.show(userFragment)
                }
            }
        }
        transaction.commit()
    }

    /**
     * 清除掉所有的选中状态。
     */
    private fun clearSelection() {
        hot_image!!.setImageResource(R.drawable.ic_home_grey_24dp)
        hot_text!!.setTextColor(Color.parseColor("#82858b"))
        contacts_image!!.setImageResource(R.drawable.ic_home_grey_24dp)
        contacts_text!!.setTextColor(Color.parseColor("#82858b"))
        news_image!!.setImageResource(R.drawable.ic_home_grey_24dp)
        news_text!!.setTextColor(Color.parseColor("#82858b"))
        user_image!!.setImageResource(R.drawable.ic_home_grey_24dp)
        user_text!!.setTextColor(Color.parseColor("#82858b"))
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        if (hotFragment != null) {
            transaction.hide(hotFragment)
        }
        if (contactsFragment != null) {
            transaction.hide(contactsFragment)
        }
        if (newsFragment != null) {
            transaction.hide(newsFragment)
        }
        if (userFragment != null) {
            transaction.hide(userFragment)
        }
    }

}
