package com.codoon.common.util;

/**
 * Created by huangjingqing on 17/4/14.
 */


import android.content.Intent;
import android.graphics.Rect;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class ScreenUtil {
    public ScreenUtil() {
    }

    public static RawScreen rawScreenshot() {
        String screenshotPath = "sdcard/screentmp";
        Intent screenShot = new Intent();
//        screenShot.setAction("sk.action.EXECUTE");
//        screenShot.putExtra("type", "2");
//        screenShot.putExtra("package", "this is rawScreenshot");
//        screenShot.putExtra("path", screenshotPath);
//        InstrumentationRegistry.getTargetContext().sendBroadcast(screenShot);
        SystemClock.sleep(500L);
        byte[] buffer = getBytes(screenshotPath);
        return new RawScreen(buffer);
    }

    private static byte[] getBytes(String filePath) {
        byte[] buffer = new byte[102400];

        try {
            File e = new File(filePath);
            FileInputStream fis = new FileInputStream(e);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];

            int n;
            while((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }

            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        return buffer;
    }

    public static byte[] rawColor(RawScreen raw, int row) {
        if(raw != null) {
            int starts = row * raw.width * 4 + 12;
            int ends = starts + raw.width * 4;
            return Arrays.copyOfRange(raw.raw(), starts, ends);
        } else {
            return null;
        }
    }

    public static byte[] buffer(RawScreen raw) {
        return raw != null?raw.raw():null;
    }

    public static byte[] rectColor(RawScreen raw, Rect rect) {
        if(raw == null) {
            return null;
        } else {
            rect = raw.transform(rect);
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;
            int size = width * height * 4;
            int index = 0;
            byte[] buffer = new byte[size];

            for(int i = rect.top; i < rect.top + height; ++i) {
                for(int j = rect.left; j < rect.left + width; ++j) {
                    buffer[index++] = raw.raw()[12 + (i * raw.width + j) * 4 + 0];
                    buffer[index++] = raw.raw()[12 + (i * raw.width + j) * 4 + 1];
                    buffer[index++] = raw.raw()[12 + (i * raw.width + j) * 4 + 2];
                    buffer[index++] = raw.raw()[12 + (i * raw.width + j) * 4 + 3];
                }
            }

            return buffer;
        }
    }
}
