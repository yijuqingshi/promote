package com.ztdh.promote.ui.user.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ztdh.promote.R;
import com.ztdh.promote.model.bean.Money;
import com.ztdh.promote.model.bean.Recommond;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder>{


    private List<Recommond> data = new ArrayList<>();

    private Context context;

    public RecommendAdapter(Context context){
        this.context = context;
    }
    public List<Recommond> getData() {
        return data;
    }


    public void setData(List<Recommond> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend, viewGroup, false);
        return new RecommendViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendViewHolder userViewHolder, final int i) {
            userViewHolder.name.setText(data.get(i).getName());
            userViewHolder.moblie.setText(data.get(i).getMoblie());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.id_item_recommend_name)
        TextView name;

        @BindView(R.id.id_item_recommend_moblie)
        TextView moblie;


        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
