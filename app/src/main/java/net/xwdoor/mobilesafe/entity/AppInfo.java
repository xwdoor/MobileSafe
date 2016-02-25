package net.xwdoor.mobilesafe.entity;

/**
 * App应用信息
 * Created by XWdoor on 2016/2/24 024 17:15.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class AppInfo {

    private int versionCode;
    private String versionName;
    private String description;
    private String downloadUrl;

    public AppInfo() {
        this(0,"0");
    }

    public AppInfo(int versionCode, String versionName) {
        this(versionCode,versionName,"");
    }

    public AppInfo(int versionCode, String versionName, String description) {
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.description = description;
        this.downloadUrl = "";
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String toJson() {
        return "{" +
                "\"versionCode\":\"" + versionCode + '"' +
                ", \"versionName\":\"" + versionName + '"' +
                ", \"description\":\"" + description + '"' +
                ", \"downloadUrl\":\"" + downloadUrl + '"' +
                '}';
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", description='" + description + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
