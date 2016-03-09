package net.xwdoor.mobilesafe.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.util.Log;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.service.LocationService;

public class SmsReceiver extends BroadcastReceiver {

    public SmsReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");

        for (Object pdu : pdus) {//短信超过140字节，会分为多条短信发送
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);

            String address = sms.getOriginatingAddress();
            String msg = sms.getMessageBody();
            Log.i(BaseActivity.TAG_LOG, "address-->" + address + ",msg-->" + msg);

            if ("#*alarm*#".equals(msg)) {
                //报警命令
                //播放报警音乐
                MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
                player.setVolume(1, 1);//音量最大
                player.setLooping(true);//循环播放
                player.start();//开始播放
                //拦截短信
                abortBroadcast();
            } else if ("#*location*#".equals(msg)) {
                //gps定位命令
                Intent service = new Intent(context, LocationService.class);
                context.startService(service);
                //拦截短信
                abortBroadcast();
            } else if ("#*wipedata*#".equals(msg)) {
                //删除数据
                wipeData(context);
                abortBroadcast();
            } else if ("#*lockscreen*#".equals(msg)) {
                //远程锁屏
                lockNow(context);
                abortBroadcast();
            }
        }
    }


    /**
     * 立即锁屏
     */
    public void lockNow(Context context) {
        // 设备策略管理器
        DevicePolicyManager mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        // 初始化管理员组件
        ComponentName mDeviceComponentName = new ComponentName(context, AdminReceiver.class);

        if (mDPM.isAdminActive(mDeviceComponentName)) {
            mDPM.lockNow();// 立即锁屏
//            mDPM.resetPassword("123456", 0);// 重新设置密码, 传""取消密码
        }
    }

    /**
     * 清除数据
     */
    public void wipeData(Context context) {
        // 设备策略管理器
        DevicePolicyManager mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        // 初始化管理员组件
        ComponentName mDeviceComponentName = new ComponentName(context, AdminReceiver.class);

        if (mDPM.isAdminActive(mDeviceComponentName)) {
            mDPM.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);// 清除内部数据和sdcard
        }
    }
}
