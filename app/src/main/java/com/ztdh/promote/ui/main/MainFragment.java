package com.ztdh.promote.ui.main;



import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.common.base.BaseFragment;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.ztdh.promote.R;
import com.ztdh.promote.model.api.ApiHelper;
import com.ztdh.promote.model.bean.Icoin;
import com.ztdh.promote.model.bean.Reponse;
import com.ztdh.promote.ui.login.RegisterActivity;
import com.ztdh.promote.ui.main.adapter.MainAdapter;
import com.ztdh.promote.utils.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;


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
        id_recyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }



    @Override
    public void onResume() {
        mAdapter.setData(getData());
        super.onResume();
    }

    private List<Icoin> getData() {
     List<Icoin> data = new ArrayList<>();

        OkHttpUtils.post().url(ApiHelper.SERVER_RUL + ApiHelper.GET_ICOIN).addParams("accessKey", (String) SharePreferenceUtils.getData("accessKey","")).build().execute(new Callback<Reponse<Object>>() {
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
                     List<Icoin> icoins = new ArrayList<>();
                    for (Map<String, Object> datum : data) {
                        Icoin icoin = new Icoin();
                        String coinTypeId = (String) datum.get("coinTypeId");
                        icoin.setId(coinTypeId);
                        double  normalCurrency = (double) datum.get("normalCurrency");
                        icoin.setAvailable(normalCurrency);
                        String  coinName = (String) datum.get("coinName");
                        icoin.setName(coinName);
                        double  lockCurrency = (double) datum.get("lockCurrency");
                        icoin.setFreeze(lockCurrency);
                        icoin.setCanTixian(true);
                        icoin.setCanChongzhi(true);
                        icoin.setCanDingcun(true);
                        icoins.add(icoin);

                    }
                    mAdapter.setData(icoins);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });


//     Icoin icoin = new Icoin();
//     icoin.setCanChongzhi(true);
//     icoin.setCanDingcun(true);
//     icoin.setCanTixian(true);
//     icoin.setName("比特币");
//     icoin.setAvailable(10);
//     icoin.setFreeze(10);
//     data.add(icoin);
//
//        Icoin icoin1 = new Icoin();
//        icoin1.setCanChongzhi(true);
//        icoin1.setCanDingcun(true);
//        icoin1.setCanTixian(true);
//        icoin1.setName("KBT");
//        icoin1.setAvailable(1000000);
//        icoin1.setFreeze(100000);
//        data.add(icoin1);
//
//        Icoin icoin2 = new Icoin();
//        icoin2.setCanChongzhi(true);
//        icoin2.setCanDingcun(true);
//        icoin2.setCanTixian(true);
//        icoin2.setName("KBT+");
//        icoin2.setAvailable(1000000000);
//        icoin2.setFreeze(100000);
//        data.add(icoin2);
     return data;
    }


}
