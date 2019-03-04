package com.ztdh.promote.ui.main;

import android.os.Bundle;
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
import com.ztdh.promote.model.bean.DingCun;
import com.ztdh.promote.model.bean.Icoin;
import com.ztdh.promote.model.bean.Reponse;
import com.ztdh.promote.ui.main.adapter.DingCunAdapter;
import com.ztdh.promote.utils.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class DingCunActivity extends AppCompatActivity implements IBaseActivity {


    @BindView(R.id.id_dingcun_recyclerview)
    RecyclerView idDingcunRecyclerview;
    @BindView(R.id.id_common_back)
    ImageView idCommonBack;
    @BindView(R.id.id_common_title)
    TextView idCommonTitle;

    private DingCunAdapter mAdapter;

    private Icoin icoin;

    @Override
    public int getLayoutId() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.dingcun;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initViewAndEvent() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        idDingcunRecyclerview.setLayoutManager(linearLayoutManager);
        idDingcunRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        icoin = (Icoin) getIntent().getSerializableExtra("Icon");
        mAdapter = new DingCunAdapter(this, icoin);
        mAdapter.setData(getData());
        idDingcunRecyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        idCommonTitle.setText("定存列表");
        idCommonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private List<DingCun> getData() {
        final List<DingCun> data = new ArrayList<>();
        OkHttpUtils.post().url(ApiHelper.SERVER_RUL + ApiHelper.GET_COIN_PROJECT).addParams("accessKey", (String) SharePreferenceUtils.getData("accessKey", "")).addParams("coinName", icoin.getName())
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
                    List<Map<String, Object>> data1 = (List<Map<String, Object>>) response.getData();
                    List<DingCun> data = new ArrayList<>();
                    for (Map<String, Object> stringObjectMap : data1) {
                        String projectName = (String) stringObjectMap.get("projectName");
                        String tid = (String) stringObjectMap.get("tid");
                        String currencyType = (String) stringObjectMap.get("currencyType");
                        String releaseStatus = (String) stringObjectMap.get("releaseStatus");
                        String startDate = (String) stringObjectMap.get("startDate");
                        String endDate = (String) stringObjectMap.get("endDate");
                        String createDate = (String) stringObjectMap.get("createDate");
                        String updateDate = (String) stringObjectMap.get("updateDate");
                        String delFlag = (String) stringObjectMap.get("delFlag");
                        String isEnable = (String) stringObjectMap.get("isEnable");
                        String startReleaseDate = (String) stringObjectMap.get("startReleaseDate");
                        String projectStatus = (String) stringObjectMap.get("projectStatus");

                        double releaseDay = (double) stringObjectMap.get("releaseDay");
                        double smallCurrency = (double) stringObjectMap.get("smallCurrency");
                        double sellCount = (double) stringObjectMap.get("sellCount");
                        double currencyTotal = (double) stringObjectMap.get("currencyTotal");
                        double incomeRate = (double) stringObjectMap.get("incomeRate");
                        DingCun dingCun = new DingCun();
                        dingCun.setCurrencyType(currencyType);
                        dingCun.setCreateDate(createDate);
                        dingCun.setDelFlag(delFlag);
                        dingCun.setEndDate(endDate);
                        dingCun.setTid(tid);
                        dingCun.setIsEnable(isEnable);
                        dingCun.setReleaseStatus(releaseStatus);
                        dingCun.setProjectName(projectName);
                        dingCun.setStartDate(startDate);
                        dingCun.setUpdateDate(updateDate);
                        dingCun.setStartReleaseDate(startReleaseDate);
                        dingCun.setCurrencyTotal(currencyTotal);
                        dingCun.setProjectStatus(projectStatus);
                        dingCun.setReleaseDay(releaseDay);
                        dingCun.setSmallCurrency(smallCurrency);
                        dingCun.setSellCount(sellCount);
                        dingCun.setIncomeRate(incomeRate);
                        data.add(dingCun);
                    }
                    mAdapter.setData(data);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        return data;
    }

}
