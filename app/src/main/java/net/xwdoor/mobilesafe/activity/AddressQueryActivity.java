package net.xwdoor.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.db.AddressQuery;

/**
 * Created by XWdoor on 2016/3/10 010 9:48.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class AddressQueryActivity extends BaseActivity {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, AddressQueryActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_address_query);

        final EditText etNumber = (EditText) findViewById(R.id.et_number);
        Button btnQuery = (Button) findViewById(R.id.btn_query);
        final TextView tvResult = (TextView) findViewById(R.id.tv_result);

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = etNumber.getText().toString().trim();
                showLog("查询号码",number);
                String address = AddressQuery.getAddress(AddressQueryActivity.this, number);
                tvResult.setText(address);
            }
        });
    }

    @Override
    protected void loadData() {

    }
}