package com.ztdh.promote.ui.user;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.common.base.BaseFragment;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.ztdh.promote.R;
import com.ztdh.promote.model.api.ApiHelper;
import com.ztdh.promote.model.bean.Reponse;
import com.ztdh.promote.model.bean.UserAction;
import com.ztdh.promote.ui.main.adapter.MainAdapter;
import com.ztdh.promote.ui.user.adapter.UserAdapter;
import com.ztdh.promote.utils.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

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

    @Override
    public void onResume() {
         getIdentity();
        super.onResume();
    }

    private void getIdentity() {
        OkHttpUtils.post().url(ApiHelper.SERVER_RUL + ApiHelper.GET_AUTHRESULT).addParams("accessKey",(String)SharePreferenceUtils.getData("accessKey",""))
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
                    if (response != null){
                        if (response.getStatus().equals("200")){
                            Map<String,Object> data = (Map<String, Object>) response.getData();
                            String result = (String) data.get("authStatus");
                            UserAction action = mAdapter.getData().get(5);
                            switch (result)
                            {
                                case "0":
                                    action.setId_user_status("审核中");
                                    action.setAction("认证");
//                                    Intent intent4 = new Intent(getContext(),IdentityActivity.class);
                                    action.setIntent(null);
                                    break;
                                case "1":
                                    action.setId_user_status("已通过");
                                    action.setAction("已认证");
                                    action.setIntent(null);
                                    break;
                                case "2":
                                    action.setId_user_status("已驳回");
                                    action.setAction("认证");
                                    Intent intent4 = new Intent(getContext(),IdentityActivity.class);
                                    action.setIntent(intent4);
                                    break;
                                case "3":
                                    action.setId_user_status("未认证");
                                    action.setAction("认证");
                                    Intent intent5 = new Intent(getContext(),IdentityActivity.class);
                                    action.setIntent(intent5);
                                    break;

                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }
            }
        });
    }

    private List<UserAction> getData() {

        List<UserAction> data = new ArrayList<>();

        UserAction action = new UserAction();
        action.setName("用户名:");
        action.setId_user_status((String) SharePreferenceUtils.getData("mobile",""));
        action.setAction("签到");
        data.add(action);

        UserAction action1 = new UserAction();
        action1.setName("资金明细");
        action1.setId_user_status("");
        action1.setAction("查看");
        Intent intent = new Intent(getContext(),MoneyActivity.class);
        action1.setIntent(intent);
        data.add(action1);

//        UserAction action2 = new UserAction();
//        action2.setName("提现明细");
//        action2.setId_user_status("");
//        action2.setAction("查看");
//        data.add(action2);


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
        if (SharePreferenceUtils.getData("highPpassffective","").equals("2")){
            action5.setId_user_status( "已设置");
            action5.setAction("修改");
        }else {
            action5.setId_user_status( "未设置");
            action5.setAction("设置");
        }
        Intent intent3 = new Intent(getContext(),ModifyPasswordActivity.class);
        intent3.putExtra("inlogin",false);
        action5.setIntent(intent3);
        data.add(action5);

        UserAction action6 = new UserAction();
        action6.setName("身份认证");
        String userAuth = (String)SharePreferenceUtils.getData("userAuth","");
        if (userAuth.equals("1")){
            action6.setId_user_status("未认证");
            action6.setAction("认证");
            Intent intent4 = new Intent(getContext(),IdentityActivity.class);
            action6.setIntent(intent4);
        }else {
            action6.setId_user_status("已认证");
            action6.setAction("认证");
        }

        data.add(action6);

        UserAction action7 = new UserAction();
        action7.setName("邀请奖励");
        String userpushurl = (String) SharePreferenceUtils.getData("userpushurl", "");
        String[] split = userpushurl.split("=");
        if (split[1] != null && !split[1].equals("")){
            action7.setId_user_status("邀请码"+ split[1]);
        }else {
            action7.setId_user_status("邀请码5881");
        }

        action7.setAction("邀请");
        data.add(action7);

        UserAction action8 = new UserAction();
        action8.setName("退出登陆");
        action8.setAction("退出");
        data.add(action8);
        return data;
    }
}
