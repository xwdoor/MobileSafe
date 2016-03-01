package net.xwdoor.mobilesafe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.xwdoor.mobilesafe.R;

/**
 * 用于主页面九宫格的列表展示所用的adapter
 *
 * Created by XWdoor on 2016/2/26 026 14:31.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class HomeAdapter extends BaseAdapter {
    private final Context mContext;
    //功能列表的文字描述项
    private String[] mStrItems;
    //功能列表的图片展示项
    private int[] mImgIds;

    public HomeAdapter(Context context,String[] mStrItems, int[] mImgIds) {
        this.mContext = context;
        this.mStrItems = mStrItems;
        this.mImgIds = mImgIds;
    }

    //返回功能列表项目数量
    @Override
    public int getCount() {
        return mStrItems.length;
    }

    //获取某功能项目
    @Override
    public Object getItem(int position) {
        return null;
    }

    //获取某功能Id
    @Override
    public long getItemId(int position) {
        return position;
    }

    //显示相应功能的视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //生成视图
        View view = View.inflate(mContext, R.layout.item_home_adapter,null);
        //获取视图中的图片展示项的控件
        ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        //获取文字描述项的控件
        TextView tvFuncName = (TextView) view.findViewById(R.id.tv_func_name);

        //设置文字和图片
        ivIcon.setImageResource(mImgIds[position]);
        tvFuncName.setText(mStrItems[position]);
        return view;
    }
}
