package com.platform.open.wxplatform.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.platform.open.wxplatform.Utils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.net.URL;

/**
 * Created by wangzehua on 18/2/27.
 */

public class WxShare {


    private IWXAPI mApi;
    private Context mAppCxt;

    public void sendReq(Context cxt, IWXAPI api, WxShareReq req, int dis) {

        mApi = api;
        mAppCxt = cxt;

        ShareWXTask task = new ShareWXTask(req, dis);
        task.execute();
    }

    private final int THUMB_SIZE = 120;

    private class ShareWXTask extends AsyncTask<String, Integer, Bitmap> {

        private WxShareReq shareInfo;
        private int dis;

        public ShareWXTask(WxShareReq info, int is) {
            shareInfo = info;
            dis = is;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = shareInfo.getUrl();
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = shareInfo.getTitle();
            msg.description = shareInfo.getDesc();
            String imgUrl = shareInfo.getLogo();
            Bitmap thumbBmp = null;
            try {
                if (imgUrl != null && imgUrl.length() > 4) {
                    Bitmap bmp = BitmapFactory.decodeStream(new URL(imgUrl).openStream());
                    thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                    bmp.recycle();
                    msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);
                }
            } catch (Exception e) {

            }

            if (thumbBmp == null || msg.thumbData.length >= 32000) {
                Bitmap thumb0 = BitmapFactory.decodeResource(mAppCxt.getResources(), shareInfo.getDefultLogo());
                msg.thumbData = Utils.bmpToByteArray(thumb0, true);
            }

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;
            req.scene = dis;
            mApi.sendReq(req);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progresses) {
        }

        @Override
        protected void onPostExecute(Bitmap result) {
        }

        @Override
        protected void onCancelled() {
        }
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
