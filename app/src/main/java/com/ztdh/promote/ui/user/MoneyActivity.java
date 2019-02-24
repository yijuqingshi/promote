package com.ztdh.promote.ui.user;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


import com.common.base.IBaseActivity;
import com.ztdh.promote.R;
import com.ztdh.promote.model.bean.Money;
import com.ztdh.promote.ui.user.adapter.MoneyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MoneyActivity extends AppCompatActivity implements IBaseActivity {


    @BindView(R.id.id_recyclerview_money)
    RecyclerView id_recyclerview;

    private MoneyAdapter mAdapter;

    @BindView(R.id.id_common_back)
    ImageView back;

    @BindView(R.id.id_common_title)
    TextView title;
    @Override
    public int getLayoutId() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.money;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initViewAndEvent() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        id_recyclerview.setLayoutManager(linearLayoutManager);
        id_recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        mAdapter = new MoneyAdapter(this);
        mAdapter.setData(getData());
        id_recyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("资金明细");
    }

    private List<Money> getData() {
        List<Money> data = new ArrayList<>();
        Money money = new Money();
        money.setType("分红");
        money.setCoinTypeId("BTC");
        money.setCurrency("0.01");
        money.setCreateDate("17:12 06/23/2018");
        data.add(money);

        Money money1 = new Money();
        money1.setType("分红");
        money1.setCoinTypeId("ETH");
        money1.setCurrency("1.0");
        money1.setCreateDate("17:12 09/23/2018");
        data.add(money1);

        Money money2 = new Money();
        money2.setType("分红");
        money2.setCoinTypeId("KBT+");
        money2.setCurrency("2.0");
        money2.setCreateDate("17:12 02/23/2019");
        data.add(money2);
        return  data;
    }
}
