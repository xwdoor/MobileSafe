package net.xwdoor.mobilesafe.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;


/**
 * Created by XWdoor on 2016/3/11 011 14:50.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class ServiceStatusUtils {

    /**
     * 判断服务是否正在运行
     * @param context
     * @param serviceName
     * @return
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        //获取活动管理器
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获取当前正在运行的服务，最多返回100条记录
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);
        for(ActivityManager.RunningServiceInfo service : runningServices){
            // 获取服务名称，并判断
            if(serviceName.equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}
