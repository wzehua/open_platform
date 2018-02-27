package com.platform.open.wxplatform;

import android.content.Context;

import com.platform.open.wxplatform.share.WxShare;
import com.platform.open.wxplatform.share.WxShareReq;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信开放平台移动应用的功能管理类
 * 管理分享，收藏，登录，支付等功能
 * <p>
 * Created by wangzehua on 18/2/27.
 */

public class WxManager {

    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
    private IWXAPI api;

    private static WxManager wxManager;

    public static WxManager getInstance() {

        if (wxManager == null) {

            wxManager = new WxManager();
        }

        return wxManager;
    }


    private String APP_ID;

    /**
     * 设置授权appID
     *
     * @param appId
     */
    public void setAppId(String appId) {

        APP_ID = appId;
    }

    /**
     * 取消到微信的注册，释放链接
     */
    public void unregisterApp() {

        api.unregisterApp();
        api = null;
        mAppCxt = null;

    }

    /**
     * 创建且注册 到微信
     */

    private Context mAppCxt;

    public void registerApp(Context appCxt) throws Exception {

        if (APP_ID == null || APP_ID.trim().length() < 1) {

            throw new Exception("请设置App_ID");
        }

        mAppCxt = appCxt;

        api = WXAPIFactory.createWXAPI(appCxt, APP_ID, false);
        api.registerApp(APP_ID);
    }


    public static int WX_SESSION = SendMessageToWX.Req.WXSceneSession; //微信好友
    public static int WX_FRIENDS = SendMessageToWX.Req.WXSceneTimeline; //微信朋友圈
    public static int WX_HISTORY = SendMessageToWX.Req.WXSceneFavorite; //微信收藏

    /**
     * 分享接口
     *
     * @param req 分享的内容对象
     * @param dis 分享的场景 （微信好友，微信朋友圈，微信收藏）
     * @return
     */
    public String sendReq(WxShareReq req, int dis) {

        if (req == null) {
            return "分享的内容对方不能为空";
        }

        if (api == null) {
            return "没有注册微信接口，请先完成注册";
        }

        if (weixin_supported_version()) {

            WxShare share = new WxShare();
            share.sendReq(mAppCxt, api, req, dis);

        } else {

            return "您没有安装微信APP哦～";
        }

        return null;

    }


    private boolean weixin_supported_version() {

        int wxSdkVersion = api.getWXAppSupportAPI();
        if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
            return true;
        } else {
            return false;
        }
    }


}
