package net.xwdoor.mobilesafe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.xwdoor.mobilesafe.R;

/**
 * Created by XWdoor on 2016/2/26 026 14:31.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class HomeAdapter extends BaseAdapter {
    private final Context mContext;
    private String[] mStrItems;
    private int[] mImgIds;

    public HomeAdapter(Context context,String[] mStrItems, int[] mImgIds) {
        this.mContext = context;
        this.mStrItems = mStrItems;
        this.mImgIds = mImgIds;
    }

    @Override
    public int getCount() {
        return mStrItems.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.item_home_adapter,null);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        TextView tvFuncName = (TextView) view.findViewById(R.id.tv_func_name);

        ivIcon.setImageResource(mImgIds[position]);
        tvFuncName.setText(mStrItems[position]);
        return view;
    }
}
