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
import com.ztdh.promote.model.bean.Recommond;
import com.ztdh.promote.ui.user.adapter.RecommendAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
            Recommond recommond = new Recommond();
            recommond.setName("未**");
            recommond.setMoblie("13045856746");
            data.add(recommond);

        Recommond recommond1 = new Recommond();
        recommond1.setName("许**");
        recommond1.setMoblie("13045856426");
        data.add(recommond1);

        Recommond recommond2 = new Recommond();
        recommond2.setName("许**");
        recommond2.setMoblie("13045856426");
        data.add(recommond2);

        return  data;
    }
}
