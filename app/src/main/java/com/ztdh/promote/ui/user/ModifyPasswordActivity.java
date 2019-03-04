package com.ztdh.promote.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.base.IBaseActivity;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.ztdh.promote.PromoteApp;
import com.ztdh.promote.R;
import com.ztdh.promote.model.api.ApiHelper;
import com.ztdh.promote.model.bean.Recommond;
import com.ztdh.promote.model.bean.Reponse;
import com.ztdh.promote.ui.BaseActivity;
import com.ztdh.promote.ui.login.RegisterActivity;
import com.ztdh.promote.ui.login.loginActivity;
import com.ztdh.promote.utils.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class ModifyPasswordActivity extends BaseActivity implements IBaseActivity {

    @BindView(R.id.id_common_back)
    ImageView back;
    @BindView(R.id.id_common_title)
    TextView title;
    @BindView(R.id.id_modify_ev_moblie)
    EditText idModifyEvMoblie;
    @BindView(R.id.id_ev_code)
    EditText idEvCode;
    @BindView(R.id.id_btn_sendcode)
    Button idBtnSendcode;
    @BindView(R.id.id_modify_password)
    EditText idModifyPassword;
    @BindView(R.id.id_modify_password_commit)
    EditText idModifyPasswordCommit;
    @BindView(R.id.id_modify_commit)
    Button idModifyCommit;

    private boolean inlogin;

    private boolean isSendCode;

    private String mobile;

    @Override
    public int getLayoutId() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.modify;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initViewAndEvent() {
        inlogin = getIntent().getBooleanExtra("inlogin", true);
        mobile = (String)SharePreferenceUtils.getData("mobile","");
        idModifyEvMoblie.setText(mobile);
        if (inlogin) {
            title.setText("修改登录密码");
        } else {
            title.setText("修改资金密码");
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        idBtnSendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();
            }
        });
        idModifyCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
    }

    private void commit() {
        String code = idEvCode.getText().toString();
        String password = idModifyPassword.getText().toString();
        String password_commit = idModifyPasswordCommit.getText().toString();
        if (TextUtils.isEmpty(password)){
            Toast.makeText(ModifyPasswordActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(code)){
            Toast.makeText(ModifyPasswordActivity.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(password_commit)){
            Toast.makeText(ModifyPasswordActivity.this,"输入的两次密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isSendCode){
            Toast.makeText(ModifyPasswordActivity.this,"没有发送验证码",Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpUtils.post().url(ApiHelper.SERVER_RUL + ApiHelper.SET_PASSWORD).addParams("mobile",mobile)
                .addParams("mobileCode",code).addParams("mobileAreaCode","86")
                .addParams("pass",password).addParams("type",inlogin ? "1" : "2").build().execute(new Callback<Reponse<Object>>() {
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
                    Toast.makeText(ModifyPasswordActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    SharePreferenceUtils.putData("accessKey","");
                    PromoteApp.fiinishAll();
                    Intent intent = new Intent(ModifyPasswordActivity.this,loginActivity.class);
                    startActivity(intent);

                }
            }
        });



    }

    private void sendCode(){
        OkHttpUtils.post().url(ApiHelper.SERVER_RUL + ApiHelper.SEND_CODE).addParams("mobile",mobile)
                .addParams("ex1","86").build().execute(new Callback<Reponse<Object>>() {
            @Override
            public Reponse<Object> parseNetworkResponse(Response response, int id) throws Exception {

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
                    isSendCode = true;
                    Toast.makeText(ModifyPasswordActivity.this,"发送成功",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }



}
