package net.xwdoor.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;

/**
 * Created by XWdoor on 2016/3/10 010 10:53.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class ToolsActivity extends BaseActivity {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, ToolsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tools);

        TextView tvQueryAddress = (TextView) findViewById(R.id.tv_query_address);

        tvQueryAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressQueryActivity.startAct(ToolsActivity.this);
            }
        });
    }

    @Override
    protected void loadData() {

    }
}