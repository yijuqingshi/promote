package com.ztdh.promote.ui.main.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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

import com.ztdh.promote.R;
import com.ztdh.promote.model.bean.Icoin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{


    private List<Icoin> data = new ArrayList<>();

    private Context context;

    public MainAdapter(Context context){
        this.context = context;
    }
    public List<Icoin> getData() {
        return data;
    }

    private Dialog mDialog;

    public void setData(List<Icoin> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coin_item, viewGroup, false);
        return new MainViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
         mainViewHolder.available.setText(data.get(i).getAvailable()+"");
         mainViewHolder.freeze.setText(data.get(i).getFreeze()+ "");
         mainViewHolder.name.setText(data.get(i).getName());
         mainViewHolder.icoin.setImageResource(R.mipmap.btc);
         mainViewHolder.chongzhi.setVisibility(data.get(i).isCanChongzhi() ? View.VISIBLE:View.GONE);
         mainViewHolder.tixian.setVisibility(data.get(i).isCanTixian() ? View.VISIBLE:View.GONE);
         mainViewHolder.dingcun.setVisibility(data.get(i).isCanDingcun() ? View.VISIBLE:View.GONE);
         final int potion = i;
         mainViewHolder.dingcun.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
             showDialog(  data.get(potion),true);
             }
         });
         mainViewHolder.tixian.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showDialog(data.get(potion), false);
             }
         });
         mainViewHolder.chongzhi.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showchongzhiDialog();
             }
         });
    }

    private void showchongzhiDialog(){
        mDialog = new AlertDialog.Builder(context).create();
        mDialog.show();
        Window window = mDialog.getWindow();
        window.setContentView(R.layout.chongzhi);
        Button btn = window.findViewById(R.id.id_btn_fuzhi);
    }

    private void showDialog(Icoin icoin,boolean isdingcun){

        mDialog = new AlertDialog.Builder(context).create();
        if (isdingcun){
            mDialog.setTitle("定存");
        }else {
            mDialog.setTitle("提现");
        }
        mDialog.show();
        Window window = mDialog.getWindow();
        window.setContentView(R.layout.popup);
        TextView textView = window.findViewById(R.id.id_dingcun_textview);
        EditText editText = window.findViewById(R.id.id_dingcun_editview);
        EditText editText_tixian = window.findViewById(R.id.id_tixian_editview);

        Button button = window.findViewById(R.id.id_dingcun_btn);
        textView.setText("可用"+icoin.getName() +"数量:" + icoin.getAvailable());
        if (isdingcun){
            editText.setHint("请输入定存的数量");
            button.setText("定存");

        }else {
            editText.setHint("请输入提现的数量");
            button.setText("提现");
            editText_tixian.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.id_coin_icon)
        ImageView icoin;

        @BindView(R.id.id_coin_name)
        TextView name;

        @BindView(R.id.id_chongzhi)
        TextView chongzhi;

        @BindView(R.id.id_tixian)
        TextView tixian;

        @BindView(R.id.id_dingcun)
        TextView dingcun;

        @BindView(R.id.id_available)
        TextView available;

        @BindView(R.id.id_freeze)
        TextView freeze;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
