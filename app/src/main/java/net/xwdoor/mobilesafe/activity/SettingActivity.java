package net.xwdoor.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;
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

//                if(sivUpdate.isChecked()){
//                    //若选中，开启自动更新设置
//                    sivUpdate.setDesc("自动更新已开启");
//
//                }else {
//                    //关闭自动更新
//                    sivUpdate.setDesc("自动更新已关闭");
//                }
            }
        });
    }

    @Override
    protected void loadData() {

    }
}