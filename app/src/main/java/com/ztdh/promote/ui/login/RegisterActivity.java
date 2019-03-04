package com.ztdh.promote.ui.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.common.base.IBaseActivity;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.ztdh.promote.R;
import com.ztdh.promote.model.api.ApiHelper;
import com.ztdh.promote.model.bean.Reponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements IBaseActivity , View.OnClickListener {

    @BindView(R.id.id_regiter_phone)
    EditText idRegiterPhone;
    @BindView(R.id.id_regiter_code)
    EditText idRegiterCode;
    @BindView(R.id.id_regiter_sendcode)
    Button idRegiterSendcode;
    @BindView(R.id.id_regiter_password)
    EditText idRegiterPassword;
    @BindView(R.id.id_regiter_commit)
    EditText idRegiterCommit;
    @BindView(R.id.id_regiter_firend_code)
    EditText idRegiterFirendCode;
    @BindView(R.id.id_regiter_commit_btn)
    Button idRegiterCommitBtn;

    private String moblie;

    private boolean hassend;

    @Override
    public int getLayoutId() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.register;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initViewAndEvent() {
        idRegiterCommitBtn.setOnClickListener(this);
        idRegiterSendcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_regiter_sendcode:
                sendCode();
                break;
            case R.id.id_regiter_commit_btn:
                commit();
                break;
        }
    }

    private void commit() {
        String code = idRegiterCode.getText().toString().trim();
        String password = idRegiterPassword.getText().toString();
        String password_commit = idRegiterCommit.getText().toString();
        String friend_code = idRegiterFirendCode.getText().toString();
        moblie = idRegiterPhone.getText().toString().trim();

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password_commit)){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(code)){
            Toast.makeText(this,"验证码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        if (!hassend){
            Toast.makeText(this,"没有发送验证码",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(password_commit)){
            Toast.makeText(this,"输入密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpUtils.post().url(ApiHelper.SERVER_RUL + ApiHelper.REGISTER).addParams("ex1","86").addParams("mobile",moblie)
                .addParams("mobileCheckcode",code).addParams("loginPass",password).addParams("recomId",friend_code).build()
                .execute(new Callback<Reponse<Object>>() {
                    @Override
                    public Reponse<Object> parseNetworkResponse(Response response, int id) throws Exception {

                        if (response.code() == 200){
                            String string = response.body().string();
                            Gson gson = new Gson();
                            return ( Reponse<Object>)gson.fromJson(string,Reponse.class);
                        }
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Reponse<Object> response, int id) {
                        if (response != null && response.getStatus().equals("200")){
                            hassend = true;
                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(RegisterActivity.this,"注册失败 " + response.getMsg(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    private void sendCode() {
        moblie = idRegiterPhone.getText().toString().trim();
        if (!TextUtils.isEmpty(moblie)){
            OkHttpUtils.post().url(ApiHelper.SERVER_RUL + ApiHelper.SEND_CODE ).addParams("mobile",moblie)
                    .addParams("ex1","86").build().execute(new Callback<Reponse<Object>>() {
                @Override
                public Reponse<Object> parseNetworkResponse(Response response, int id) throws Exception {
                    if (response.code() == 200){
                        String string = response.body().string();
                        Gson gson = new Gson();
                        return ( Reponse<Object>)gson.fromJson(string,Reponse.class);
                    }
                    return null;
                }

                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(Reponse<Object> response, int id) {
                    if (response != null && response.getStatus().equals("200")){
                        hassend = true;
                        Toast.makeText(RegisterActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
