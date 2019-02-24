package com.ztdh.promote.ui.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ztdh.promote.R;
import com.ztdh.promote.model.bean.Money;
import com.ztdh.promote.model.bean.UserAction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoneyAdapter extends RecyclerView.Adapter<MoneyAdapter.MoneyViewHolder>{


    private List<Money> data = new ArrayList<>();

    private Context context;

    public MoneyAdapter(Context context){
        this.context = context;
    }
    public List<Money> getData() {
        return data;
    }


    public void setData(List<Money> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.money_item, viewGroup, false);
        return new MoneyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder userViewHolder, final int i) {
         userViewHolder.name.setText(data.get(i).getCoinTypeId());
         userViewHolder.amount.setText(data.get(i).getCurrency());
         userViewHolder.type.setText(data.get(i).getType());
         userViewHolder.time.setText(data.get(i).getCreateDate());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MoneyViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.id_money_time)
        TextView time;

        @BindView(R.id.id_money_type)
        TextView type;

        @BindView(R.id.id_money_amount)
        TextView amount;

        @BindView(R.id.id_money_name)
        TextView name;


        public MoneyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
