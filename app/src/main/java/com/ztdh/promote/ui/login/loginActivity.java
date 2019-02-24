package com.ztdh.promote.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.common.base.IBaseActivity;
import com.ztdh.promote.R;
import com.ztdh.promote.ui.main.MainActivity;

import butterknife.BindView;

public class loginActivity extends AppCompatActivity implements IBaseActivity {

    @BindView(R.id.id_login_regiter)
    Button regiter;
    @Override
    public int getLayoutId() {
        if (true){
            Intent intent =new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.login;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initViewAndEvent() {
        regiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
