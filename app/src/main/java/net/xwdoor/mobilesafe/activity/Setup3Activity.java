package net.xwdoor.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;
import net.xwdoor.mobilesafe.utils.PrefUtils;

/**
 * Created by XWdoor on 2016/3/4 004 13:32.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class Setup3Activity extends BaseActivity {

    private EditText etPhoneNumber;
    private String mPhoneNumber;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, Setup3Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initVariables() {
        mPhoneNumber = PrefUtils.getString(PREF_PHONE_NUMBER,"",this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setup3);

        Button btnPreviousPage = (Button) findViewById(R.id.btn_previous_page);
        Button btnNextPage = (Button) findViewById(R.id.btn_next_page);
        Button btnSelectContact = (Button) findViewById(R.id.btn_select_contact);
        etPhoneNumber = (EditText) findViewById(R.id.et_number);
        etPhoneNumber.setText(mPhoneNumber);

        btnPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage();
            }
        });
        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });
        btnSelectContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectContactActivity.startActForResult(Setup3Activity.this);
            }
        });
    }

    @Override
    protected void loadData() {

    }


    /**
     * 上一步
     */
    protected void previousPage() {
        Setup2Activity.startAct(this);
        finish();
        // activity切换动画
        overridePendingTransition(R.anim.anim_previous_in,
                R.anim.anim_previous_out);
    }

    /**
     * 下一步
     */
    protected void nextPage() {
        mPhoneNumber = etPhoneNumber.getText().toString();
        if(!TextUtils.isEmpty(mPhoneNumber)){
            //保存安全号码
            PrefUtils.putString(PREF_PHONE_NUMBER,mPhoneNumber,this);

            Setup4Activity.startAct(this);
            finish();
            // activity切换动画
            overridePendingTransition(R.anim.anim_next_in,
                    R.anim.anim_next_out);
        }else {
            showToast("安全号码不能为空");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null){
            mPhoneNumber = data.getStringExtra("phone");
            mPhoneNumber = mPhoneNumber.replaceAll("-","").replaceAll(" ", "");
            etPhoneNumber.setText(mPhoneNumber);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}