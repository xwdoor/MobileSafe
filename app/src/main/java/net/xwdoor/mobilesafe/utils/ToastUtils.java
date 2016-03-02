package net.xwdoor.mobilesafe.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by XWdoor on 2016/3/2 002 14:51.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class ToastUtils {

    public static void showToast(Context context, CharSequence text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
