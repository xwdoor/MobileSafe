package net.xwdoor.mobilesafe.entity;

/**
 * 黑名单信息
 *
 * Created by XWdoor on 2016/3/16 016 14:27.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BlackNumberInfo {
    public BlackNumberInfo() {
    }

    public BlackNumberInfo(String number, int mode) {
        this.number = number;
        this.mode = mode;
    }

    public String number;
    public int mode;
}
