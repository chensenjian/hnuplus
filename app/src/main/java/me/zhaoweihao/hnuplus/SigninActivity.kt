package me.zhaoweihao.hnuplus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager

import android.os.Build
import android.view.View


class SigninActivity : AppCompatActivity() {

    private var fragmentManager: FragmentManager? = null
    private var signinFragment: SigninFragment? = null
    private var signupFragment: SignupFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(android.R.id.content).systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        setContentView(R.layout.activity_signin)

        fragmentManager = supportFragmentManager

        // 开启一个Fragment事务
        val transaction = (fragmentManager as FragmentManager?)!!.beginTransaction()

        if (signinFragment == null) {
            // 如果MessageFragment为空，则创建一个并添加到界面上
            signinFragment = SigninFragment()
            transaction.add(R.id.fl_signin, signinFragment)
        } else {
            // 如果MessageFragment不为空，则直接将它显示出来
            transaction.show(signinFragment)
        }
        transaction.commit()

    }

    public fun toSignupFragment(){

        // 开启一个Fragment事务
        val transaction = (fragmentManager as FragmentManager?)!!.beginTransaction()

        if (signinFragment != null) {
            transaction.hide(signinFragment)
        }

        if (signupFragment == null) {
            // 如果MessageFragment为空，则创建一个并添加到界面上
            signupFragment = SignupFragment()
            transaction.add(R.id.fl_signin, signupFragment)
        } else {
            // 如果MessageFragment不为空，则直接将它显示出来
            transaction.show(signupFragment)
        }
        transaction.commit()

    }





}
