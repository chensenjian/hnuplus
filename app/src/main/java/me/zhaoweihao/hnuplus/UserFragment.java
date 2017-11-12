package me.zhaoweihao.hnuplus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

import me.zhaoweihao.hnuplus.JavaBean.MyUser;


/**
 * Created by Administrator on 2017/11/9.
 */

public class UserFragment extends Fragment {

    /**
     * Butter Knife
     */
    @BindView(R.id.btn_signout) Button signoutButton;
    @BindView(R.id.btn_signin_1) Button signinButton;
    @BindView(R.id.tv_signinstatus) TextView  signinStatusTextView;

    private MyUser userInfo;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View userLayout = inflater.inflate(R.layout.user_layout,
                container, false);

        ButterKnife.bind(this, userLayout);

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

        return userLayout;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null; }
    }

    @Override
    public void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        userInfo = BmobUser.getCurrentUser(MyUser.class);

        if(userInfo != null){
            signinStatusTextView.setText("Welcome "+userInfo.getUsername());
            signinButton.setVisibility(View.GONE);
            signoutButton.setVisibility(View.VISIBLE);
            progressDialog.hide();
        }else{
            signinStatusTextView.setText("You have not signin");
            signinButton.setVisibility(View.VISIBLE);
            signoutButton.setVisibility(View.GONE);
            progressDialog.hide();
        }

    }

}
