package com.codoon.common.util;

/**
 * Created by huangjingqing on 17/4/14.
 */

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import org.sikuli.api.Screen;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class RawScreen {
    private byte[] raws;
    public final int width;
    public final int height;
    public final boolean rotate=true;

    public RawScreen(byte[] raw) {
        this.width = (raw[1] & 255) << 8 | raw[0] & 255;
        this.height = (raw[5] & 255) << 8 | raw[4] & 255;
        if(raw != null && raw.length == this.width * this.height * 4 + 12) {
            this.raws = raw;
          //  this.rotate = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).getDisplayRotation() != 0;
        } else {
            throw new RuntimeException("数据长度不能通过验证");
        }
    }

    public byte[] raw() {
        return this.raws;
    }

    public byte[] rgba() {
        return Arrays.copyOfRange(this.raws, 12, this.raws.length);
    }

    public Bitmap bitmap() {
        Bitmap bmp = Bitmap.createBitmap(this.width, this.height, Config.ARGB_8888);
        bmp.copyPixelsFromBuffer(ByteBuffer.wrap(Arrays.copyOfRange(this.raws, 12, this.raws.length)));
        return bmp;
    }

    public Bitmap bitmap(float scale) {
        Bitmap bmp = Bitmap.createBitmap(this.width, this.height, Config.ARGB_8888);
        bmp.copyPixelsFromBuffer(ByteBuffer.wrap(Arrays.copyOfRange(this.raws, 12, this.raws.length)));
        if(scale > 0.0F) {
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            bmp = Bitmap.createBitmap(bmp, 0, 0, this.width, this.height, matrix, true);
        }

        return bmp;
    }

    public Bitmap rectBitmap(Rect rect) {
        rect = this.transform(rect);
        int r_width = rect.right - rect.left;
        int r_height = rect.bottom - rect.top;
        ByteBuffer buffer = ByteBuffer.allocate(r_width * r_height * 4);

        for(int bmp = rect.top; bmp < rect.top + r_height; ++bmp) {
            byte[] row = Arrays.copyOfRange(this.raws, 4 * (bmp * this.width + rect.left) + 12, 4 * (bmp * this.width + rect.left) + 12 + r_width * 4);
            buffer.put(row);
        }

        buffer.flip();
        Bitmap var7 = Bitmap.createBitmap(r_width, r_height, Config.ARGB_8888);
        var7.copyPixelsFromBuffer(buffer);
        return var7;
    }

    public RectScreen rectScreen(Rect rect) {
        rect = this.transform(rect);
        int r_width = rect.right - rect.left;
        int r_height = rect.bottom - rect.top;
        ByteBuffer buffer = ByteBuffer.allocate(r_width * r_height * 4);

        for(int y = rect.top; y < rect.top + r_height; ++y) {
            byte[] row = Arrays.copyOfRange(this.raws, 4 * (y * this.width + rect.left) + 12, 4 * (y * this.width + rect.left) + 12 + r_width * 4);
            buffer.put(row);
        }

        return new RectScreen(buffer.array(), rect, this.rotate);
    }

    public Rect transform(Rect rect) {
        if(this.rotate) {
            rect = new Rect(this.width - (rect.top + rect.height()), rect.left, this.width - rect.top, rect.left + rect.width());
        }

        return rect;
    }

//    public Bitmap elementBitmap(WebElement el) {
//        if(el != null && el.exists) {
//
//                return this.rectBitmap(el.getBounds());
//
//        }
//
//        return null;
//    }
//
//    public RectScreen elementScreen(WebElement o) {
//        if(o != null && o.exists()) {
//
//                return this.rectScreen(o.getBounds());
//
//        }
//
//        return null;
//    }
}

