package net.xwdoor.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreference工具类
 *
 * Created by XWdoor on 2016/3/1 001 17:38.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class PrefUtils {

	public static void putBoolean(String key, boolean value, Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).apply();
	}

	public static boolean getBoolean(String key, boolean defValue, Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}

	public static void putString(String key, String value, Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		sp.edit().putString(key, value).apply();
	}

	public static String getString(String key, String defValue, Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}

	public static void putInt(String key, int value, Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).apply();
	}

	public static int getInt(String key, int defValue, Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		return sp.getInt(key, defValue);
	}

	public static void remove(String key, Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		sp.edit().remove(key).apply();
	}

}
