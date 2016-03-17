package net.xwdoor.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.service.AddressService;
import net.xwdoor.mobilesafe.service.BlackNumberService;
import net.xwdoor.mobilesafe.utils.PrefUtils;
import net.xwdoor.mobilesafe.utils.ServiceStatusUtils;
import net.xwdoor.mobilesafe.view.SettingClickView;
import net.xwdoor.mobilesafe.view.SettingItemView;

/**
 * Created by XWdoor on 2016/3/1 001 11:26.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class SettingActivity extends BaseActivity {

    private String[] mItems;
    private SettingClickView scvStyle;
    private int mWhichStyle;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void initVariables() {
        mItems = new String[] { "半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿" };
        mWhichStyle = PrefUtils.getInt(PREF_ADDRESS_STYLE, 0, this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);

        initUpdate();

        initAddress();

        initBlackNumber();
    }

    /** 初始化黑名单设置 */
    private void initBlackNumber() {
        final SettingItemView sivBlackNumber = (SettingItemView) findViewById(R.id.siv_black_number);
        // 根据服务是否运行来更新checkbox
        boolean serviceRunning = ServiceStatusUtils.isServiceRunning(this,
                "net.xwdoor.mobilesafe.service.BlackNumberService");
        sivBlackNumber.setChecked(serviceRunning);

        sivBlackNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sivBlackNumber.setChecked(!sivBlackNumber.isChecked());
                Intent service = new Intent(getApplicationContext(),
                        BlackNumberService.class);
                if(sivBlackNumber.isChecked()){
                    startService(service);
                }else {
                    stopService(service);
                }
            }
        });
    }

    /**
     * 初始化自动更新设置
     */
    private void initUpdate() {
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

    /**
     * 初始化归属地设置
     */
    private void initAddress() {
        final SettingItemView sivAddress = (SettingItemView) findViewById(R.id.siv_address);
        // 根据服务是否运行来更新checkbox
        boolean serviceRunning = ServiceStatusUtils.isServiceRunning(this,
                "net.xwdoor.mobilesafe.service.AddressService");
        sivAddress.setChecked(serviceRunning);

        sivAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sivAddress.setChecked(!sivAddress.isChecked());
                Intent service = new Intent(SettingActivity.this, AddressService.class);
                if (sivAddress.isChecked()) {
                    startService(service);//开启电话归属地显示服务
                } else {
                    stopService(service);//关闭电话归属地服务
                }
            }
        });

        initAddressStyle();
        initAddressLocation();
    }

    /** 设置归属地提示框的位置 */
    private void initAddressLocation() {
        SettingClickView scvLocation = (SettingClickView) findViewById(R.id.scv_address_location);
        scvLocation.setTitle("归属地提示框位置");
        scvLocation.setDesc("设置归属地提示框位置");
        scvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DragViewActivity.startAct(SettingActivity.this);
            }
        });
    }

    /** 初始化归属地提示框风格 */
    private void initAddressStyle() {
        scvStyle = (SettingClickView) findViewById(R.id.scv_address_style);
        scvStyle.setTitle("归属地提示框风格");
        scvStyle.setDesc(mItems[mWhichStyle]);
        scvStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseDialog();
            }
        });
    }

    private void showChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("归属地提示框风格");
        builder.setIcon(R.drawable.ic_launcher);

        builder.setSingleChoiceItems(mItems, mWhichStyle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //保存风格
                PrefUtils.putInt(PREF_ADDRESS_STYLE,which,SettingActivity.this);
                scvStyle.setDesc(mItems[which]);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    protected void loadData() {

    }
}