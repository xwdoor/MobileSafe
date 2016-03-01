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
public class SettingItemView extends RelativeLayout {

    public static final String NAMESPACE_XWDOOR = "http://schemas.android.com/apk/res-auto";
    private String mDescOff;
    private String mDescOn;

    private TextView tvTitle;
    private TextView tvDesc;
    private CheckBox cbCheck;

    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        int attrCount = attrs.getAttributeCount();
        //获取stitle属性的值
        String title = attrs.getAttributeValue(NAMESPACE_XWDOOR, "stitle");
        //获取关闭设置时的描述
        mDescOn = attrs.getAttributeValue(NAMESPACE_XWDOOR, "desc_on");
        //获取开启设置时的描述
        mDescOff = attrs.getAttributeValue(NAMESPACE_XWDOOR, "desc_off");
        initView();

        setTitle(title);
        setDesc(mDescOff);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View view = View.inflate(getContext(), R.layout.item_setting,null);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvDesc = (TextView) view.findViewById(R.id.tv_desc);
        cbCheck = (CheckBox) view.findViewById(R.id.cb_check);

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

    /** 是否开启 */
    public boolean isChecked(){
        return cbCheck.isChecked();
    }

    /** 设置开启状态 */
    public void setChecked(boolean checked){
        cbCheck.setChecked(checked);
        if(checked){
            setDesc(mDescOn);
        }else {
            setDesc(mDescOff);
        }
    }
}
