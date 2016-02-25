package net.xwdoor.mobilesafe.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.entity.AppInfo;
import net.xwdoor.mobilesafe.global.Config;
import net.xwdoor.mobilesafe.net.HttpRequest;
import net.xwdoor.mobilesafe.net.RequestCallback;

/**
 * 闪屏界面
 * Created by XWdoor on 2016/2/24 024 12:03.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class SplashActivity extends BaseActivity {

    private TextView mTvVersion;
    private AppInfo mLocalAppInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initVariables() {
        mLocalAppInfo = getLocalAppInfo();
        Log.i(TAG_LOG, "当前版本信息--->" + mLocalAppInfo.toJson());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);

        mTvVersion = (TextView) findViewById(R.id.tv_version);
        mTvVersion.setText("版本号：" + mLocalAppInfo.getVersionName());
    }

    @Override
     protected void loadData() {
        getAppInfoFromServer();
    }

    /** 从服务器中获取版本信息 */
    private void getAppInfoFromServer() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.requestGet(Config.VERSION_URL, new RequestCallback() {
            @Override
            public void onSuccess(String content) {
                Gson gson = new Gson();
                //将Json字符串解析为AppInfo对象
                AppInfo remoteAppInfo = gson.fromJson(content, AppInfo.class);
                Log.i(TAG_LOG, "服务器版本信息--->" + remoteAppInfo);

                if (remoteAppInfo.getVersionCode() > mLocalAppInfo.getVersionCode()) {
                    //有更新，弹出升级对话框
                    showUpdateDialog();
                } else {
                    //无更新，进入主界面
                }
            }

            @Override
            public void onFaile(String errorMsg) {
                Toast.makeText(SplashActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                Log.e(TAG_LOG, "获取版本信息异常--->" + errorMsg);
            }
        });
    }

    private void showUpdateDialog() {
        
    }

    /** 获取当前App版本信息 */
    private AppInfo getLocalAppInfo() {
        AppInfo appInfo = new AppInfo();
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            appInfo.setVersionCode(packageInfo.versionCode);
            appInfo.setVersionName(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appInfo;
    }
}