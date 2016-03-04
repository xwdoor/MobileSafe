package net.xwdoor.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;

/**
 * Created by XWdoor on 2016/3/4 004 13:32.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class Setup4Activity extends BaseActivity {

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

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setup4);

        Button btnNextPage = (Button) findViewById(R.id.btn_next_page);
        Button btnPreviousPage = (Button) findViewById(R.id.btn_previous_page);

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
        AntiTheftActivity.startAct(this);
        finish();
        // activity切换动画
        overridePendingTransition(R.anim.anim_next_in,
                R.anim.anim_next_out);
    }
}