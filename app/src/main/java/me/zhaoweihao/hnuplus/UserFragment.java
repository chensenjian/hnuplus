package me.zhaoweihao.hnuplus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;

import me.zhaoweihao.hnuplus.JavaBean.MyUser;


/**
 * Created by Administrator on 2017/11/9.
 */

public class UserFragment extends Fragment {

    private Button signinButton,signoutButton;
    private TextView signinStatusTextView;
    private MyUser userInfo;
    private ProgressDialog progressDialog;

    public static final String TAG = "UserFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View settingLayout = inflater.inflate(R.layout.setting_layout,
                container, false);
        Log.d(TAG,"onCreateView");
        initViews(settingLayout);

        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
                BmobUser.logOut();
                BmobUser currentUser = BmobUser.getCurrentUser();
                if(currentUser==null){
                    Toast.makeText(getActivity(), "sign out successfully", Toast.LENGTH_SHORT).show();
                    signinStatusTextView.setText("You have not signin");
                    signinButton.setVisibility(View.VISIBLE);
                    signoutButton.setVisibility(View.GONE);
                    progressDialog.hide();
                }
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SigninActivity.class);
                startActivity(intent);
            }
        });

        return settingLayout;
    }

    private void initViews(View view){

        signoutButton = (Button) view.findViewById(R.id.btn_signout);
        signinButton = (Button) view.findViewById(R.id.btn_signin_1);
        signinStatusTextView = (TextView) view.findViewById(R.id.tv_signinstatus);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null; }
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
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        userInfo = BmobUser.getCurrentUser(MyUser.class);

        if(userInfo != null){
//            Toast.makeText(getActivity(), "You have signin", Toast.LENGTH_SHORT).show();
            signinStatusTextView.setText("Welcome "+userInfo.getUsername());
            signinButton.setVisibility(View.GONE);
            signoutButton.setVisibility(View.VISIBLE);
            progressDialog.hide();
        }else{
//            Toast.makeText(getActivity(), "You have not signin", Toast.LENGTH_SHORT).show();
            signinStatusTextView.setText("You have not signin");
            signinButton.setVisibility(View.VISIBLE);
            signoutButton.setVisibility(View.GONE);
            progressDialog.hide();
        }

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
