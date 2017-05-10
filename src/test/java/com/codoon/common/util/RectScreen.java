package com.codoon.common.util;

/**
 * Created by huangjingqing on 17/4/14.
 */


import android.graphics.Rect;

public class RectScreen {
    public final byte[] raw;
    public final Rect rect;
    public final int width;
    public final int height;
    public final boolean rotate;

    public RectScreen(byte[] raw, Rect rect, boolean rotate) {
        if(rect != null && raw != null) {
            if(rect.width() * rect.height() * 4 == raw.length) {
                this.raw = raw;
                this.rect = rect;
                this.rotate = rotate;
                this.width = rect.width();
                this.height = rect.height();
            } else {
                throw new RuntimeException("原始RGB数据与矩形区域不符");
            }
        } else {
            throw new RuntimeException("参数不能为null");
        }
    }
}

