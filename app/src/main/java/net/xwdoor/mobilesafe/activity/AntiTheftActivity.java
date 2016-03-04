package net.xwdoor.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.utils.PrefUtils;

/**
 * Created by XWdoor on 2016/3/4 004 13:13.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class AntiTheftActivity extends BaseActivity {


    public static void startAct(Context context) {
        Intent intent = new Intent(context, AntiTheftActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        boolean isConfig =PrefUtils.getBoolean(PREF_CONFIG,false,this);
        if(isConfig) {
            setContentView(R.layout.activity_anti_theft);
        }else {
            //没有设置过，则进入设置向导
            Setup1Activity.startAct(this);
            finish();
        }
    }

    @Override
    protected void loadData() {

    }
}