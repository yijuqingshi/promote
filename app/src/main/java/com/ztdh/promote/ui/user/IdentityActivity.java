package com.ztdh.promote.ui.user;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.IBaseActivity;
import com.ztdh.promote.R;

import butterknife.BindView;

public class IdentityActivity extends AppCompatActivity implements IBaseActivity {


    @BindView(R.id.id_common_back)
    ImageView back;

    @BindView(R.id.id_common_title)
    TextView title;

    @Override
    public int getLayoutId() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.identity;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initViewAndEvent() {
        boolean inlogin = getIntent().getBooleanExtra("inlogin", true);
        if (inlogin){
            title.setText("修改登录密码");
        }else {
            title.setText("修改资金密码");
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
