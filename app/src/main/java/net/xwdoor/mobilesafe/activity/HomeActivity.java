package net.xwdoor.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.adapter.HomeAdapter;
import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.utils.MD5Utils;
import net.xwdoor.mobilesafe.utils.PrefUtils;


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

        HomeAdapter adapter = new HomeAdapter(this, mGvItems, mImgIds);
        gvFunction.setAdapter(adapter);
        gvFunction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://手机防盗界面
                        showSafeDialog();
                        break;
                    case 1://通讯卫士
                        BlackNumberActivity.startAct(HomeActivity.this);
                        break;
                    case 7://高级工具
                        ToolsActivity.startAct(HomeActivity.this);
                        break;
                    case 8://设置界面
                        SettingActivity.startAct(HomeActivity.this);
                        break;
                }
            }
        });
    }

    /**
     * 显示手机防盗弹窗
     */
    private void showSafeDialog() {
        String password = PrefUtils.getString(PREF_PASSWORD, "", this);

        //判断是否存在密码
        if (!TextUtils.isEmpty(password)) {
            //显示输入密码弹窗
            showInputPasswordDialog();
        } else {
            //显示设置密码弹窗
            showSetPasswordDialog();
        }

    }

    /**
     * 输入密码的弹窗
     */
    private void showInputPasswordDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = View.inflate(this, R.layout.dialog_input_password, null);
        Button btnOk = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString().trim();
                //获取保存的密码
                String savedPassword = PrefUtils.getString(PREF_PASSWORD, "", HomeActivity.this);

                //判断密码是否为空
                if (!TextUtils.isEmpty(password)) {
                    if (MD5Utils.encode(password).equals(savedPassword)) {
                        AntiTheftActivity.startAct(HomeActivity.this);
                        dialog.dismiss();
                    } else {
                        showToast("密码错误");
                    }
                } else {
                    showToast("密码不能为空");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }

    /**
     * 设置密码的弹窗
     */
    private void showSetPasswordDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = View.inflate(this, R.layout.dialog_set_password, null);
        Button btnOk = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
        final EditText etPasswordConfirm = (EditText) view.findViewById(R.id.et_password_confirm);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString().trim();
                String passwordConfirm = etPasswordConfirm.getText().toString().trim();

                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirm)) {
                    if (password.equals(passwordConfirm)) {
                        PrefUtils.putString(PREF_PASSWORD, MD5Utils.encode(password), HomeActivity.this);
                        AntiTheftActivity.startAct(HomeActivity.this);
                        dialog.dismiss();
                    } else {
                        showToast("两次密码不一致");
                    }
                } else {
                    showToast("密码不能为空");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }

    @Override
    protected void loadData() {

    }
}
