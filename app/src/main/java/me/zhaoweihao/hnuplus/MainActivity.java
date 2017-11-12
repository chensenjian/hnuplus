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

import cn.bmob.v3.Bmob;

/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 用于展示热点的Fragment
     */
    private HotFragment hotFragment;

    /**
     * 用于展示联系人的Fragment
     */
    private ContactsFragment contactsFragment;

    /**
     * 用于展示动态的Fragment
     */
    private NewsFragment newsFragment;

    /**
     * 用于展示设置的Fragment
     */
    private UserFragment userFragment;

    /**
     * 消息界面布局
     */
    private View hotLayout;

    /**
     * 联系人界面布局
     */
    private View contactsLayout;

    /**
     * 动态界面布局
     */
    private View newsLayout;

    /**
     * 设置界面布局
     */
    private View userLayout;

    /**
     * 在Tab布局上显示消息图标的控件
     */
    private ImageView hotImage;

    /**
     * 在Tab布局上显示联系人图标的控件
     */
    private ImageView contactsImage;

    /**
     * 在Tab布局上显示动态图标的控件
     */
    private ImageView newsImage;

    /**
     * 在Tab布局上显示设置图标的控件
     */
    private ImageView userImage;

    /**
     * 在Tab布局上显示消息标题的控件
     */
    private TextView hotText;

    /**
     * 在Tab布局上显示联系人标题的控件
     */
    private TextView contactsText;

    /**
     * 在Tab布局上显示动态标题的控件
     */
    private TextView newsText;

    /**
     * 在Tab布局上显示设置标题的控件
     */
    private TextView userText;

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
        initViews();
        fragmentManager = getSupportFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
    }

    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
        hotLayout = findViewById(R.id.hot_layout);
        contactsLayout = findViewById(R.id.contacts_layout);
        newsLayout = findViewById(R.id.news_layout);
        userLayout = findViewById(R.id.user_layout);
        hotImage = (ImageView) findViewById(R.id.hot_image);
        contactsImage = (ImageView) findViewById(R.id.contacts_image);
        newsImage = (ImageView) findViewById(R.id.news_image);
        userImage = (ImageView) findViewById(R.id.user_image);
        hotText = (TextView) findViewById(R.id.hot_text);
        contactsText = (TextView) findViewById(R.id.contacts_text);
        newsText = (TextView) findViewById(R.id.news_text);
        userText = (TextView) findViewById(R.id.user_text);
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
     *
     * @param index
     *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
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
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
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

    @Override
    protected void onStop() {
        super.onStop();
    }





}
