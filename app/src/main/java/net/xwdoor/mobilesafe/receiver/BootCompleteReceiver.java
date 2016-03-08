package net.xwdoor.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.utils.PrefUtils;


public class BootCompleteReceiver extends BroadcastReceiver {
    public BootCompleteReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isProtect = PrefUtils.getBoolean(BaseActivity.PREF_IS_PROTECT, false, context);
        String serialNumber = PrefUtils.getString(BaseActivity.PREF_BIND_SIM, "", context);

        if(isProtect) {
            if (!TextUtils.isEmpty(serialNumber)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String simSerialNumber = tm.getSimSerialNumber()+"xx";

                if (serialNumber.equals(simSerialNumber)) {
                    Log.i(BaseActivity.TAG_LOG, "手机安全");
                } else {
                    Log.i(BaseActivity.TAG_LOG, "SIM卡发生变化，危险");
                    //发送警报短信
                    SmsManager smsManager = SmsManager.getDefault();//短信管理器
                    String phone = PrefUtils.getString(BaseActivity.PREF_PHONE_NUMBER,"",context);
                    smsManager.sendTextMessage(phone,null,"SIM卡发生变化",null,null);
                }
            }
        }
    }
}
