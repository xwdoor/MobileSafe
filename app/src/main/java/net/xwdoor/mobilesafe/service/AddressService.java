package net.xwdoor.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import net.xwdoor.mobilesafe.listener.PhoneListener;
import net.xwdoor.mobilesafe.receiver.CallReceiver;

public class AddressService extends Service {

    private PhoneListener mPhoneListener;
    private CallReceiver mCallReceiver;
    private TelephonyManager mTM;

    public AddressService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTM = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        mPhoneListener = new PhoneListener(getApplicationContext());
        mTM.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);//监听来电

        //监听去电
        mCallReceiver = new CallReceiver(mPhoneListener);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(mCallReceiver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //取消监听来电
        mTM.listen(mPhoneListener,PhoneStateListener.LISTEN_NONE);
        //取消监听去电
        unregisterReceiver(mCallReceiver);
        mCallReceiver = null;
    }
}
