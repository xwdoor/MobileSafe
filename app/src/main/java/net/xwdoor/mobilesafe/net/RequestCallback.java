package net.xwdoor.mobilesafe.net;

/**
 * Created by XWdoor on 2016/2/25 025 9:56.
 * 博客：http://blog.csdn.net/xwdoor
 */
public interface RequestCallback {

    /**
     * 请求成功
     * @param content 服务器返回的 Json 字符串
     */
    public void onSuccess(String content);

    /**
     * 请求失败
     * @param errorMsg 失败信息
     */
    public void onFaile(String errorMsg);
}
