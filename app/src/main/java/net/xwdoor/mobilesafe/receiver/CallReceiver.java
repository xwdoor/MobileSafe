package net.xwdoor.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.xwdoor.mobilesafe.db.AddressQuery;
import net.xwdoor.mobilesafe.listener.PhoneListener;

/**
 * 打电话监听：去电监听
 *
 * Created by XWdoor on 2016/3/11 011 13:44.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class CallReceiver extends BroadcastReceiver {
    private final PhoneListener mPhoneListener;

    public CallReceiver(PhoneListener phoneListener) {
        this.mPhoneListener = phoneListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String number = getResultData();//获取电话号码
        String address = AddressQuery.getAddress(context, number);

        //ToastUtils.showToast(context, "去电地址-->" + address + "，去电号码-->" + number);
        mPhoneListener.showAddressBox(address);
    }
}
