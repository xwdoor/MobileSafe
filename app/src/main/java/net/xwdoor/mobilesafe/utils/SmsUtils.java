package net.xwdoor.mobilesafe.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 短信备份工具
 * 需要权限
 * <uses-permission android:name="android.permission.READ_SMS" />
 * <uses-permission android:name="android.permission.WRITE_SMS" />
 *
 * Created by XWdoor on 2016/3/18 018 10:58.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class SmsUtils {

    public static void smsBackup(Context context, File output, SmsCallback callback) {
        // 1.读取短信数据库 mmssms.db/sms
        // 2.将短信数据输出为xml文件

        //数据库字段：
        // address: 短信号码
        // date:短信时间
        // read: 1表示已读, 0表示未读
        // type: 类型 1:收到短信, 2表示发出的短信
        // body: 短信内容

        try {
            XmlSerializer xml = Xml.newSerializer();
            xml.setOutput(new FileOutputStream(output), "utf-8");
            xml.startDocument("utf-8", true);
            xml.startTag(null, "smss");

            //查询短信
            Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms"),
                    new String[]{"address", "date", "read", "type", "body"}, null, null, null);

            if (cursor != null) {
                callback.preSmsBackup(cursor.getCount());//回传短信总数
                //读取数据，并写入xml文件
                int progress = 0;
                while (cursor.moveToNext()){
                    xml.startTag(null, "sms");

                    //短信号码
                    xml.startTag(null, "address");
                    String address = cursor.getString(cursor.getColumnIndex("address"));
                    xml.text(address);
                    xml.endTag(null, "address");

                    //短信日期
                    xml.startTag(null, "date");
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    xml.text(date);
                    xml.endTag(null,"date");

                    //短信阅读状态
                    xml.startTag(null, "read");
                    String read = cursor.getString(cursor.getColumnIndex("read"));
                    xml.text(read);
                    xml.endTag(null,"read");

                    //短信类型
                    xml.startTag(null, "type");
                    String type = cursor.getString(cursor.getColumnIndex("type"));
                    xml.text(type);
                    xml.endTag(null,"type");

                    //短信内容
                    xml.startTag(null, "body");
                    String body = cursor.getString(cursor.getColumnIndex("body"));
                    xml.text(body);
                    xml.endTag(null, "body");

                    xml.endTag(null, "sms");

                    progress++;
                    // 更新进度的方法,可以放在子线程
                    callback.onSmsBackup(progress);//回传备份进度

                    Thread.sleep(500);// 模拟耗时操作
                }
                cursor.close();
            }

            xml.endTag(null,"smss");
            xml.endDocument();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 短信备份的回调接口
     *
     */
    public interface SmsCallback {
        /**
         * 备份短信前的回调
         * @param totalCount 短信总数
         */
        void preSmsBackup(int totalCount);

        /**
         * 备份过程中的回调
         * @param progress 备份进度(备份短信条数)
         */
        void onSmsBackup(int progress);
    }
}
