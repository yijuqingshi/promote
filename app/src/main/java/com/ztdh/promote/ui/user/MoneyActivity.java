package com.ztdh.promote.ui.user;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


import com.common.base.IBaseActivity;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.ztdh.promote.R;
import com.ztdh.promote.model.api.ApiHelper;
import com.ztdh.promote.model.bean.Icoin;
import com.ztdh.promote.model.bean.Money;
import com.ztdh.promote.model.bean.Reponse;
import com.ztdh.promote.model.bean.TypeUtils;
import com.ztdh.promote.ui.BaseActivity;
import com.ztdh.promote.ui.user.adapter.MoneyAdapter;
import com.ztdh.promote.utils.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class MoneyActivity extends BaseActivity implements IBaseActivity {


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
        mDialog = new AlertDialog.Builder(this).create();
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
        OkHttpUtils.post().url(ApiHelper.SERVER_RUL + ApiHelper.GET_MONEY).addParams("accessKey", (String) SharePreferenceUtils.getData("accessKey",""))
                .addParams("offset","0").addParams("limit","10000").addParams("sort","create_date").addParams("order","DESC").build().execute(new Callback<Reponse<Object>>() {
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
                    List<Map<String,Object>> data = (List<Map<String, Object>>) response.getData();
                    List<Money> monies = new ArrayList<>();
                    for (Map<String, Object> datum : data) {
                        Money money = new Money();
                        String coinTypeId = (String) datum.get("coinTypeId");
                        money.setCoinTypeId(coinTypeId);
                        double  currency = (double) datum.get("currency");
                        money.setCurrency(currency);
                        double  type = (double) datum.get("type");
                        int type1 = (int) type;
                        money.setType(TypeUtils.getType(type1+""));
                        String remark = (String) datum.get("remark");
                        money.setRemark(remark);
                        String createDate = (String) datum.get("createDate");
                        money.setCreateDate(createDate);
                        monies.add(money);
                    }
                    mAdapter.setData(monies);
                    mAdapter.notifyDataSetChanged();
            }
        }
        });
        return  data;
    }
}
