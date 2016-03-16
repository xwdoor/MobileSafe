package net.xwdoor.mobilesafe.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.xwdoor.mobilesafe.entity.BlackNumberInfo;

import java.util.ArrayList;

/**
 * 黑名单数据库封装 crud create remove update delete
 *
 * Created by XWdoor on 2016/3/16 016 14:25.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BlackNumberDao {

    private static BlackNumberDao sInstance = null;// 懒汉模式
    private BlackNumberOpenHelper mHelper;

    private BlackNumberDao(Context ctx) {
        mHelper = new BlackNumberOpenHelper(ctx);
    }

    public static BlackNumberDao getInstance(Context ctx) {
        if (sInstance == null) {
            // A, B
            synchronized (BlackNumberDao.class) {
                if (sInstance == null) {
                    sInstance = new BlackNumberDao(ctx);
                }
            }
        }

        return sInstance;
    }

    /**
     * 增加黑名单
     *
     * @param number
     * @param mode
     */
    public void add(String number, int mode) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number", number);
        values.put("mode", mode);
        database.insert("blacknumber", null, values);
        database.close();
    }

    /**
     * 删除黑名单
     *
     * @param number
     */
    public void delete(String number) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        database.delete("blacknumber", "number=?", new String[]{number});
        database.close();
    }

    /**
     * 修改黑名单拦截模式
     *
     * @param number
     * @param mode
     */
    public void update(String number, int mode) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mode", mode);
        database.update("blacknumber", values, "number=?",
                new String[]{number});
        database.close();
    }

    /**
     * 查询黑名单
     *
     * @param number
     */
    public boolean find(String number) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        Cursor cursor = database
                .query("blacknumber", new String[] { "number", "mode" },
                        "number=?", new String[] { number }, null, null, null);

        boolean exist = false;
        if (cursor.moveToFirst()) {
            exist = true;
        }

        cursor.close();
        database.close();

        return exist;
    }

    /**
     * 查询黑名单拦截模式
     *
     * @param number
     */
    public int findMode(String number) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        Cursor cursor = database.query("blacknumber", new String[]{"mode"},
                "number=?", new String[]{number}, null, null, null);

        int mode = -1;
        if (cursor.moveToFirst()) {
            mode = cursor.getInt(0);
        }

        cursor.close();
        database.close();

        return mode;
    }

    /**
     * 查询全部黑名单
     */
    public ArrayList<BlackNumberInfo> findAll() {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        Cursor cursor = database.query("blacknumber", new String[] { "number",
                "mode" }, null, null, null, null, null);

        ArrayList<BlackNumberInfo> list = new ArrayList<BlackNumberInfo>();
        while (cursor.moveToNext()) {
            BlackNumberInfo info = new BlackNumberInfo();
            String number = cursor.getString(0);
            int mode = cursor.getInt(1);

            info.number = number;
            info.mode = mode;
            list.add(info);
        }

        cursor.close();
        database.close();

        return list;
    }

    /**
     * 分页查找数据
     *
     * @param index 查询起始位置
     * @return
     */
    public ArrayList<BlackNumberInfo> findPart(int index) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        // select number, mode from blacknumber limit 0,20;
        Cursor cursor = database
                .rawQuery(
                        "select number, mode from blacknumber order by _id desc limit ?,20",
                        new String[] { index + "" });// 逆序排列
        ArrayList<BlackNumberInfo> list = new ArrayList<BlackNumberInfo>();
        while (cursor.moveToNext()) {
            BlackNumberInfo info = new BlackNumberInfo();
            String number = cursor.getString(0);
            int mode = cursor.getInt(1);

            info.number = number;
            info.mode = mode;
            list.add(info);
        }

        cursor.close();
        database.close();

        return list;
    }

    /**
     * 获取数据总数
     */
    public int getTotalCount() {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select count(*) from blacknumber",
                null);

        int totalCount = 0;
        if (cursor.moveToFirst()) {
            totalCount = cursor.getInt(0);
        }

        cursor.close();
        database.close();
        return totalCount;
    }

}

