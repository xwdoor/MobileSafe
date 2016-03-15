package net.xwdoor.mobilesafe.listener;

import android.content.Context;
import android.graphics.PixelFormat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.db.AddressQuery;
import net.xwdoor.mobilesafe.utils.PrefUtils;

/**
 * 电话状态监听
 * <p/>
 * Created by XWdoor on 2016/3/11 011 13:50.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class PhoneListener extends PhoneStateListener {

    private Context mContext;
    private View mView;
    private WindowManager mWM;
    private int startX;
    private int startY;
    private int mScreenWidth;
    private int mScreenHeight;

    public PhoneListener(Context context) {
        mContext = context;
    }

    // 电话状态发生变化
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING://电话铃响
                String address = AddressQuery.getAddress(mContext, incomingNumber);
                showAddressBox(address);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK://电话摘机
                break;
            case TelephonyManager.CALL_STATE_IDLE://电话空闲
                closeAddressBox();
                break;
        }
    }

    /**
     * 显示电话归属地提示框
     * 需要权限:android.permission.SYSTEM_ALERT_WINDOW
     *
     * @param address
     */
    public void showAddressBox(String address) {
        Log.i(BaseActivity.TAG_LOG, "电话归属地-->" + address);

        //窗口管理器, 系统最顶级的界面布局, 所有东西都展示在窗口上,activity,状态栏
        mWM = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = mWM.getDefaultDisplay().getWidth();// 屏幕宽度
        mScreenHeight = mWM.getDefaultDisplay().getHeight();// 屏幕高度

        // 初始化布局参数
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                // | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;// 提高类型级别,保证可以触摸移动
        params.gravity = Gravity.START + Gravity.TOP;// 将重心设置在左上方位置,和屏幕坐标体系重合,方便修改窗口的位置

        //获取归属地提示框位置
        startX = PrefUtils.getInt(BaseActivity.PREF_LAST_X, 0, mContext);
        startY = PrefUtils.getInt(BaseActivity.PREF_LAST_Y, 0, mContext);
        params.x = startX;
        params.y = startY;

        // 初始化UI布局
        mView = View.inflate(mContext, R.layout.dialog_address_box, null);
        TextView tvAddress = (TextView) mView.findViewById(R.id.tv_address);
        tvAddress.setText(address);
        // 根据用户设置,更新UI布局背景
        int which = PrefUtils.getInt(BaseActivity.PREF_ADDRESS_STYLE, 0, mContext);
        //背景图片id数组，用于风格设置
        int[] bgs = new int[]{R.drawable.call_locate_white,
                R.drawable.call_locate_orange, R.drawable.call_locate_blue,
                R.drawable.call_locate_gray, R.drawable.call_locate_green};
        tvAddress.setBackgroundResource(bgs[which]);

        tvAddress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 记录起点坐标
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 获取移动后坐标
                        int endX = (int) event.getRawX();
                        int endY = (int) event.getRawY();

                        // 计算偏移量
                        int dx = endX - startX;
                        int dy = endY - startY;

                        // 根据偏移量,更新位置
                        // 根据偏移量,更新位置
                        params.x = params.x + dx;
                        params.y = params.y + dy;


                        // 防止坐标越界
                        if (params.x < 0) {
                            params.x = 0;
                        }

                        // 防止坐标越界
                        if (params.x + mView.getWidth() > mScreenWidth) {
                            params.x = mScreenWidth - mView.getWidth();
                        }

                        // 防止坐标越界
                        if (params.y < 0) {
                            params.y = 0;
                        }

                        // 防止坐标越界
                        if (params.y + mView.getHeight() > mScreenHeight - 20) {
                            params.y = mScreenHeight - 20 - mView.getHeight();
                        }
                        //减去状态栏，大概20像素
                        // 根据布局位置,更新提示框位置
                         mWM.updateViewLayout(mView,params);

                            startX = (int) event.getRawX();
                            startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        //记录最终坐标
                        PrefUtils.putInt(BaseActivity.PREF_LAST_X, params.x, mContext);
                        PrefUtils.putInt(BaseActivity.PREF_LAST_Y, params.y, mContext);
                        break;
                }
                return true;
            }
        });

        // 给窗口添加布局
        mWM.addView(mView, params);
    }

    /**
     * 关闭电话归属地提示框
     */
    public void closeAddressBox() {
        if (mWM != null && mView != null) {
            mWM.removeView(mView);// 电话挂断后,移除窗口布局
        }
    }
}
