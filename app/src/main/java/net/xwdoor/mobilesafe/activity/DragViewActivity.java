package net.xwdoor.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.utils.PrefUtils;

/**
 * Created by XWdoor on 2016/3/15 015 10:02.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class DragViewActivity extends BaseActivity {

    private int startX;
    private int startY;
    private int mScreenWidth;
    private int mScreenHeight;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, DragViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initVariables() {
        //得到屏幕宽高度
        WindowManager wm = getWindowManager();
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        mScreenWidth = size.x;
        mScreenHeight = size.y;

        //获取归属地提示框位置
        startX = PrefUtils.getInt(PREF_LAST_X,0,this);
        startY = PrefUtils.getInt(PREF_LAST_Y,0,this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_drag_view);
        final TextView tvDragView = (TextView) findViewById(R.id.tv_drag_view);
        final TextView tvTop = (TextView) findViewById(R.id.tv_top);
        final TextView tvBottom = (TextView) findViewById(R.id.tv_bottom);
        // 根据布局位置,更新提示框位置
        if (startY > mScreenHeight / 2) {// 屏幕下方
            tvTop.setVisibility(View.VISIBLE);
            tvBottom.setVisibility(View.INVISIBLE);
        } else {
            tvTop.setVisibility(View.INVISIBLE);
            tvBottom.setVisibility(View.VISIBLE);
        }
        // 获取当前控件的布局参数
        // 父控件是谁,就拿谁定义的布局参数
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvDragView.getLayoutParams();
        // 临时修改布局参数
        layoutParams.leftMargin = startX;
        layoutParams.topMargin = startY;

        // 设置触摸监听
        tvDragView.setOnTouchListener(new View.OnTouchListener() {
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
                        int l = tvDragView.getLeft() + dx;
                        int t = tvDragView.getTop() + dy;
                        int r = tvDragView.getRight() + dx;
                        int b = tvDragView.getBottom() + dy;

                        //避免布局超出屏幕边界
                        if (l >= 0 && r <= mScreenWidth && t >= 0 && b <= mScreenHeight - 20) {//减去状态栏，大概20像素
                            // 根据布局位置,更新提示框位置
                            if (t > mScreenHeight / 2) {// 屏幕下方
                                tvTop.setVisibility(View.VISIBLE);
                                tvBottom.setVisibility(View.INVISIBLE);
                            } else {
                                tvTop.setVisibility(View.INVISIBLE);
                                tvBottom.setVisibility(View.VISIBLE);
                            }

                            tvDragView.layout(l, t, r, b);

                            startX = (int) event.getRawX();
                            startY = (int) event.getRawY();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        //记录最终坐标
                        PrefUtils.putInt(PREF_LAST_X, tvDragView.getLeft(), DragViewActivity.this);
                        PrefUtils.putInt(PREF_LAST_Y, tvDragView.getTop(), DragViewActivity.this);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void loadData() {

    }
}