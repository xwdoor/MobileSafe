package net.xwdoor.mobilesafe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.db.BlackNumberDao;
import net.xwdoor.mobilesafe.entity.BlackNumberInfo;

import java.util.ArrayList;

/**
 * Created by XWdoor on 2016/3/16 016 14:30.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BlackNumberAdapter extends BaseAdapter {
    ArrayList<BlackNumberInfo> mList;
    Context mContext;
    BlackNumberDao mNumberDao;

    public BlackNumberAdapter(Context ctx, ArrayList<BlackNumberInfo> list, BlackNumberDao numberDao) {
        this.mContext = ctx;
        this.mList = list;
        this.mNumberDao = numberDao;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public BlackNumberInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //listview优化
    // 1. 使用convertView,重用对象,保证对象不被创建多次
    // 2. 使用ViewHolder,减少findViewById的次数
    // 3. 将ViewHolder改为static, 内存只加载一次
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if(convertView == null) {
            view = View.inflate(mContext, R.layout.item_black_number_adapter, null);
            holder = new ViewHolder();
            holder.tvNumber = (TextView) view.findViewById(R.id.tv_number);
            holder.tvMode = (TextView) view.findViewById(R.id.tv_mode);
            holder.ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
            // 将holder对象和view绑定在一起
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        final BlackNumberInfo info = getItem(position);
        holder.tvNumber.setText(info.number);
        switch (info.mode) {
            case 1:
                holder.tvMode.setText("拦截电话");
                break;
            case 2:
                holder.tvMode.setText("拦截短信");
                break;
            case 3:
                holder.tvMode.setText("拦截电话+短信");
                break;
            default:
                break;
        }

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从集合中移除
                mList.remove(info);
                // 从数据库移除
                mNumberDao.delete(info.number);
                //更新数据
                notifyDataSetChanged();
            }
        });
        return view;
    }

    static class ViewHolder {
        public TextView tvNumber;
        public TextView tvMode;
        public ImageView ivDelete;
    }
}
