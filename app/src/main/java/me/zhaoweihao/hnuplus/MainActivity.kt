package me.zhaoweihao.hnuplus


import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import kotlinx.android.synthetic.main.activity_main.*
import me.zhaoweihao.hnuplus.Constant.Constant
import me.zhaoweihao.hnuplus.Interface.AnotherInterface
import me.zhaoweihao.hnuplus.JavaBean.MyUser
import me.zhaoweihao.hnuplus.JavaBean.Post



/**
 * Created by zhaoweihaoChina on 2017/11/9.
 * github:https://github.com/zhaoweihaoChina
 */
class MainActivity : AppCompatActivity(){

    /**
     * Four Fragment for display
     */
    private var hotFragment: HotFragment? = null
    private var contactsFragment: ContactsFragment? = null
    private var newsFragment: NewsFragment? = null
    private var userFragment: UserFragment? = null

    /**
     * Used to manage Fragment
     */
    private var fragmentManager: FragmentManager? = null

    private var listener: AnotherInterface? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        Bmob.initialize(this, Constant.APP_ID)

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
     * Set the selected tab page based on the index parameter passed in
     */
    private fun setTabSelection(index: Int) {
        // Clear the last selected state before each selection
        clearSelection()
        // Open a Fragment transaction
        val transaction = fragmentManager!!.beginTransaction()
        // Hide all the Fragment first to prevent multiple Fragment displayed on the screen
        hideFragments(transaction)
        when (index) {
            0 -> {
                // When you click the message tab, change the tab's picture and text color
                hot_image!!.setImageResource(R.drawable.ic_home_black_24dp)
                hot_text!!.setTextColor(Color.WHITE)
                if (hotFragment == null) {
                    // If Hot Fragment is empty, create one and add to the screen
                    hotFragment = HotFragment()
                    setListener(hotFragment!!)
                    transaction.add(R.id.content, hotFragment)
                } else {
                    // If Hot Fragment is not empty, it will be displayed directly
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
     * Clear all the selected state
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
     * Hide all the Fragments
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

    public fun gotoPostFragment(){
        val intent = Intent(this, PostActivity::class.java)
                startActivityForResult(intent, 1)
    }

    fun setListener(listener: AnotherInterface) {
        this.listener = listener
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            //receive data from PostActivity
            1 -> if (resultCode == RESULT_OK) {
                val returnedData = data!!.getStringExtra("data_return")
                //check the receive data is empty or not
                if (returnedData == "") {
                    Toast.makeText(this, "empty text", Toast.LENGTH_SHORT).show()
                } else {
                    //send data to server via bmob sdk
                    val user = BmobUser.getCurrentUser(MyUser::class.java)
                    val post = Post()
                    post.content = returnedData
                    post.author = user
                    post.save(object : SaveListener<String>() {

                        override fun done(objectId: String, e: BmobException?) {
                            if (e == null) {
                                Toast.makeText(this@MainActivity, "post successfully", Toast.LENGTH_SHORT).show()
                                listener!!.myMethod()
                            } else {
                                Toast.makeText(this@MainActivity, "post failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }
            }

            2 -> if (resultCode == RESULT_OK){
                Toast.makeText(this@MainActivity,"select successfully",Toast.LENGTH_SHORT).show()
            }

        }
    }


}
