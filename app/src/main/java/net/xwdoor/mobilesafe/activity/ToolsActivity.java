package net.xwdoor.mobilesafe.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.utils.SmsUtils;

import java.io.File;

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
        TextView tvSmsBackup = (TextView) findViewById(R.id.tv_sms_backup);

        tvQueryAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //归属地查询
                AddressQueryActivity.startAct(ToolsActivity.this);
            }
        });

        tvSmsBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsBackup();
            }
        });
    }

    /** 备份短信 */
    private void smsBackup() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/sms.xml";
            final File output = new File(path);

            final ProgressDialog dialog = new ProgressDialog(ToolsActivity.this);
            dialog.setTitle("正在短信备份");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//水平方向展示进度条
            dialog.show();

            //短信备份
            new Thread(){
                @Override
                public void run() {
                    SmsUtils.smsBackup(ToolsActivity.this, output, new SmsUtils.SmsCallback() {
                        @Override
                        public void preSmsBackup(int totalCount) {
                            dialog.setMax(totalCount);
                        }

                        @Override
                        public void onSmsBackup(int progress) {
                            dialog.setProgress(progress);
                        }
                    });

                    dialog.dismiss();
                }
            }.start();
        }else {
            showToast("没有找到sdcard");
        }
    }

    @Override
    protected void loadData() {

    }


}