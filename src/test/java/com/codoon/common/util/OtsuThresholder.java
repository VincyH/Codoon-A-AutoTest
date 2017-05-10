package com.codoon.common.util;

/**
 * Created by huangjingqing on 17/4/14.
 */
public class OtsuThresholder {
    private int[] histData = new int[256];
    private int maxLevelValue;
    private int threshold;

    public OtsuThresholder() {
    }

    public int[] getHistData() {
        return this.histData;
    }

    public int getMaxLevelValue() {
        return this.maxLevelValue;
    }

    public int getThreshold() {
        return this.threshold;
    }

    public int doThreshold(byte[] srcData, byte[] monoData) {
        int ptr;
        for(ptr = 0; ptr < this.histData.length; this.histData[ptr++] = 0) {
            ;
        }

        ptr = 0;

        int total;
        for(this.maxLevelValue = 0; ptr < srcData.length; ++ptr) {
            total = 255 & srcData[ptr];
            ++this.histData[total];
            if(this.histData[total] > this.maxLevelValue) {
                this.maxLevelValue = this.histData[total];
            }
        }

        total = srcData.length;
        float sum = 0.0F;

        for(int sumB = 0; sumB < 256; ++sumB) {
            sum += (float)(sumB * this.histData[sumB]);
        }

        float var14 = 0.0F;
        int wB = 0;
        boolean wF = false;
        float varMax = 0.0F;
        this.threshold = 0;

        for(int t = 0; t < 256; ++t) {
            wB += this.histData[t];
            if(wB != 0) {
                int var15 = total - wB;
                if(var15 == 0) {
                    break;
                }

                var14 += (float)(t * this.histData[t]);
                float mB = var14 / (float)wB;
                float mF = (sum - var14) / (float)var15;
                float varBetween = (float)wB * (float)var15 * (mB - mF) * (mB - mF);
                if(varBetween > varMax) {
                    varMax = varBetween;
                    this.threshold = t;
                }
            }
        }

        if(monoData != null) {
            for(ptr = 0; ptr < srcData.length; ++ptr) {
                monoData[ptr] = (byte)((255 & srcData[ptr]) >= this.threshold?1:0);
            }
        }

        return this.threshold;
    }
}
