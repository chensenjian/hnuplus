package me.zhaoweihao.hnuplus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.zhaoweihao.hnuplus.Adapter.CommentAdapter;
import me.zhaoweihao.hnuplus.Interface.MyInterface;
import me.zhaoweihao.hnuplus.JavaBean.Comment;
import me.zhaoweihao.hnuplus.JavaBean.MyUser;
import me.zhaoweihao.hnuplus.JavaBean.Post;

/**
 * Created by Administrator on 2017/11/10.
 */

public class CommentFragment extends Fragment implements MyInterface{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CommentAdapter adapter;
    private TextView commentAuthorTextView,commentContentTextView;
    private ProgressDialog progressDialog;
    private String objectID,authorObjectID;
    private FrameLayout deleteFrameLayout;
    private MyUser user;
    public static final String TAG = "CommentFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comment_layout,
                container, false);
        Log.d(TAG,"onCreateView");

        initViews(view);

        user = BmobUser.getCurrentUser(MyUser.class);

        Intent intent = getActivity().getIntent();
        commentAuthorTextView.setText(intent.getStringExtra("author"));
        commentContentTextView.setText(intent.getStringExtra("content"));
        objectID = intent.getStringExtra("objectID");
        authorObjectID = intent.getStringExtra("authorObjectID");


        if(user == null){
            deleteFrameLayout.setVisibility(View.GONE);
        } else if(user.getObjectId().equals(authorObjectID)){
            deleteFrameLayout.setVisibility(View.VISIBLE);
        }else{
            deleteFrameLayout.setVisibility(View.GONE);
        }

        deleteFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder (getActivity());
                dialog.setTitle("Are you sure to delete this post?");
                dialog.setMessage("This can not be restored");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK,I am sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Post p = new Post();
                        p.setObjectId(objectID);
                        p.delete( new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(getActivity(), "delete successfully", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                }else{
                                    Toast.makeText(getActivity(), "delete failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
                    }
                });
                dialog.setNegativeButton("No,I want cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.show();


            }
        });

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        refreshCommentData(objectID);

        return view;
    }

    private void initViews(View view){

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_comments);
        commentAuthorTextView = (TextView) view.findViewById(R.id.tv_comment_author);
        commentContentTextView = (TextView) view.findViewById(R.id.tv_comment_content);
        deleteFrameLayout = (FrameLayout) view.findViewById(R.id.fl_delete);

    }

    private void refreshCommentData(String objectID){

        BmobQuery<Comment> query = new BmobQuery<Comment>();
        Post post = new Post();
        post.setObjectId(objectID);
        query.addWhereEqualTo("post",new BmobPointer(post));
        query.include("user,post.author");
        query.findObjects(new FindListener<Comment>() {

            @Override
            public void done(List<Comment> objects,BmobException e) {
                if(objects.size()==0){
                    Toast.makeText(getActivity(), "empty comment", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }else{
                Collections.reverse(objects);
//                Toast.makeText(getActivity(), objects.get(0).getUser().getUsername(), Toast.LENGTH_SHORT).show();
                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new CommentAdapter(objects);
                recyclerView.setAdapter(adapter);
                progressDialog.hide();}
            }
        });
    }

    public void myAction(String data) {



        if(user==null){

            Snackbar.make(recyclerView,"You are not signin",Snackbar.LENGTH_SHORT)
                    .setAction("Sign in", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),SigninActivity.class);
                            startActivity(intent);
                        }
                    }).show();

        }else if(data.equals("")){

            Toast.makeText(getActivity(), "Comment content can't be empty", Toast.LENGTH_SHORT).show();

        }else{

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.show();

            Post post = new Post();
            post.setObjectId(objectID);
            final Comment comment = new Comment();
            comment.setContent(data);
            comment.setPost(post);
            comment.setUser(user);
            comment.save(new SaveListener<String>() {

                @Override
                public void done(String objectId,BmobException e) {
                    if(e==null){
                        refreshCommentData(objectID);
                        Toast.makeText(getActivity(), "add commit successfully", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getActivity(), "add commit failed", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null; }
        Log.d(TAG,"onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetach");
    }
}
