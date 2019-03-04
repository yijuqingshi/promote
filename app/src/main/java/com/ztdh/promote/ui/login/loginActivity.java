package com.ztdh.promote.ui.login;

import android.app.AlertDialog;
import android.content.Intent;
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
import com.ztdh.promote.ui.BaseActivity;
import com.ztdh.promote.ui.main.MainActivity;
import com.ztdh.promote.utils.SharePreferenceUtils;

import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class loginActivity extends BaseActivity implements IBaseActivity , View.OnClickListener {

    @BindView(R.id.id_login_regiter)
    Button regiter;
    @BindView(R.id.id_login_mobile)
    EditText idLoginMobile;
    @BindView(R.id.id_loginp_password)
    EditText idLoginpPassword;
    @BindView(R.id.id_login_commit)
    Button idLoginCommit;

    @Override
    public int getLayoutId() {
        String accesskey = (String) SharePreferenceUtils.getData("accessKey", "");
        if (!accesskey.equals("")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.login;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initViewAndEvent() {
        mDialog = new AlertDialog.Builder(this).create();
        regiter.setOnClickListener(this);
        idLoginCommit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_login_regiter:
                Intent intent = new Intent(loginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.id_login_commit:
                login();
                break;
        }
    }

    private void login() {

        String mobile = idLoginMobile.getText().toString();
        String password = idLoginpPassword.getText().toString();

        if (TextUtils.isEmpty(mobile)){
            Toast.makeText(loginActivity.this,"手机号码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(loginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        mDialog.setTitle("正在登陆");
        mDialog.show();
        OkHttpUtils.post().url(ApiHelper.SERVER_RUL + ApiHelper.LOGIN).addParams("ex1","86").addParams("mobile",mobile)
                .addParams("loginPass",password).build().execute(new Callback<Reponse<Object>>() {
            @Override
            public Reponse<Object> parseNetworkResponse(Response response, int id) throws Exception {
                mDialog.cancel();
                if (response.code() == 200){
                    String string = response.body().string();
                    Gson gson = new Gson();
                    return (Reponse<Object>)gson.fromJson(string,Reponse.class);
                }
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(Reponse<Object> response, int id) {
                    if (response != null && response.getStatus().equals("200")){

                        Map<String,Object> data = (Map<String, Object>) response.getData();
                        SharePreferenceUtils.putData("accessKey",data.get("accessKey"));
                        Map<String,Object> userPush = (Map<String, Object>) data.get("userPush");
                        String  userpushurl = (String) userPush.get("userPushURL");
                        SharePreferenceUtils.putData("userpushurl",userpushurl);
                        SharePreferenceUtils.putData("userAuth",data.get("userAuth"));
                        SharePreferenceUtils.putData("mobile",data.get("mobile"));
                        SharePreferenceUtils.putData("highPpassffective",data.get("highPpassffective"));
//                        SharePreferenceUtils.putData("username",data.get("username"));
                        Intent intent = new Intent(loginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
            }
        });
    }
}
