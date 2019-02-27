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
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.ztdh.promote.R;
import com.ztdh.promote.model.api.ApiHelper;
import com.ztdh.promote.model.bean.Recommond;
import com.ztdh.promote.model.bean.Reponse;
import com.ztdh.promote.ui.user.adapter.RecommendAdapter;
import com.ztdh.promote.utils.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class RecommendActivity extends AppCompatActivity implements IBaseActivity {

    @BindView(R.id.id_common_back)
    ImageView back;

    @BindView(R.id.id_common_title)
    TextView title;

    @BindView(R.id.id_recommend_shang_name)
    TextView shang_name;

    @BindView(R.id.id_recommend_shang_moblie)
    TextView shang_moblie;

    @BindView(R.id.id_recommend_recyclerview)
    RecyclerView mRecyclerView;

    private RecommendAdapter mAdapter;

    @Override
    public int getLayoutId() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.recommend;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initViewAndEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("推荐关系");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAdapter = new RecommendAdapter(this);
        mAdapter.setData(getData());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private List<Recommond> getData() {
            List<Recommond> data = new ArrayList<>();

            OkHttpUtils.post().url(ApiHelper.SERVER_RUL + ApiHelper.GET_PUSHUSER).addParams("accessKey", (String) SharePreferenceUtils.getData("accessKey",""))
                .build().execute(new Callback<Reponse<Object>>() {
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
                    List<Recommond>  recommonds = new ArrayList<>();
                    Map<String,Object> data = (Map<String, Object>) response.getData();
                    List<Map<String,Object>>  subUser = (List<Map<String, Object>>) data.get("subUser");
                    for (Map<String, Object> stringObjectMap : subUser) {
                        String mobile = (String) stringObjectMap.get("mobile");
                        String userName = (String) stringObjectMap.get("userName");
                        Recommond recommond2 = new Recommond();
                        recommond2.setMoblie(mobile);
                        recommond2.setName(userName);
                        recommonds.add(recommond2);
                    }
                    mAdapter.setData(recommonds);
                    mAdapter.notifyDataSetChanged();
                    Map<String,Object> superUser = (Map<String, Object>) data.get("superUser");
                    String mobile = (String) superUser.get("mobile");
                    String name = (String) superUser.get("userName");
                    shang_moblie.setText(mobile);
                    shang_name.setText(name);
                }
            }
        });

        return  data;
    }
}
