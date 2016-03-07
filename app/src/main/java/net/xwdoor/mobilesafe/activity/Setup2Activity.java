package net.xwdoor.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.utils.PrefUtils;
import net.xwdoor.mobilesafe.view.SettingItemView;

/**
 * Created by XWdoor on 2016/3/4 004 13:32.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class Setup2Activity extends BaseActivity {

    private String mSimSerialNumber;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, Setup2Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initVariables() {
        mSimSerialNumber = PrefUtils.getString(PREF_BIND_SIM, "", this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setup2);

        Button btnNextPage = (Button) findViewById(R.id.btn_next_page);
        Button btnPreviousPage = (Button) findViewById(R.id.btn_previous_page);
        final SettingItemView sivBindSim = (SettingItemView) findViewById(R.id.siv_bind_sim);
        //根据sim卡的绑定情况，显示是否选中
        sivBindSim.setChecked(!TextUtils.isEmpty(mSimSerialNumber));

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

        sivBindSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sivBindSim.setChecked(!sivBindSim.isChecked());
                if (sivBindSim.isChecked()) {
                    //获取手机sim卡序列号
                    TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    mSimSerialNumber = tm.getSimSerialNumber();
                    PrefUtils.putString(PREF_BIND_SIM, mSimSerialNumber, Setup2Activity.this);
                    showLog("simSerialNumber", mSimSerialNumber);
                } else {
                    PrefUtils.remove(PREF_BIND_SIM, Setup2Activity.this);
                    mSimSerialNumber = "";
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    protected void previousPage() {
        Setup1Activity.startAct(this);
        finish();
        // activity切换动画
        overridePendingTransition(R.anim.anim_previous_in,
                R.anim.anim_previous_out);
    }

    protected void nextPage() {
        if (TextUtils.isEmpty(mSimSerialNumber)) {
            showToast("必须绑定sim卡");
        } else {

            Setup3Activity.startAct(this);
            finish();
            // activity切换动画
            overridePendingTransition(R.anim.anim_next_in,
                    R.anim.anim_next_out);
        }
    }
}