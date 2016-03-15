package net.xwdoor.mobilesafe.base;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.xwdoor.mobilesafe.receiver.AdminReceiver;
import net.xwdoor.mobilesafe.utils.ToastUtils;

/**
 * Created by XWdoor on 2016/2/24.
 * 博客：http://blog.csdn.net/xwdoor
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG_LOG = "123123";
    public static final String PREF_AUTO_UPDATE = "auto_update";
    public static final String PREF_PASSWORD = "password";
    public static final String PREF_CONFIG = "config";
    public static final String PREF_BIND_SIM = "bind_sim";
    public static final String PREF_PHONE_NUMBER = "phone_number";
    public static final String PREF_IS_PROTECT = "is_protect";
    public static final String PREF_ADDRESS_STYLE = "address_style";
    public static final String PREF_LAST_X = "lastX";
    public static final String PREF_LAST_Y = "lastY";

    protected DevicePolicyManager mDPM;
    protected ComponentName mDeviceComponentName;

    /** 显示提示框 */
    public void showToast(CharSequence text){
        ToastUtils.showToast(this,text);
    }

    /** 打印日志 */
    public void showLog(String title, String msg){
        Log.i(TAG_LOG, title + "-->" + msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设备策略管理器
        mDPM = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        // 初始化管理员组件
        mDeviceComponentName = new ComponentName(this, AdminReceiver.class);

        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    /** 初始化变量，包括启动Activity传过来的变量和Activity内的变量 */
    public abstract void initVariables();

    /** 初始化视图，加载layout布局文件，初始化控件，为控件挂上事件 */
    protected abstract void initViews(Bundle savedInstanceState);

    /** 加载数据，包括网络数据，缓存数据，用户数据，调用服务器接口获取的数据 */
    protected abstract void loadData();


    /**
     * 激活超级管理员权限 设置->安全->设备管理器     *
     */
    public void activeAdmin() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceComponentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "手机安全卫士-手机防盗，您的好帮手，值得拥有");
        startActivity(intent);
        showToast("请先激活手机防盗的管理员权限");
    }
}
