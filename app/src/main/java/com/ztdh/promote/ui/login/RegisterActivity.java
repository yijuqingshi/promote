package com.ztdh.promote.ui.login;

import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.common.base.IBaseActivity;
import com.ztdh.promote.R;

public class RegisterActivity extends AppCompatActivity implements IBaseActivity {

    @Override
    public int getLayoutId() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.register;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initViewAndEvent() {

    }
}
