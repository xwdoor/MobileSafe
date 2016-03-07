package net.xwdoor.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        boolean isConfig = PrefUtils.getBoolean(PREF_CONFIG, false, this);
        if (isConfig) {
            setContentView(R.layout.activity_anti_theft);
            TextView tvPhone = (TextView) findViewById(R.id.tv_phone);
            TextView tvReset = (TextView) findViewById(R.id.tv_reset);
            ImageView ivProtect = (ImageView) findViewById(R.id.iv_protect);

            tvReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //重新进入设置向导
                    Setup1Activity.startAct(AntiTheftActivity.this);
                }
            });
            tvPhone.setText(PrefUtils.getString(PREF_PHONE_NUMBER, "", this));
            ivProtect.setImageResource(PrefUtils.getBoolean(PREF_IS_PROTECT, false, this) ? R.drawable.lock : R.drawable.unlock);
        } else {
            //没有设置过，则进入设置向导
            Setup1Activity.startAct(this);
            finish();
        }
    }

    @Override
    protected void loadData() {

    }
}