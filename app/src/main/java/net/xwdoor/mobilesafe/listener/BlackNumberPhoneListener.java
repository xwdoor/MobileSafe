package net.xwdoor.mobilesafe.listener;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

import net.xwdoor.mobilesafe.db.BlackNumberDao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by XWdoor on 2016/3/17 017 14:20.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BlackNumberPhoneListener extends PhoneStateListener {

    private final BlackNumberDao mNumberDao;
    private final Context mContext;
    private BlackNumberLogObserver mObserver;

    public BlackNumberPhoneListener(Context context) {
        mContext = context;
        mNumberDao = BlackNumberDao.getInstance(context);
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        switch (state){
            case TelephonyManager.CALL_STATE_RINGING://电话铃响
                boolean exist = mNumberDao.find(incomingNumber);
                if(exist){//黑名单中有该号码
                    int mode = mNumberDao.findMode(incomingNumber);
                    if (mode == 1 || mode == 3) {
                        //拦截该号码：挂断电话
                        endCall();

                        // 系统通话记录是异步添加的,需要一定时间,如果在通话记录添加完成之前直接删除记录, 不会有作用
                        // deleteCallLog(incomingNumber);
                        // 注册内容观察者,观察通话记录表的变化
                        mObserver = new BlackNumberLogObserver(new Handler(), incomingNumber);
                        mContext.getContentResolver().registerContentObserver(
                                Uri.parse("content://call_log/calls"), true, mObserver);
                    }
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK://电话摘机
                break;
            case TelephonyManager.CALL_STATE_IDLE://电话空闲
                break;
        }
    }

    /** 挂断电话 需要权限:android.permission.CALL_PHONE */
    private void endCall() {
        try {
            // TelephonyManager.endCall();
            // IBinder b = ServiceManager.getService(ALARM_SERVICE);
            // IAlarmManager service = IAlarmManager.Stub.asInterface(b);
            // ServiceManager 被隐藏了,需要通过反射来调用
            // IBinder b = ServiceManager.getService(TELEPHONY_SERVICE);
            Class<?> aClass = Class.forName("android.os.ServiceManager");//通过反射找到ServiceManager
            Method method = aClass.getMethod("getService", String.class);//找到ServiceManager的静态方法getService
            IBinder b = (IBinder) method.invoke(null, Context.TELEPHONY_SERVICE);//调用getService()方法，得到IBinder对象
            ITelephony service = ITelephony.Stub.asInterface(b);//得到TelephonyManager接口
            service.endCall();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    class BlackNumberLogObserver extends ContentObserver{

        private final String number;

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         * @param incomingNumber 电话号码
         */
        public BlackNumberLogObserver(Handler handler, String incomingNumber) {
            super(handler);
            number = incomingNumber;
        }

        // 表的数据发生变化会回调此方法
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            deleteCallLog(number);
            // 注销观察者
            mContext.getContentResolver().unregisterContentObserver(mObserver);
        }
    }

    /**
     * 删除通话记录
     *
     * 需要权限：
     * <uses-permission android:name="android.permission.READ_CALL_LOG" />
     * <uses-permission android:name="android.permission.WRITE_CALL_LOG" />
     *
     * @param number 电话
     */
    private void deleteCallLog(String number) {
        // 和联系人是一个数据库
        mContext.getContentResolver().delete(Uri.parse("content://call_log/calls"), "number = ?", new String[]{number});
    }


}
