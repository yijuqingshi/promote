package com.ztdh.promote.ui.user.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.ztdh.promote.PromoteApp;
import com.ztdh.promote.R;
import com.ztdh.promote.model.api.ApiHelper;
import com.ztdh.promote.model.bean.Icoin;
import com.ztdh.promote.model.bean.Reponse;
import com.ztdh.promote.model.bean.UserAction;
import com.ztdh.promote.ui.login.loginActivity;
import com.ztdh.promote.utils.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{


    private List<UserAction> data = new ArrayList<>();

    private Context context;

    private Dialog mDialog;

    public UserAdapter(Context context){
        this.context = context;
    }
    public List<UserAction> getData() {
        return data;
    }


    public void setData(List<UserAction> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false);
        return new UserViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, final int i) {
        userViewHolder.name.setText(data.get(i).getName());
        userViewHolder.status.setText(data.get(i).getId_user_status());
        userViewHolder.action.setText(data.get(i).getAction());
        final  int potion = i;
        userViewHolder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = data.get(potion).getIntent();
                if (potion == 0){
                    qiandao();
                }else if (potion == data.size() - 2){
                    showTuiGuang();
                }else if (potion == data.size() - 1){
                      logout();
                }else {
                    if (intent != null){
                        context.startActivity(intent);
                    }
                }

            }
        });
    }

    private void logout() {
        OkHttpUtils.post().url(ApiHelper.SERVER_RUL + ApiHelper.LOGOUT).addParams("accessKey",(String)SharePreferenceUtils.getData("accessKey",""))
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

            }

            @Override
            public void onResponse(Reponse<Object> response, int id) {
                    if (response != null && response.getStatus().equals("200")){
                         PromoteApp.fiinishAll();
                         SharePreferenceUtils.putData("accessKey","");
                         Intent intent = new Intent(context, loginActivity.class);
                         context.startActivity(intent);
                    }else {
                         PromoteApp.fiinishAll();
                        SharePreferenceUtils.putData("accessKey","");
                        Intent intent = new Intent(context, loginActivity.class);
                        context.startActivity(intent);
                    }
            }
        });
    }

    private void qiandao() {

        OkHttpUtils.post().url(ApiHelper.SERVER_RUL + ApiHelper.GET_QIAN).addParams("accessKey",(String)SharePreferenceUtils.getData("accessKey",""))
                .addParams("checkinStatus","1").build().execute(new Callback<Reponse<Object>>() {
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
                    if (response != null){
                        if (response.getStatus().equals("200")){
                            Toast.makeText(context,""+ response.getData(),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"签到失败" + response.getData(),Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        });
    }

    private void showTuiGuang() {
        mDialog = new AlertDialog.Builder(context).create();
        mDialog.show();
        Window window = mDialog.getWindow();
        window.setContentView(R.layout.chongzhi);
        TextView viewById = (TextView)window.findViewById(R.id.id_tv_chongzhi);
        viewById.setText((String)SharePreferenceUtils.getData("userpushurl",""));
        Button btn = window.findViewById(R.id.id_btn_fuzhi);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.id_user_name)
        TextView name;

        @BindView(R.id.id_user_status)
        TextView status;

        @BindView(R.id.id_user_action)
        Button action;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
