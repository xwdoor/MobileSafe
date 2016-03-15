package net.xwdoor.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.xwdoor.mobilesafe.R;

/**
 * Created by XWdoor on 2016/3/1 001 16:19.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class SettingClickView extends RelativeLayout {

    public static final String NAMESPACE_XWDOOR = "http://schemas.android.com/apk/res-auto";


    private TextView tvTitle;
    private TextView tvDesc;
    private CheckBox cbCheck;

    public SettingClickView(Context context) {
        super(context);
        initView();
    }

    public SettingClickView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public SettingClickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View view = View.inflate(getContext(), R.layout.item_setting_click,null);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvDesc = (TextView) view.findViewById(R.id.tv_desc);

        addView(view);
    }

    /** 设置标题 */
    public void setTitle(String title){
        tvTitle.setText(title);
    }

    /** 设置描述 */
    public void setDesc(String desc){
        tvDesc.setText(desc);
    }
}
