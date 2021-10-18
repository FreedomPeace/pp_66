package com.example.p2p.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by 刘楠 on 2016/7/20 0020.22:37
 */
public class OtherUtils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取拍照相片存储文件
     *
     * @param context
     * @return
     */
    public static File createFile(Context context) {
        File file;
        try {

            String timeStamp = String.valueOf(new Date().getTime());
            File images = context.getExternalFilesDir("Pictures");
            file = new File(images.getAbsolutePath()+File.separator+ timeStamp + ".jpg");
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("创建文件失败");
        }
        return file;
    }

}