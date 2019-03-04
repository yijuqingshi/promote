package com.ztdh.promote.ui.user;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.base.IBaseActivity;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.ztdh.promote.R;
import com.ztdh.promote.model.api.ApiHelper;
import com.ztdh.promote.model.bean.Reponse;
import com.ztdh.promote.ui.BaseActivity;
import com.ztdh.promote.utils.SharePreferenceUtils;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileWithBitmapCallback;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class IdentityActivity extends BaseActivity implements IBaseActivity , View.OnClickListener {


    @BindView(R.id.id_common_back)
    ImageView back;

    @BindView(R.id.id_common_title)
    TextView title;

    @BindView(R.id.id_iden_ed_cont)
    EditText idIdenEdCont;
    @BindView(R.id.id_iden_ed_xing)
    EditText idIdenEdXing;
    @BindView(R.id.id_iden_ed_name)
    EditText idIdenEdName;
    @BindView(R.id.id_iden_check_shen)
    CheckBox idIdenCheckShen;
    @BindView(R.id.id_iden_check_hu)
    CheckBox idIdenCheckHu;
    @BindView(R.id.id_iden_ev_number)
    EditText idIdenEvNumber;
    @BindView(R.id.id_iden_im_zheng)
    ImageView idIdenImZheng;
    @BindView(R.id.id_iden_im_fan)
    ImageView idIdenImFan;
    @BindView(R.id.id_iden_im_chi)
    ImageView idIdenImChi;
    @BindView(R.id.id_iden_btn_commit)
    Button idIdenBtnCommit;

    private File zhengFile;

    private File fanFile;

    private File chiFile;

    private  String zheng;
    private  String fan;
    private  String chi;

   private String contory;
   private String xing;
   private   String name;
   private  String number;


    public static final int TYPE_CHI =3;
    public static final int TYPE_ZHENG =1;
    public static final int TYPE_FAN =2;

    public static final int MY_ADD_CASE_CALL_PHONE2 =0;


    private  boolean isHu = false;

    @Override
    public int getLayoutId() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.identity;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initViewAndEvent() {
        mDialog = new AlertDialog.Builder(this).create();
        title.setText("实名认证");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        idIdenCheckHu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                idIdenCheckShen.setChecked(!isChecked);
                isHu = isChecked;
            }
        });
        idIdenCheckShen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                idIdenCheckHu.setChecked(!isChecked);
                isHu = !isChecked;
            }
        });
        idIdenBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitIdenInfo();
            }
        });
        idIdenImZheng.setOnClickListener(this);
        idIdenImFan.setOnClickListener(this);
        idIdenImChi.setOnClickListener(this);

    }

    private   Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case TYPE_ZHENG:
                    uploadFileFan();
                    break;
                case TYPE_FAN:
                    uploadFileChi();
                    break;
                case TYPE_CHI:
                    commitInfo();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null){
            mHandler.removeMessages(TYPE_CHI);
            mHandler.removeMessages(TYPE_FAN);
            mHandler.removeMessages(TYPE_ZHENG);
            mHandler = null;

        }
    }

    private void uploadFileZheng(){

        OkHttpUtils.post().url(ApiHelper.SERVER_RUL+ ApiHelper.UPLOAD_FILE).addFile("file","jpg",zhengFile).addParams("accessKey",(String) SharePreferenceUtils.getData("accessKey",""))
                .build().execute(new Callback<Reponse<Object>>() {
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
                mDialog.cancel();
            }

            @Override
            public void onResponse(Reponse<Object> response, int id) {
                if (response != null && response.getStatus().equals("200")){
                    Map<String, Object> data = (Map<String, Object>) response.getData();
                    String fileName = (String) data.get("fileName");
                    if (!fileName.equals("")){
                        zheng = fileName;
                        Message message = mHandler.obtainMessage();
                        message.what = TYPE_ZHENG;
                        message.sendToTarget();
                    }
                }else {
                    mDialog.cancel();
                }
            }
        });

    }

    private void uploadFileFan(){

        OkHttpUtils.post().url(ApiHelper.SERVER_RUL+ ApiHelper.UPLOAD_FILE).addFile("file","jpg",fanFile).addParams("accessKey",(String) SharePreferenceUtils.getData("accessKey",""))
                .build().execute(new Callback<Reponse<Object>>() {
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
                mDialog.cancel();
            }

            @Override
            public void onResponse(Reponse<Object> response, int id) {
                if (response != null && response.getStatus().equals("200")){
                    Map<String, Object> data = (Map<String, Object>) response.getData();
                    String fileName = (String) data.get("fileName");
                    if (!fileName.equals("")){
                        fan = fileName;
                        Message message = mHandler.obtainMessage();
                        message.what = TYPE_FAN;
                        message.sendToTarget();
                    }
                }else {
                    mDialog.cancel();
                }
            }
        });

    }


    private void uploadFileChi(){

        OkHttpUtils.post().url(ApiHelper.SERVER_RUL+ ApiHelper.UPLOAD_FILE).addFile("file","jpg",zhengFile).addParams("accessKey",(String) SharePreferenceUtils.getData("accessKey",""))
                .build().execute(new Callback<Reponse<Object>>() {
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
                mDialog.cancel();
            }

            @Override
            public void onResponse(Reponse<Object> response, int id) {
                if (response != null && response.getStatus().equals("200")){
                    Map<String, Object> data = (Map<String, Object>) response.getData();
                    String fileName = (String) data.get("fileName");
                    if (!fileName.equals("")){
                        chi = fileName;
                        Message message = mHandler.obtainMessage();
                        message.what = TYPE_CHI;
                        message.sendToTarget();
                    }
                }else {
                    mDialog.cancel();
                }
            }
        });

    }

    private void commitIdenInfo() {

         mDialog.setTitle("正在提交...");
         mDialog.show();
         contory = idIdenEdCont.getText().toString();
         xing = idIdenEdXing.getText().toString();
         name = idIdenEdName.getText().toString();
         number = idIdenEvNumber.getText().toString();
        if (TextUtils.isEmpty(contory)){
            Toast.makeText(this,"国籍不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(xing)){
            Toast.makeText(this,"姓不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(number)){
            Toast.makeText(this,"证件号码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        if (fanFile != null && zhengFile != null && chiFile != null){
            uploadFileZheng();
        }else {
            Toast.makeText(this,"证件照片不能为空",Toast.LENGTH_SHORT).show();
        }

    }


    private void commitInfo(){
        if(!zheng.equals("")&& !fan.equals("") && !chi.equals("")){
            OkHttpUtils.post().url(ApiHelper.SERVER_RUL+ ApiHelper.UPLOAD_INFO).addParams("accessKey",(String)SharePreferenceUtils.getData("accessKey","'")).
                    addParams("country",contory)
                    .addParams("surname",xing)
                    .addParams("tName",name)
                    .addParams("idcardType",isHu?"2":"1")
                    .addParams("idcardNum",number)
                    .addParams("idcardPositivePhoto",zheng)
                    .addParams("idcardBackPhoto",fan)
                    .addParams("ex1",chi).build().execute(new Callback<Reponse<Object>>() {
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
                    mDialog.cancel();
                }

                @Override
                public void onResponse(Reponse<Object> response, int id) {
                    if (response != null && response.getStatus().equals("200")){
                        Toast.makeText(IdentityActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(IdentityActivity.this,"上传失败",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }else {
            mDialog.cancel();
            Toast.makeText(IdentityActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.id_iden_btn_commit:
                 commitIdenInfo();
                 break;
             case R.id.id_iden_im_zheng:
                 getImage(TYPE_ZHENG);
                 break;
             case R.id.id_iden_im_fan:
                 getImage(TYPE_FAN);
                 break;
             case R.id.id_iden_im_chi:
                 getImage(TYPE_CHI);
                 break;
         }
    }

    private void getImage(int type){

        if (ContextCompat.checkSelfPermission(IdentityActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(IdentityActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    type);

        } else {
            choosePhoto(type);
        }

    }
    /**
     * 打开相册
     */
    private void choosePhoto(int type) {
        //这是打开系统默认的相册(就是你系统怎么分类,就怎么显示,首先展示分类列表)
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, type);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto(requestCode);
            } else {
                //"权限拒绝");
                // TODO: 2018/12/4 这里可以给用户一个提示,请求权限被拒绝了
            }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == Activity.RESULT_OK
                && null != data) {
            try {
                Uri selectedImage = data.getData();
                Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                Tiny.getInstance().source(selectedImage).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
                    @Override
                    public void callback(boolean isSuccess, Bitmap bitmap, String outfile, Throwable t) {
                        saveImageToServer(bitmap, outfile,requestCode);
                    }
                });
            } catch (Exception e) {
                //"上传失败");
            }
        }
    }
    private void saveImageToServer(final Bitmap bitmap, String outfile,int type) {
        File file = new File(outfile);

        // TODO: 2018/12/4  这里就可以将图片文件 file 上传到服务器,上传成功后可以将bitmap设置给你对应的图片展示
        switch (type){
            case TYPE_ZHENG:
                idIdenImZheng.setImageBitmap(bitmap);
                zhengFile = file;
                break;
            case TYPE_FAN:
                idIdenImFan.setImageBitmap(bitmap);
                fanFile = file;
                break;
            case TYPE_CHI:
                chiFile = file;
                idIdenImChi.setImageBitmap(bitmap);
                break;
        }
    }
}
