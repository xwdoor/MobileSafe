package net.xwdoor.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.adapter.BlackNumberAdapter;
import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.db.BlackNumberDao;
import net.xwdoor.mobilesafe.entity.BlackNumberInfo;

import java.util.ArrayList;
import java.util.Random;

/**
 * 通讯卫士：黑名单
 * <p>
 * Created by XWdoor on 2016/3/16 016 14:06.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BlackNumberActivity extends BaseActivity {

    private Button btnAddNumber;
    private ListView lvList;
    private ProgressBar pbLoading;

    private BlackNumberAdapter mAdapter;
    private ArrayList<BlackNumberInfo> mList = null;
    private Handler mHandler;
    private BlackNumberDao mNumberDao;
    private boolean isLoading = false;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, BlackNumberActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initVariables() {
        mNumberDao = BlackNumberDao.getInstance(this);
        //初始化 Handler
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (mAdapter == null) {// 第一页
                    mAdapter = new BlackNumberAdapter(BlackNumberActivity.this, mList, mNumberDao);
                    // 给listview设置数据
                    lvList.setAdapter(mAdapter);// 这个方法导致数据默认跑到第0个位置
                } else {
                    //更新列表数据
                    mAdapter.notifyDataSetChanged();
                }

                pbLoading.setVisibility(View.GONE);
                isLoading = false;
            }
        };
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_black_number);
        btnAddNumber = (Button) findViewById(R.id.btn_add_number);
        lvList = (ListView) findViewById(R.id.lv_list);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);

        lvList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    // 获取最后一个可见item的位置
                    int lastPosition = lvList.getLastVisiblePosition();
                    // 判断是否滑动 到最后一个
                    if (lastPosition >= mList.size() - 1 && !isLoading) {
                        //读取数据库中数据的总数
                        int totalCount = mNumberDao.getTotalCount();
                        if (mList.size() >= totalCount) {
                            //说明没有更多的数据了
                            showToast("没有更多的数据了");
                            return;
                        }
                        // 加载下一页
                        loadBlackNumber();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        btnAddNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBlackNumber();
            }
        });
    }

    @Override
    protected void loadData() {
        loadBlackNumber();
    }

    /**
     * 加载黑名单
     */
    private void loadBlackNumber() {
        pbLoading.setVisibility(View.VISIBLE);
        isLoading = true;
        new Thread() {
            @Override
            public void run() {
                if (mList == null) {
                    //添加模拟数据
                    if (mNumberDao.getTotalCount() == 0)
                        addMockData();

                    // 加载第一页数据
                    mList = mNumberDao.findPart(0);// 20条数据
                } else {
                    // 给集合添加一个集合, 集合相加
                    mList.addAll(mNumberDao.findPart(mList.size()));
                }
                //更新列表数据
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    private void addMockData() {
        for (int i = 0; i < 100; i++) {
            Random random = new Random();
            int mode = random.nextInt(3) + 1;
            if (i < 10) {
                mNumberDao.add("1341234567" + i, mode);
            } else {
                mNumberDao.add("135123456" + i, mode);
            }
        }
    }

    /**
     * 添加黑名单
     */
    private void addBlackNumber() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_add_black_number, null);
        final EditText etNumber = (EditText) view.findViewById(R.id.et_number);
        Button btnOk = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        final RadioGroup rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = etNumber.getText().toString().trim();
                if (!TextUtils.isEmpty(number)) {
                    int checkedRadioButtonId = rgGroup.getCheckedRadioButtonId();// 获取当前被选中radiobutton的id

                    int mode = 1;
                    switch (checkedRadioButtonId) {
                        case R.id.rb_phone:
                            mode = 1;
                            break;
                        case R.id.rb_sms:
                            mode = 2;
                            break;
                        case R.id.rb_all:
                            mode = 3;
                            break;
                        default:
                            break;
                    }
                    mNumberDao.add(number, mode);
                    // 在第一个位置添加item对象
                    mList.add(0, new BlackNumberInfo(number, mode));
                    mAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }
}