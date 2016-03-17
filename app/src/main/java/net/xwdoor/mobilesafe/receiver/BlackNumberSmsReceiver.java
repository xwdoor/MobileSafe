package net.xwdoor.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import net.xwdoor.mobilesafe.db.BlackNumberDao;

/**
 * 监听短信广播：黑名单监听
 *
 * Created by XWdoor on 2016/3/17 017 14:03.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BlackNumberSmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        for (Object pdu : pdus) {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) pdu);
            String address = message.getOriginatingAddress();
            String msg = message.getMessageBody();

            BlackNumberDao mNumberDao = BlackNumberDao.getInstance(context);
            boolean exist = mNumberDao.find(address);
            if (exist) {// 在黑名单中
                // 1, 2, 3
                int mode = mNumberDao.findMode(address);// 获取拦截模式
                if (mode > 1) {// 2, 3才拦截
                    abortBroadcast();
                }
            }
            mNumberDao = null;
        }
    }
}
