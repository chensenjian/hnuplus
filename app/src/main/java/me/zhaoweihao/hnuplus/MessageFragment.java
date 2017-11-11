package me.zhaoweihao.hnuplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.yalantis.phoenix.PullToRefreshView;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import me.zhaoweihao.hnuplus.Adapter.PostAdapter;
import me.zhaoweihao.hnuplus.JavaBean.MyUser;
import me.zhaoweihao.hnuplus.JavaBean.Post;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/11/9.
 */

public class MessageFragment extends Fragment  {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private PostAdapter adapter;
    private PullToRefreshView mPullToRefreshView;
    private FloatingActionButton floatingActionButton;
    private MyUser userInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.hot_layout,
                container, false);

        initViews(messageLayout);



        refreshRecyclerView();

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshRecyclerView();

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo = BmobUser.getCurrentUser(MyUser.class);

                if(userInfo != null){
                    Intent intent = new Intent(getActivity(),PostActivity.class);
                    startActivityForResult(intent,1);
                }else{
                    Snackbar.make(floatingActionButton,"You are not signin",Snackbar.LENGTH_SHORT)
                            .setAction("Sign in", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(),SigninActivity.class);
                                    startActivity(intent);
                                }
                            }).show();

                }

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0)
                    floatingActionButton.hide();
                else if (dy < 0)
                    floatingActionButton.show();
            }
        });

        return messageLayout;
    }

    private void initViews(View view){

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_posts);
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fb);

    }

    private void refreshRecyclerView(){

        mPullToRefreshView.setRefreshing(true);
        BmobQuery<Post> query = new BmobQuery<Post>();
        query.setLimit(15);
        query.include("author");
        query.findObjects(new FindListener<Post>() {

            @Override
            public void done(List<Post> object, BmobException e) {
                if(e==null){
                    Collections.reverse(object);
                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new PostAdapter(object);
                    recyclerView.setAdapter(adapter);
                    Snackbar.make(recyclerView,"refresh successfully",Snackbar.LENGTH_SHORT).show();
                    mPullToRefreshView.setRefreshing(false);

                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });

    }



    @Override
    public void onStart() {
        super.onStart();
//        refreshRecyclerView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){

            case 1:
                if(resultCode == RESULT_OK){
                    String returnedData = data.getStringExtra("data_return");

                    if(returnedData.equals("")){
                        Toast.makeText(getActivity(), "empty text", Toast.LENGTH_SHORT).show();
                    }else{
                        MyUser user = BmobUser.getCurrentUser(MyUser.class);
                        Post post = new Post();
                        post.setContent(returnedData);
                        post.setAuthor(user);
                        post.save(new SaveListener<String>() {

                            @Override
                            public void done(String objectId,BmobException e) {
                                if(e==null){
                                    Toast.makeText(getActivity(), "post successfully", Toast.LENGTH_SHORT).show();
                                    refreshRecyclerView();
                                }else{
                                    Toast.makeText(getActivity(), "post failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

                break;

            default:
        }
    }



}