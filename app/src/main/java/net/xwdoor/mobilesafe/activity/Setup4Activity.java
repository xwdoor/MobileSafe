package net.xwdoor.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.utils.PrefUtils;

/**
 * Created by XWdoor on 2016/3/4 004 13:32.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class Setup4Activity extends BaseActivity {

    private boolean mIsProtect;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, Setup4Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initVariables() {
        mIsProtect = PrefUtils.getBoolean(PREF_IS_PROTECT, false, this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setup4);

        Button btnNextPage = (Button) findViewById(R.id.btn_next_page);
        Button btnPreviousPage = (Button) findViewById(R.id.btn_previous_page);
        final CheckBox cbCheck = (CheckBox) findViewById(R.id.cb_check);
        cbCheck.setChecked(mIsProtect);
        cbCheck.setText(mIsProtect ? "您已开启防盗保护" : "您没有开启防盗保护");


        btnPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage();
            }
        });
        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsProtect = isChecked;
                PrefUtils.putBoolean(PREF_IS_PROTECT, isChecked, Setup4Activity.this);
                cbCheck.setText(isChecked ? "您已开启防盗保护" : "您没有开启防盗保护");

                if(isChecked) {
                    if (!mDPM.isAdminActive(mDeviceComponentName)) {
                        activeAdmin();
                    }
                }else {
                    mDPM.removeActiveAdmin(mDeviceComponentName);
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }


    protected void previousPage() {
        Setup3Activity.startAct(this);
        finish();
        // activity切换动画
        overridePendingTransition(R.anim.anim_previous_in,
                R.anim.anim_previous_out);
    }

    protected void nextPage() {
        PrefUtils.putBoolean(PREF_CONFIG, true, this);
        AntiTheftActivity.startAct(this);
        finish();
        // activity切换动画
        overridePendingTransition(R.anim.anim_next_in,
                R.anim.anim_next_out);
    }
}