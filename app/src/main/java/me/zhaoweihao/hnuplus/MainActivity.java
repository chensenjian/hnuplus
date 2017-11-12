package me.zhaoweihao.hnuplus;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

/**
 * Created by zhaoweihaoChina on 2017/11/9.
 * github:https://github.com/zhaoweihaoChina
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Butter Knife
     */
    @BindView(R.id.hot_layout)  View hotLayout;
    @BindView(R.id.contacts_layout)  View contactsLayout;
    @BindView(R.id.news_layout)  View newsLayout;
    @BindView(R.id.user_layout)  View userLayout;
    @BindView(R.id.hot_image)  ImageView hotImage;
    @BindView(R.id.contacts_image)  ImageView contactsImage;
    @BindView(R.id.news_image)  ImageView newsImage;
    @BindView(R.id.user_image)  ImageView userImage;
    @BindView(R.id.hot_text)  TextView hotText;
    @BindView(R.id.contacts_text)  TextView contactsText;
    @BindView(R.id.news_text)  TextView newsText;
    @BindView(R.id.user_text)  TextView userText;

    /**
     * 用于展示的四个Fragment
     */
    private HotFragment hotFragment;
    private ContactsFragment contactsFragment;
    private NewsFragment newsFragment;
    private UserFragment userFragment;

    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this, "a15e40755375ee7434e6be8c000c184b");

        // 初始化布局元素
        ButterKnife.bind(this);
        bindListener();
        fragmentManager = getSupportFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
    }

    private void bindListener(){
        hotLayout.setOnClickListener(this);
        contactsLayout.setOnClickListener(this);
        newsLayout.setOnClickListener(this);
        userLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hot_layout:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.contacts_layout:
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.news_layout:
                // 当点击了动态tab时，选中第3个tab
                setTabSelection(2);
                break;
            case R.id.user_layout:
                // 当点击了设置tab时，选中第4个tab
                setTabSelection(3);
                break;
            default:
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                hotImage.setImageResource(R.drawable.ic_home_black_24dp);
                hotText.setTextColor(Color.WHITE);
                if (hotFragment == null) {
                    // 如果HotFragment为空，则创建一个并添加到界面上
                    hotFragment = new HotFragment();
                    transaction.add(R.id.content, hotFragment);
                } else {
                    // 如果HotFragment不为空，则直接将它显示出来
                    transaction.show(hotFragment);
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                contactsImage.setImageResource(R.drawable.ic_home_black_24dp);
                contactsText.setTextColor(Color.WHITE);
                if (contactsFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    contactsFragment = new ContactsFragment();
                    transaction.add(R.id.content, contactsFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(contactsFragment);
                }
                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                newsImage.setImageResource(R.drawable.ic_home_black_24dp);
                newsText.setTextColor(Color.WHITE);
                if (newsFragment == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.content, newsFragment);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(newsFragment);
                }
                break;
            case 3:
            default:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                userImage.setImageResource(R.drawable.ic_home_black_24dp);
                userText.setTextColor(Color.WHITE);
                if (userFragment == null) {
                    // 如果userFragment为空，则创建一个并添加到界面上
                    userFragment = new UserFragment();
                    transaction.add(R.id.content, userFragment);
                } else {
                    // 如果userFragment不为空，则直接将它显示出来
                    transaction.show(userFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        hotImage.setImageResource(R.drawable.ic_home_grey_24dp);
        hotText.setTextColor(Color.parseColor("#82858b"));
        contactsImage.setImageResource(R.drawable.ic_home_grey_24dp);
        contactsText.setTextColor(Color.parseColor("#82858b"));
        newsImage.setImageResource(R.drawable.ic_home_grey_24dp);
        newsText.setTextColor(Color.parseColor("#82858b"));
        userImage.setImageResource(R.drawable.ic_home_grey_24dp);
        userText.setTextColor(Color.parseColor("#82858b"));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (hotFragment != null) {
            transaction.hide(hotFragment);
        }
        if (contactsFragment != null) {
            transaction.hide(contactsFragment);
        }
        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
        if (userFragment != null) {
            transaction.hide(userFragment);
        }
    }

}
