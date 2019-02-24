package com.ztdh.promote.ui.main;

import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;

import com.common.base.BaseActivity;
import com.common.base.IBaseActivity;
import com.ztdh.promote.R;
import com.ztdh.promote.model.Event.TabSelectedEvent;
import com.ztdh.promote.ui.user.UserFragment;
import com.ztdh.promote.widget.bottombar.ButtomBar;
import com.ztdh.promote.widget.bottombar.CustomTabEntity;
import com.ztdh.promote.widget.bottombar.TabEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity implements IBaseActivity {

    public static final int WALLET = 0;
    public static final int USER = 1;

    @BindView(R.id.id_main_buttombar)
    ButtomBar mBottomBar;



    private SupportFragment[] mFragments = new SupportFragment[2];

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private String[] mTitles;

    private int[] mIconSelectIds = {
            R.mipmap.wallet_sel, R.mipmap.user_sel };

    private int[] mIconUnselectIds = {
            R.mipmap.wallet_default, R.mipmap.user_default};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments[WALLET] = new MainFragment() ;
        mFragments[USER] = new UserFragment();
        loadMultipleRootFragment(R.id.id_main_c, 0,
                mFragments[WALLET],
                mFragments[USER]);
    }

    @Override
    public int getLayoutId() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_main;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initViewAndEvent() {
        mTitles = getResources().getStringArray(R.array.main_sections);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mBottomBar.setTabEntities(mTabEntities);

        mBottomBar.setOnTabSelectedListener(new ButtomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
                TabSelectedEvent event = new TabSelectedEvent(position);
                EventBus.getDefault().post(event);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }


}
