package com.ztdh.promote.ui.main;



import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.common.base.BaseFragment;
import com.ztdh.promote.R;
import com.ztdh.promote.model.bean.Icoin;
import com.ztdh.promote.ui.main.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainFragment extends BaseFragment {




    @BindView(R.id.id_recyclerview_main)
    RecyclerView id_recyclerview;

    private MainAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return  R.layout.coin;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initViewAndEvent() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        id_recyclerview.setLayoutManager(linearLayoutManager);
        id_recyclerview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        mAdapter = new MainAdapter(getContext());
        mAdapter.setData(getData());
        id_recyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    private List<Icoin> getData() {
     List<Icoin> data = new ArrayList<>();
     Icoin icoin = new Icoin();
     icoin.setCanChongzhi(true);
     icoin.setCanDingcun(true);
     icoin.setCanTixian(true);
     icoin.setName("比特币");
     icoin.setAvailable(10);
     icoin.setFreeze(10);
     data.add(icoin);

        Icoin icoin1 = new Icoin();
        icoin1.setCanChongzhi(true);
        icoin1.setCanDingcun(true);
        icoin1.setCanTixian(true);
        icoin1.setName("KBT");
        icoin1.setAvailable(1000000);
        icoin1.setFreeze(100000);
        data.add(icoin1);

        Icoin icoin2 = new Icoin();
        icoin2.setCanChongzhi(true);
        icoin2.setCanDingcun(true);
        icoin2.setCanTixian(true);
        icoin2.setName("KBT+");
        icoin2.setAvailable(1000000000);
        icoin2.setFreeze(100000);
        data.add(icoin2);
     return data;
    }


}
