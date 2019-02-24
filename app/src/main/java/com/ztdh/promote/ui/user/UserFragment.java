package com.ztdh.promote.ui.user;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.common.base.BaseFragment;
import com.ztdh.promote.R;
import com.ztdh.promote.model.bean.UserAction;
import com.ztdh.promote.ui.main.adapter.MainAdapter;
import com.ztdh.promote.ui.user.adapter.UserAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UserFragment extends BaseFragment {

    @BindView(R.id.id_recyclerview_user)
    RecyclerView id_recyclerview;

    private UserAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.user;
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
        mAdapter = new UserAdapter(getContext());
        mAdapter.setData(getData());
        id_recyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private List<UserAction> getData() {

        List<UserAction> data = new ArrayList<>();

        UserAction action = new UserAction();
        action.setName("用户名:");
        action.setId_user_status("18923715896");
        action.setAction("签到");
        data.add(action);

        UserAction action1 = new UserAction();
        action1.setName("资金明细");
        action1.setId_user_status("");
        action1.setAction("查看");
        Intent intent = new Intent(getContext(),MoneyActivity.class);
        action1.setIntent(intent);
        data.add(action1);

        UserAction action2 = new UserAction();
        action2.setName("提现明细");
        action2.setId_user_status("");
        action2.setAction("查看");
        data.add(action2);


        UserAction action3 = new UserAction();
        action3.setName("推荐关系");
        action3.setId_user_status("");
        action3.setAction("查看");
        Intent intent1 = new Intent(getContext(),RecommendActivity.class);
        action3.setIntent(intent1);
        data.add(action3);

        UserAction action4 = new UserAction();
        action4.setName("登录密码");
        action4.setId_user_status("已设置");
        action4.setAction("修改");
        Intent intent2 = new Intent(getContext(),ModifyPasswordActivity.class);
        intent2.putExtra("inlogin",true);
        action4.setIntent(intent2);
        data.add(action4);


        UserAction action5 = new UserAction();
        action5.setName("资金密码");
        action5.setId_user_status("已设置");

        action5.setAction("修改");
        Intent intent3 = new Intent(getContext(),ModifyPasswordActivity.class);
        intent3.putExtra("inlogin",false);
        action4.setIntent(intent3);
        action5.setIntent(intent3);
        data.add(action5);

        UserAction action6 = new UserAction();
        action6.setName("身份认证");
        action6.setId_user_status("未认证");
        action6.setAction("认证");
        data.add(action6);

        UserAction action7 = new UserAction();
        action7.setName("邀请奖励");
        action7.setId_user_status("邀请码6KWRC1");
        action7.setAction("邀请");
        data.add(action7);
        return data;
    }
}
