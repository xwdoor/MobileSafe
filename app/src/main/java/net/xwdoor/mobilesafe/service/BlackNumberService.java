package net.xwdoor.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import net.xwdoor.mobilesafe.listener.BlackNumberPhoneListener;
import net.xwdoor.mobilesafe.receiver.BlackNumberSmsReceiver;

/**
 * 黑名单服务
 *
 * Created by XWdoor on 2016/3/17 017 13:41.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BlackNumberService extends Service {

    private TelephonyManager mTM;
    private BlackNumberSmsReceiver mSmsReceiver;
    private BlackNumberPhoneListener mPhoneListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //拦截短信
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(Integer.MAX_VALUE);
        mSmsReceiver = new BlackNumberSmsReceiver();
        // 同等条件下, 动态注册的广播比静态注册的更先获取广播内容
        registerReceiver(mSmsReceiver,filter);

        // 拦截电话
        mTM = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        mPhoneListener = new BlackNumberPhoneListener(this);
        mTM.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //停止监听短信
        unregisterReceiver(mSmsReceiver);
        mSmsReceiver = null;

        // 停止监听来电
        mTM.listen(mPhoneListener,PhoneStateListener.LISTEN_NONE);
    }
}
