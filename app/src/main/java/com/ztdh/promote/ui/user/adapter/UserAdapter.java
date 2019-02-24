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

import com.ztdh.promote.R;
import com.ztdh.promote.model.bean.Icoin;
import com.ztdh.promote.model.bean.UserAction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{


    private List<UserAction> data = new ArrayList<>();

    private Context context;

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
                    Toast.makeText(context,"签到成功",Toast.LENGTH_SHORT).show();
                }
                if (intent != null){
                    context.startActivity(intent);
                }
            }
        });
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
