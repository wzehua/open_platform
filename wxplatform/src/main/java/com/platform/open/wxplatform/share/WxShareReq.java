package com.platform.open.wxplatform.share;

/**
 * Created by wangzehua on 18/2/27.
 */

public class WxShareReq {

    private String title;
    private String logo;
    private String desc;
    private String url;
    private Integer defultLogo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDefultLogo() {
        return defultLogo;
    }

    public void setDefultLogo(Integer defultLogo) {
        this.defultLogo = defultLogo;
    }
}
