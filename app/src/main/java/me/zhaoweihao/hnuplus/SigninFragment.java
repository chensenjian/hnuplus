package me.zhaoweihao.hnuplus;

import android.app.ProgressDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/11/10.
 */

public class SigninFragment extends Fragment{

    /**
     * Butter Knife
     */
    @BindView(R.id.container) LinearLayout mContainer;
    @BindView(R.id.btn_signup_1) Button signupButton;
    @BindView(R.id.btn_signin_2) Button signinButton;
    @BindView(R.id.et_username_1) EditText usernameEditText;
    @BindView(R.id.et_password_1) EditText passwordEditText;
    @BindView(R.id.btn_back_1) Button backButton;

    private AnimationDrawable anim;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View signinLayout = inflater.inflate(R.layout.signin_layout,
                container, false);

        ButterKnife.bind(this, signinLayout);

        anim = (AnimationDrawable) mContainer.getBackground();
        anim.setEnterFadeDuration(6000);
        anim.setExitFadeDuration(2000);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((SigninActivity)getActivity()).toSignupFragment();

            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(username.equals("")||password.equals("")){
                    Toast.makeText(getActivity(), "can't be empty", Toast.LENGTH_SHORT).show();
                }else{

                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(true);
                    progressDialog.show();

                    BmobUser bu2 = new BmobUser();
                    bu2.setUsername(username);
                    bu2.setPassword(password);
                    bu2.login(new SaveListener<BmobUser>() {

                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if(e==null){
                                Toast.makeText(getActivity(), "signin successfully", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                getActivity().finish();
                            }else{
                                Toast.makeText(getActivity(), "signin failed", Toast.LENGTH_SHORT).show();
                                progressDialog.hide();
                            }
                        }
                    });

                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return signinLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning()){
            anim.stop();}
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null; }
    }


}
