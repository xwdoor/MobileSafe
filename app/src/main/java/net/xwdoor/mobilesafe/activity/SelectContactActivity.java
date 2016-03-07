package net.xwdoor.mobilesafe.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import net.xwdoor.mobilesafe.R;
import net.xwdoor.mobilesafe.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by XWdoor on 2016/3/7 007 11:18.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class SelectContactActivity extends BaseActivity {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, SelectContactActivity.class);
        context.startActivity(intent);
    }

    public static void startActForResult(Context context) {
        Intent intent = new Intent(context, SelectContactActivity.class);
        ((Activity) context).startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_contact);
        ListView lvContact = (ListView) findViewById(R.id.lv_contact);

        final ArrayList<HashMap<String, String>> listContacts = readContacts();
        lvContact.setAdapter(new SimpleAdapter(this, listContacts, R.layout.item_act_select_contact, new String[]{"name", "phone"},
                new int[]{R.id.tv_name, R.id.tv_phone}));
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = listContacts.get(position);
                Intent data = new Intent();
                data.putExtra("phone",map.get("phone"));

                setResult(0,data);
                finish();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    /**
     * 读取联系人
     */
    private ArrayList<HashMap<String, String>> readContacts() {
        // raw_contacts, data, mimetypes
        // 1. 读取raw_contacts, 获取contact_id
        // 2. 根据contact_id, 从data中读取具体信息(姓名/电话 ) data1, mimetype_id
        // 3. 根据mimetype_id, 从mimetypes中查到具体类型

        // 1. 读取raw_contacts, 获取contact_id
        Cursor rawCursor = getContentResolver().query(Uri.parse("content://com.android.contacts/raw_contacts"),
                new String[]{"contact_id"}, null, null, null);

        ArrayList<HashMap<String, String>> listContacts = new ArrayList<>();
        if (rawCursor != null) {
            while (rawCursor.moveToNext()) {
                String contactId = rawCursor.getString(rawCursor.getColumnIndex("contact_id"));

                // 2. 根据contact_id, 从data中读取具体信息(姓名/电话 ) data1, mimetype_id
                // 系统在查询data表时, 实际上查询的时view_data这个视图, 视图将data和mimetypes两个表的信息整合在了一起
                Cursor cursor = getContentResolver().query(Uri.parse("content://com.android.contacts/data"),
                        new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{contactId}, null);

                HashMap<String, String> map = new HashMap<>();
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String data = cursor.getString(cursor.getColumnIndex("data1"));
                        String mimetype = cursor.getString(cursor.getColumnIndex("mimetype"));

                        // 3. 根据mimetype_id, 从mimetypes中查到具体类型
                        if ("vnd.android.cursor.item/name".equals(mimetype)) {
                            //姓名
                            map.put("name", data);
                        } else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                            //电话
                            map.put("phone", data);
                        }
                    }
                    cursor.close();

                    if (!TextUtils.isEmpty(map.get("name")) && !TextUtils.isEmpty(map.get("phone"))) {// 过滤掉脏数据
                        listContacts.add(map);
                    }
                }

            }
            rawCursor.close();
        }
        return listContacts;
    }
}
