package net.xwdoor.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.adapter.HomeAdapter;
import net.xwdoor.mobilesafe.base.BaseActivity;


public class HomeActivity extends BaseActivity {

    private int[] mImgIds;
    private String[] mGvItems;

    /**
     * Activity启动方法，方便团队开发，参数信息一目了然
     */
    public static void startAct(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initVariables() {
        mGvItems = new String[]{"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};
        mImgIds = new int[]{R.drawable.home_safe,
                R.drawable.home_callmsgsafe, R.drawable.home_apps,
                R.drawable.home_taskmanager, R.drawable.home_netmanager,
                R.drawable.home_trojan, R.drawable.home_sysoptimize,
                R.drawable.home_tools, R.drawable.home_settings};

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        GridView gvFunction = (GridView) findViewById(R.id.gv_fuction);

        HomeAdapter adapter = new HomeAdapter(this,mGvItems,mImgIds);
        gvFunction.setAdapter(adapter);
        gvFunction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 8:
                        SettingActivity.startAct(HomeActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
