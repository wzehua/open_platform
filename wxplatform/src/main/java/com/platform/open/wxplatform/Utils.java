package com.platform.open.wxplatform;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by wangzehua on 18/2/27.
 */

public class Utils {


    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();

        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
