package com.ztdh.promote.ui.main.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.ztdh.promote.R;
import com.ztdh.promote.model.api.ApiHelper;
import com.ztdh.promote.model.bean.DingCun;
import com.ztdh.promote.model.bean.Icoin;
import com.ztdh.promote.model.bean.Reponse;
import com.ztdh.promote.utils.SharePreferenceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class DingCunAdapter extends RecyclerView.Adapter<DingCunAdapter.DingCunViewHolder> {

    private List<DingCun> data;

    private Context context;

    private Icoin mIcoin;
    public List<DingCun> getData() {
        return data;
    }

    public DingCunAdapter(Context context,Icoin icoin){
        this.context = context;
        this.mIcoin = icoin;
    }


    public void setData(List<DingCun> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public DingCunAdapter.DingCunViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dingcun, viewGroup, false);
        return new DingCunViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull DingCunAdapter.DingCunViewHolder mainViewHolder, int i) {
            mainViewHolder.name.setText(data.get(i).getProjectName());
            final DingCun dingCun = data.get(i);
            mainViewHolder.action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(mIcoin,dingCun);
                }
            });
    }
    private AlertDialog mDialog;

    private void showDialog(final Icoin icoin, final DingCun dingCun){

        mDialog = new AlertDialog.Builder(context).create();
        mDialog.setTitle("定存");
        mDialog.setView(new EditText(context));
        mDialog.show();
        Window window = mDialog.getWindow();
        window.setContentView(R.layout.popup);
        TextView textView = window.findViewById(R.id.id_dingcun_textview);
        final EditText editText = window.findViewById(R.id.id_dingcun_editview);
        EditText editText_tixian = window.findViewById(R.id.id_tixian_editview);
        editText_tixian.setVisibility(View.GONE);
        Button button = window.findViewById(R.id.id_dingcun_btn);
        textView.setText("可用"+icoin.getName() +"数量:" + icoin.getAvailable() + "， 最少定存数量为:" + dingCun.getSmallCurrency());
        editText.setHint("请输入定存的数量");
        button.setText("定存");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = editText.getText().toString();
                addDingCun(amount,dingCun,icoin);
            }
        });


    }

    private void addDingCun(String amount, DingCun dingCun,Icoin icoin) {

        if (TextUtils.isEmpty(amount)){
            Toast.makeText(context,"定存的数量不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        double amount1 = Double.parseDouble(amount);
        if (amount1 > icoin.getAvailable()){
            Toast.makeText(context,"可用数量不够",Toast.LENGTH_SHORT).show();
            return;
        }
        if (amount1 < dingCun.getSmallCurrency()){
            Toast.makeText(context,"定存的数量不能少于" + dingCun.getSmallCurrency(),Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpUtils.post().url(ApiHelper.SERVER_RUL + ApiHelper.USER_ADD_PROJECT).addParams("accessKey", (String) SharePreferenceUtils.getData("accessKey",""))
                .addParams("lockProjectId",dingCun.getTid()).addParams("lockCurrency",amount1+ "")
                .build().execute(new Callback<Reponse<Object>>() {
            @Override
            public Reponse<Object> parseNetworkResponse(Response response, int id) throws Exception {
                if (response.code() == 200) {
                    String string = response.body().string();
                    Gson gson = new Gson();
                    return (Reponse<Object>) gson.fromJson(string, Reponse.class);
                }
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(Reponse<Object> response, int id) {
                if (response != null && response.getStatus().equals("200")) {
                    Toast.makeText(context,response.getMsg()+ "",Toast.LENGTH_SHORT).show();
                    mDialog.cancel();
                }else {
                    Toast.makeText(context,response.getMsg()+ "",Toast.LENGTH_SHORT).show();
                    mDialog.cancel();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class DingCunViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.id_dingcun_name)
        TextView name;

        @BindView(R.id.id_dingcun_action)
        Button action;


        public DingCunViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
