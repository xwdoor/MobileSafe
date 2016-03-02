package net.xwdoor.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.utils.PrefUtils;
import net.xwdoor.mobilesafe.view.SettingItemView;

/**
 * Created by XWdoor on 2016/3/1 001 11:26.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class SettingActivity extends BaseActivity {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        final SettingItemView sivUpdate = (SettingItemView) findViewById(R.id.siv_update);
        sivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sivUpdate.setChecked(!sivUpdate.isChecked());
                //保存相应设置
                PrefUtils.putBoolean(PREF_AUTO_UPDATE, sivUpdate.isChecked(), SettingActivity.this);
            }
        });

        boolean autoUpdate = PrefUtils.getBoolean(PREF_AUTO_UPDATE, true, SettingActivity.this);
        sivUpdate.setChecked(autoUpdate);
    }

    @Override
    protected void loadData() {

    }
}