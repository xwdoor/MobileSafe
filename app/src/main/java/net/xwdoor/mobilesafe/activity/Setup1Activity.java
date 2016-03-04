package net.xwdoor.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;

/**
 * Created by XWdoor on 2016/3/4 004 13:31.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class Setup1Activity extends BaseActivity {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, Setup1Activity.class);
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
        setContentView(R.layout.activity_setup1);

        Button btnNextPage = (Button) findViewById(R.id.btn_next_page);

        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    protected void nextPage() {
        Setup2Activity.startAct(this);
        finish();
        // activity切换动画
        overridePendingTransition(R.anim.anim_next_in,
                R.anim.anim_next_out);
    }
}