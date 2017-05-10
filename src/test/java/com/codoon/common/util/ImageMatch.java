package com.codoon.common.util;

/**
 * Created by huangjingqing on 17/4/14.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.support.test.InstrumentationRegistry;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
public class ImageMatch {
    private static int RGB_RANGE = 10;

    public ImageMatch() {
    }

    public static List<MatchPoint> findObject(String picName) {
        RawScreen raw = ScreenUtil.rawScreenshot();

        Bitmap pattern = null;

        try {
            pattern = BitmapFactory.decodeStream(InstrumentationRegistry.getTargetContext().getAssets().open(picName));
        } catch (IOException var35) {
            var35.printStackTrace();
        }

        ByteBuffer pbuffer = ByteBuffer.allocate(pattern.getWidth() * pattern.getHeight() * 4);
        if(raw.rotate) {
            Matrix patternBuffer = new Matrix();
            patternBuffer.postRotate(90.0F);
            pattern = Bitmap.createBitmap(pattern, 0, 0, pattern.getWidth(), pattern.getHeight(), patternBuffer, true);
        }

        pattern.copyPixelsToBuffer(pbuffer);
        byte[] var37 = pbuffer.array();
        byte[] screenBuffer = raw.rgba();
        int sw = raw.width;
        int sh = raw.height;
        int pw = pattern.getWidth();
        int ph = pattern.getHeight();
        int match_x = 0;
        int match_y = 0;
        int x = 0;
        int y = 0;
        int[][] keys = new int[][]{{0, 0, 255, 255, 255}, {1, 0, 0, 0, 0}};

        int sr;
        int sg;
        int sb;
        for(int e = 0; e < ph; ++e) {
            for(int h_avail = 0; h_avail < pw; ++h_avail) {
                int p_skip = var37[(e * pw + h_avail) * 4] & 255;
                sr = var37[(e * pw + h_avail) * 4 + 1] & 255;
                sg = var37[(e * pw + h_avail) * 4 + 2] & 255;
                sb = var37[(e * pw + h_avail) * 4 + 3] & 255;
                if(sb == 255) {
                    if(p_skip < keys[0][2] && sr < keys[0][3] && sg < keys[0][4]) {
                        keys[0][0] = h_avail;
                        keys[0][1] = e;
                        keys[0][2] = p_skip;
                        keys[0][3] = sr;
                        keys[0][4] = sg;
                    }

                    if(p_skip > keys[1][2] && sr > keys[1][3] && sg > keys[1][4]) {
                        keys[1][0] = h_avail;
                        keys[1][1] = e;
                        keys[1][2] = p_skip;
                        keys[1][3] = sr;
                        keys[1][4] = sg;
                    }
                }
            }
        }

        try {
            while(y < sh) {
                boolean var38 = sw - x >= pw - match_x;
                boolean var39 = sh - y >= ph - match_y;
                boolean var40 = x - match_x + pw < sw;
                if(var38 && var39) {
                    sr = screenBuffer[(sw * y + x) * 4] & 255;
                    sg = screenBuffer[(sw * y + x) * 4 + 1] & 255;
                    sb = screenBuffer[(sw * y + x) * 4 + 2] & 255;
                    int pr = var37[(pw * match_y + match_x) * 4] & 255;
                    int pg = var37[(pw * match_y + match_x) * 4 + 1] & 255;
                    int pb = var37[(pw * match_y + match_x) * 4 + 2] & 255;
                    int pa = var37[(pw * match_y + match_x) * 4 + 3] & 255;
                    boolean equals = false;
                    if(pa < 255) {
                        equals = true;
                    } else {
                        equals = sr - pr <= RGB_RANGE && sr - pr >= -RGB_RANGE && sg - pg <= RGB_RANGE && sg - pg >= -RGB_RANGE && sb - pb <= RGB_RANGE && sb - pb >= -RGB_RANGE;
                    }

                    int nr;
                    int ng;
                    int nb;
                    int l;
                    int r;
                    if(equals) {
                        if(match_x == pw - 1) {
                            if(match_y == ph - 1) {
                                MatchPoint var41 = null;
                                if(raw.rotate) {
                                    Point var42 = new Point(sh - (y - ph / 2), sw - (x - pw / 2));
                                    var41 = new MatchPoint(var42, sh - y, sw - x, ph, pw);
                                } else {
                                    var41 = new MatchPoint(new Point(x - pw / 2, y - ph / 2), x, y, pw, ph);
                                }

                                return Arrays.asList(new MatchPoint[]{var41});
                            }

                            match_x = 0;
                            ++match_y;
                            x = x - pw + 1;
                            ++y;
                        } else {
                            if(match_x == match_y && match_x == 0) {
                                nr = screenBuffer[((y + keys[0][1]) * sw + x + keys[0][0]) * 4] & 255;
                                ng = screenBuffer[((y + keys[0][1]) * sw + x + keys[0][0]) * 4 + 1] & 255;
                                nb = screenBuffer[((y + keys[0][1]) * sw + x + keys[0][0]) * 4 + 2] & 255;
                                int var43 = screenBuffer[((y + keys[1][1]) * sw + x + keys[1][0]) * 4] & 255;
                                l = screenBuffer[((y + keys[1][1]) * sw + x + keys[1][0]) * 4 + 1] & 255;
                                r = screenBuffer[((y + keys[1][1]) * sw + x + keys[1][0]) * 4 + 2] & 255;
                                boolean var44 = nr - keys[0][2] <= RGB_RANGE && nr - keys[0][2] >= -RGB_RANGE && ng - keys[0][3] <= RGB_RANGE && ng - keys[0][3] >= -RGB_RANGE && nb - keys[0][4] <= RGB_RANGE && nb - keys[0][4] >= -RGB_RANGE;
                                if(!var44) {
                                    ++x;
                                    match_y = 0;
                                    match_x = 0;
                                    continue;
                                }

                                if(var43 - keys[1][2] > RGB_RANGE || var43 - keys[1][2] < -RGB_RANGE || l - keys[1][3] > RGB_RANGE || l - keys[1][3] < -RGB_RANGE || r - keys[1][4] > RGB_RANGE || r - keys[1][4] < -RGB_RANGE) {
                                    ++x;
                                    match_y = 0;
                                    match_x = 0;
                                    continue;
                                }
                            }

                            ++x;
                            ++match_x;
                        }
                    } else if(!var40) {
                        x = 0;
                        y = y - match_y + 1;
                        match_y = 0;
                        match_x = 0;
                    } else {
                        nr = screenBuffer[(sw * (y - match_y) + x - match_x + pw) * 4] & 255;
                        ng = screenBuffer[(sw * (y - match_y) + x - match_x + pw) * 4 + 1] & 255;
                        nb = screenBuffer[(sw * (y - match_y) + x - match_x + pw) * 4 + 2] & 255;
                        boolean found = false;

                        for(l = pw - 1; l >= 0; --l) {
                            r = var37[l * 4] & 255;
                            int g = var37[l * 4 + 1] & 255;
                            int b = var37[l * 4 + 2] & 255;
                            int a = var37[l * 4 + 3] & 255;
                            if(a < 255) {
                                found = true;
                            } else {
                                found = nr - r <= RGB_RANGE && nr - r >= -RGB_RANGE && ng - g <= RGB_RANGE && ng - g >= -RGB_RANGE && nb - b <= RGB_RANGE && nb - b >= -RGB_RANGE;
                            }

                            if(found) {
                                found = true;
                                x = x - match_x + pw - l;
                                y -= match_y;
                                match_y = 0;
                                match_x = 0;
                                break;
                            }
                        }

                        if(!found) {
                            x = x - match_x + pw + 1;
                            y -= match_y;
                            match_y = 0;
                            match_x = 0;
                        }
                    }
                } else {
                    if(!var39 || var38) {
                        break;
                    }

                    x = 0;
                    y = y - match_y + 1;
                    match_y = 0;
                    match_x = 0;
                }
            }
        } catch (Exception var36) {
            var36.printStackTrace();
        }

        return null;
    }

    public static Bitmap binarization(int[] windows) {
        RawScreen raw = ScreenUtil.rawScreenshot();
        Bitmap bmp = Bitmap.createBitmap(raw.width, raw.height, Config.ARGB_8888);
        byte[] screen = raw.rgba();

        for(int i = 0; i < raw.height; ++i) {
            for(int j = 0; j < raw.width; ++j) {
                int r = screen[(raw.width * i + j) * 4] & 255;
                int g = screen[(raw.width * i + j) * 4 + 1] & 255;
                int b = screen[(raw.width * i + j) * 4 + 2] & 255;
                int gray = (int)(((double)r * 77.0D + (double)g * 151.0D + (double)b * 28.0D) / 256.0D);
                gray = gray <= windows[1] && gray >= windows[0]?255:0;
                screen[(raw.width * i + j) * 4] = (byte)(gray & 255);
                screen[(raw.width * i + j) * 4 + 1] = (byte)(gray & 255);
                screen[(raw.width * i + j) * 4 + 2] = (byte)(gray & 255);
                screen[(raw.width * i + j) * 4 + 3] = -1;
            }
        }

        bmp.copyPixelsFromBuffer(ByteBuffer.wrap(screen));
        return bmp;
    }

    public static List<MatchPoint> findObject(String resourcePath, int[] windows) {
        RawScreen raw = ScreenUtil.rawScreenshot();
        Bitmap patternImage = null;

        try {
            patternImage = BitmapFactory.decodeStream(InstrumentationRegistry.getTargetContext().getAssets().open(resourcePath));
        } catch (IOException var26) {
            var26.printStackTrace();
        }

        ByteBuffer pbuffer = ByteBuffer.allocate(patternImage.getWidth() * patternImage.getHeight() * 4);
        if(raw.rotate) {
            Matrix pattern = new Matrix();
            pattern.postRotate(90.0F);
            patternImage = Bitmap.createBitmap(patternImage, 0, 0, patternImage.getWidth(), patternImage.getHeight(), pattern, true);
        }

        patternImage.copyPixelsToBuffer(pbuffer);
        byte[] var28 = pbuffer.array();
        byte[] screen = raw.rgba();
        int sw = raw.width;
        int sh = raw.height;
        int pw = patternImage.getWidth();
        int ph = patternImage.getHeight();
        int sm = 0;
        int pm = 0;
        int begin = 0;

        try {
            while(sm < sw * sh) {
                int e = screen[sm * 4] & 255;
                int sg = screen[sm * 4 + 1] & 255;
                int sb = screen[sm * 4 + 2] & 255;
                int pr = var28[pm * 4] & 255;
                int pg = var28[pm * 4 + 1] & 255;
                int pb = var28[pm * 4 + 2] & 255;
                int s_gray = (int)(((double)e * 77.0D + (double)sg * 151.0D + (double)sb * 28.0D) / 256.0D);
                int p_gray = (int)(((double)pr * 77.0D + (double)pg * 151.0D + (double)pb * 28.0D) / 256.0D);
                s_gray = s_gray <= windows[1] && s_gray >= windows[0]?255:0;
                p_gray = p_gray <= windows[1] && p_gray >= windows[0]?255:0;
                if(s_gray - p_gray == 0) {
                    if((pm + 1) % pw == 0) {
                        if(pm + 1 == pw * ph) {
                            int x = (sm + 1) % sw == 0?0:sm + 1 - (sm + 1) / sw * sw;
                            int y = (sm + 1) % sw == 0?(sm + 1) / sw - ph / 2:(sm + 1) / sw + 1;
                            MatchPoint match = null;
                            if(raw.rotate) {
                                Point p = new Point(sh - (y - 1 - ph / 2), sw - (x - 1 - pw / 2));
                                match = new MatchPoint(p, sh - y - 1, sw - x - 1, ph, pw);
                            } else {
                                match = new MatchPoint(new Point(x - 1 - pw / 2, y - 1 - ph / 2), x, y, pw, ph);
                            }

                            return Arrays.asList(new MatchPoint[]{match});
                        }

                        if((double)(ph - (pm + 1) / pw) > (double)sh - Math.ceil((double)((sm + 1) / sw))) {
                            return null;
                        }

                        sm = sm + sw - pw + 1;
                        ++pm;
                    } else {
                        if(pm == 0) {
                            begin = sm;
                        }

                        ++sm;
                        ++pm;
                    }
                } else {
                    if(pm == 0) {
                        ++sm;
                    } else {
                        sm = begin + 1;
                    }

                    pm = 0;
                }
            }
        } catch (Exception var27) {
            var27.printStackTrace();
        }

        return null;
    }

    public static List<MatchPoint> findObject(RectScreen rs) {
        RawScreen raw = ScreenUtil.rawScreenshot();
        byte[] patternBuffer = rs.raw;
        byte[] screenBuffer = raw.rgba();
        int sw = raw.width;
        int sh = raw.height;
        int pw = rs.width;
        int ph = rs.height;
        int match_x = 0;
        int match_y = 0;
        int x = 0;
        int y = 0;
        int[][] keys = new int[][]{{0, 0, 255, 255, 255}, {1, 0, 0, 0, 0}};

        int sr;
        int sg;
        int sb;
        for(int e = 0; e < ph; ++e) {
            for(int h_avail = 0; h_avail < pw; ++h_avail) {
                int p_skip = patternBuffer[(e * pw + h_avail) * 4] & 255;
                sr = patternBuffer[(e * pw + h_avail) * 4 + 1] & 255;
                sg = patternBuffer[(e * pw + h_avail) * 4 + 2] & 255;
                sb = patternBuffer[(e * pw + h_avail) * 4 + 3] & 255;
                if(sb == 255) {
                    if(p_skip < keys[0][2] && sr < keys[0][3] && sg < keys[0][4]) {
                        keys[0][0] = h_avail;
                        keys[0][1] = e;
                        keys[0][2] = p_skip;
                        keys[0][3] = sr;
                        keys[0][4] = sg;
                    }

                    if(p_skip > keys[1][2] && sr > keys[1][3] && sg > keys[1][4]) {
                        keys[1][0] = h_avail;
                        keys[1][1] = e;
                        keys[1][2] = p_skip;
                        keys[1][3] = sr;
                        keys[1][4] = sg;
                    }
                }
            }
        }

        try {
            while(y < sh) {
                boolean var34 = sw - x >= pw - match_x;
                boolean var35 = sh - y >= ph - match_y;
                boolean var36 = x - match_x + pw < sw;
                if(var34 && var35) {
                    sr = screenBuffer[(sw * y + x) * 4] & 255;
                    sg = screenBuffer[(sw * y + x) * 4 + 1] & 255;
                    sb = screenBuffer[(sw * y + x) * 4 + 2] & 255;
                    int pr = patternBuffer[(pw * match_y + match_x) * 4] & 255;
                    int pg = patternBuffer[(pw * match_y + match_x) * 4 + 1] & 255;
                    int pb = patternBuffer[(pw * match_y + match_x) * 4 + 2] & 255;
                    int pa = patternBuffer[(pw * match_y + match_x) * 4 + 3] & 255;
                    boolean equals = false;
                    if(pa < 255) {
                        equals = true;
                    } else {
                        equals = sr - pr <= RGB_RANGE && sr - pr >= -RGB_RANGE && sg - pg <= RGB_RANGE && sg - pg >= -RGB_RANGE && sb - pb <= RGB_RANGE && sb - pb >= -RGB_RANGE;
                    }

                    int nr;
                    int ng;
                    int nb;
                    int l;
                    int r;
                    if(equals) {
                        if(match_x == pw - 1) {
                            if(match_y == ph - 1) {
                                return Arrays.asList(new MatchPoint[]{new MatchPoint(new Point(x - pw / 2, y - ph / 2), x, y, pw, ph)});
                            }

                            match_x = 0;
                            ++match_y;
                            x = x - pw + 1;
                            ++y;
                        } else {
                            if(match_x == match_y && match_x == 0) {
                                nr = screenBuffer[((y + keys[0][1]) * sw + x + keys[0][0]) * 4] & 255;
                                ng = screenBuffer[((y + keys[0][1]) * sw + x + keys[0][0]) * 4 + 1] & 255;
                                nb = screenBuffer[((y + keys[0][1]) * sw + x + keys[0][0]) * 4 + 2] & 255;
                                int var37 = screenBuffer[((y + keys[1][1]) * sw + x + keys[1][0]) * 4] & 255;
                                l = screenBuffer[((y + keys[1][1]) * sw + x + keys[1][0]) * 4 + 1] & 255;
                                r = screenBuffer[((y + keys[1][1]) * sw + x + keys[1][0]) * 4 + 2] & 255;
                                boolean var38 = nr - keys[0][2] <= RGB_RANGE && nr - keys[0][2] >= -RGB_RANGE && ng - keys[0][3] <= RGB_RANGE && ng - keys[0][3] >= -RGB_RANGE && nb - keys[0][4] <= RGB_RANGE && nb - keys[0][4] >= -RGB_RANGE;
                                if(!var38) {
                                    ++x;
                                    match_y = 0;
                                    match_x = 0;
                                    continue;
                                }

                                if(var37 - keys[1][2] > RGB_RANGE || var37 - keys[1][2] < -RGB_RANGE || l - keys[1][3] > RGB_RANGE || l - keys[1][3] < -RGB_RANGE || r - keys[1][4] > RGB_RANGE || r - keys[1][4] < -RGB_RANGE) {
                                    ++x;
                                    match_y = 0;
                                    match_x = 0;
                                    continue;
                                }
                            }

                            ++x;
                            ++match_x;
                        }
                    } else if(!var36) {
                        x = 0;
                        y = y - match_y + 1;
                        match_y = 0;
                        match_x = 0;
                    } else {
                        nr = screenBuffer[(sw * (y - match_y) + x - match_x + pw) * 4] & 255;
                        ng = screenBuffer[(sw * (y - match_y) + x - match_x + pw) * 4 + 1] & 255;
                        nb = screenBuffer[(sw * (y - match_y) + x - match_x + pw) * 4 + 2] & 255;
                        boolean found = false;

                        for(l = pw - 1; l >= 0; --l) {
                            r = patternBuffer[l * 4] & 255;
                            int g = patternBuffer[l * 4 + 1] & 255;
                            int b = patternBuffer[l * 4 + 2] & 255;
                            int a = patternBuffer[l * 4 + 3] & 255;
                            if(a < 255) {
                                found = true;
                            } else {
                                found = nr - r <= RGB_RANGE && nr - r >= -RGB_RANGE && ng - g <= RGB_RANGE && ng - g >= -RGB_RANGE && nb - b <= RGB_RANGE && nb - b >= -RGB_RANGE;
                            }

                            if(found) {
                                found = true;
                                x = x - match_x + pw - l;
                                y -= match_y;
                                match_y = 0;
                                match_x = 0;
                                break;
                            }
                        }

                        if(!found) {
                            x = x - match_x + pw + 1;
                            y -= match_y;
                            match_y = 0;
                            match_x = 0;
                        }
                    }
                } else {
                    if(!var35 || var34) {
                        break;
                    }

                    x = 0;
                    y = y - match_y + 1;
                    match_y = 0;
                    match_x = 0;
                }
            }
        } catch (Exception var33) {
            var33.printStackTrace();
        }

        return null;
    }

    public static List<MatchPoint> findObject(String resourcePath, int threads) {
        RawScreen raw = ScreenUtil.rawScreenshot();
        Bitmap pattern = null;

        try {
            pattern = BitmapFactory.decodeStream(InstrumentationRegistry.getTargetContext().getAssets().open(resourcePath));
        } catch (IOException var21) {
            var21.printStackTrace();
        }

        ByteBuffer pbuffer = ByteBuffer.allocate(pattern.getWidth() * pattern.getHeight() * 4);
//        final boolean rotation = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).getDisplayRotation() != 0;
//        if(rotation) {
//            Matrix patternBuffer = new Matrix();
//            patternBuffer.postRotate(90.0F);
//            pattern = Bitmap.createBitmap(pattern, 0, 0, pattern.getWidth(), pattern.getHeight(), patternBuffer, true);
//        }

        pattern.copyPixelsToBuffer(pbuffer);
        final byte[] var22 = pbuffer.array();
        final byte[] screenBuffer = raw.rgba();
        final int sw = raw.width;
        final int sh = raw.height;
        final int pw = pattern.getWidth();
        final int ph = pattern.getHeight();
        final Object locker = new Object();
        List result = null;
        threads = sh / threads > ph?threads:(sh / ph > threads?threads:sh / ph);
        Thread[] threadArray = new Thread[threads];
        class Runner implements Runnable {
            private int starts;
            private MatchPoint match;

            public Runner(int starts) {
                this.starts = starts;
            }

            public List<MatchPoint> matches() {
                return this.match != null?Arrays.asList(new MatchPoint[]{this.match}):null;
            }

            public void run() {
                int match_x = 0;
                int match_y = 0;
                int x = 0;
                int y = this.starts;
                int[][] keys = new int[][]{{0, 0, 255, 255, 255}, {1, 0, 0, 0, 0}};

                int sr;
                int sg;
                int sb;
                for(int e = 0; e < ph; ++e) {
                    for(int h_avail = 0; h_avail < pw; ++h_avail) {
                        int p_skip = var22[(e * pw + h_avail) * 4] & 255;
                        sr = var22[(e * pw + h_avail) * 4 + 1] & 255;
                        sg = var22[(e * pw + h_avail) * 4 + 2] & 255;
                        sb = var22[(e * pw + h_avail) * 4 + 3] & 255;
                        if(sb == 255) {
                            if(p_skip < keys[0][2] && sr < keys[0][3] && sg < keys[0][4]) {
                                keys[0][0] = h_avail;
                                keys[0][1] = e;
                                keys[0][2] = p_skip;
                                keys[0][3] = sr;
                                keys[0][4] = sg;
                            }

                            if(p_skip > keys[1][2] && sr > keys[1][3] && sg > keys[1][4]) {
                                keys[1][0] = h_avail;
                                keys[1][1] = e;
                                keys[1][2] = p_skip;
                                keys[1][3] = sr;
                                keys[1][4] = sg;
                            }
                        }
                    }
                }

                try {
                    while(y < sh) {
                        boolean var28 = sw - x >= pw - match_x;
                        boolean var29 = sh - y >= ph - match_y;
                        boolean var30 = x - match_x + pw < sw;
                        if(var28 && var29) {
                            sr = screenBuffer[(sw * y + x) * 4] & 255;
                            sg = screenBuffer[(sw * y + x) * 4 + 1] & 255;
                            sb = screenBuffer[(sw * y + x) * 4 + 2] & 255;
                            int pr = var22[(pw * match_y + match_x) * 4] & 255;
                            int pg = var22[(pw * match_y + match_x) * 4 + 1] & 255;
                            int pb = var22[(pw * match_y + match_x) * 4 + 2] & 255;
                            int pa = var22[(pw * match_y + match_x) * 4 + 3] & 255;
                            boolean equals = false;
                            if(pa < 255) {
                                equals = true;
                            } else {
                                equals = sr - pr <= ImageMatch.RGB_RANGE && sr - pr >= -ImageMatch.RGB_RANGE && sg - pg <= ImageMatch.RGB_RANGE && sg - pg >= -ImageMatch.RGB_RANGE && sb - pb <= ImageMatch.RGB_RANGE && sb - pb >= -ImageMatch.RGB_RANGE;
                            }

                            int nr;
                            int ng;
                            int nb;
                            int l;
                            int r;
                            if(equals) {
                                if(match_x == pw - 1) {
                                    if(match_y == ph - 1) {
//                                        if(rotation) {
//                                            Point var31 = new Point(sh - (y - ph / 2), sw - (x - pw / 2));
//                                            this.match = new MatchPoint(var31, sh - y, sw - x, ph, pw);
//                                        } else {
                                            this.match = new MatchPoint(new Point(x - pw / 2, y - ph / 2), x, y, pw, ph);
//                                        }

                                        this.match = new MatchPoint(new Point(x - pw / 2, y - ph / 2), x, y, pw, ph);
                                        Object var32 = locker;
                                        synchronized(locker) {
                                            locker.notify();
                                        }
                                    } else {
                                        match_x = 0;
                                        ++match_y;
                                        x = x - pw + 1;
                                        ++y;
                                    }
                                } else {
                                    if(match_x == match_y && match_x == 0) {
                                        nr = screenBuffer[((y + keys[0][1]) * sw + x + keys[0][0]) * 4] & 255;
                                        ng = screenBuffer[((y + keys[0][1]) * sw + x + keys[0][0]) * 4 + 1] & 255;
                                        nb = screenBuffer[((y + keys[0][1]) * sw + x + keys[0][0]) * 4 + 2] & 255;
                                        int var33 = screenBuffer[((y + keys[1][1]) * sw + x + keys[1][0]) * 4] & 255;
                                        l = screenBuffer[((y + keys[1][1]) * sw + x + keys[1][0]) * 4 + 1] & 255;
                                        r = screenBuffer[((y + keys[1][1]) * sw + x + keys[1][0]) * 4 + 2] & 255;
                                        boolean var34 = nr - keys[0][2] <= ImageMatch.RGB_RANGE && nr - keys[0][2] >= -ImageMatch.RGB_RANGE && ng - keys[0][3] <= ImageMatch.RGB_RANGE && ng - keys[0][3] >= -ImageMatch.RGB_RANGE && nb - keys[0][4] <= ImageMatch.RGB_RANGE && nb - keys[0][4] >= -ImageMatch.RGB_RANGE;
                                        if(!var34) {
                                            ++x;
                                            match_y = 0;
                                            match_x = 0;
                                            continue;
                                        }

                                        if(var33 - keys[1][2] > ImageMatch.RGB_RANGE || var33 - keys[1][2] < -ImageMatch.RGB_RANGE || l - keys[1][3] > ImageMatch.RGB_RANGE || l - keys[1][3] < -ImageMatch.RGB_RANGE || r - keys[1][4] > ImageMatch.RGB_RANGE || r - keys[1][4] < -ImageMatch.RGB_RANGE) {
                                            ++x;
                                            match_y = 0;
                                            match_x = 0;
                                            continue;
                                        }
                                    }

                                    ++x;
                                    ++match_x;
                                }
                            } else if(!var30) {
                                x = 0;
                                y = y - match_y + 1;
                                match_y = 0;
                                match_x = 0;
                            } else {
                                nr = screenBuffer[(sw * (y - match_y) + x - match_x + pw) * 4] & 255;
                                ng = screenBuffer[(sw * (y - match_y) + x - match_x + pw) * 4 + 1] & 255;
                                nb = screenBuffer[(sw * (y - match_y) + x - match_x + pw) * 4 + 2] & 255;
                                boolean found = false;

                                for(l = pw - 1; l >= 0; --l) {
                                    r = var22[l * 4] & 255;
                                    int g = var22[l * 4 + 1] & 255;
                                    int b = var22[l * 4 + 2] & 255;
                                    int a = var22[l * 4 + 3] & 255;
                                    if(a < 255) {
                                        found = true;
                                    } else {
                                        found = nr - r <= ImageMatch.RGB_RANGE && nr - r >= -ImageMatch.RGB_RANGE && ng - g <= ImageMatch.RGB_RANGE && ng - g >= -ImageMatch.RGB_RANGE && nb - b <= ImageMatch.RGB_RANGE && nb - b >= -ImageMatch.RGB_RANGE;
                                    }

                                    if(found) {
                                        found = true;
                                        x = x - match_x + pw - l;
                                        y -= match_y;
                                        match_y = 0;
                                        match_x = 0;
                                        break;
                                    }
                                }

                                if(!found) {
                                    x = x - match_x + pw + 1;
                                    y -= match_y;
                                    match_y = 0;
                                    match_x = 0;
                                }
                            }
                        } else {
                            if(!var29 || var28) {
                                break;
                            }

                            x = 0;
                            y = y - match_y + 1;
                            match_y = 0;
                            match_x = 0;
                        }
                    }
                } catch (Exception var27) {
                    var27.printStackTrace();
                }

            }
        }

        Runner[] runnerArray = new Runner[threads];

        int i;
        for(i = 0; i < threads; ++i) {
            runnerArray[i] = new Runner(sw / threads * i);
            threadArray[i] = new Thread(runnerArray[i]);
            threadArray[i].start();
        }

        synchronized(locker) {
            try {
                locker.wait();
            } catch (InterruptedException var19) {
                var19.printStackTrace();
            }
        }

        for(i = 0; i < threads; ++i) {
            if(runnerArray[i].matches() == null) {
                threadArray[i].interrupt();
            } else {
                result = runnerArray[i].matches();
                System.out.println("thread " + i + " got result;");
            }
        }

        return result;
    }

    public static boolean isKeyboardOn() {
        RawScreen raw = ScreenUtil.rawScreenshot();

        for(int i = raw.height - 1; i >= raw.height - 330; --i) {
            int r = ScreenUtil.rawColor(raw, i)[0] & 255;
            int g = ScreenUtil.rawColor(raw, i)[1] & 255;
            int b = ScreenUtil.rawColor(raw, i)[2] & 255;
            if((r != 254 || g != 254 || b != 254) && (r > 40 || r < 20 || g > 40 || g < 20 || b > 40 || b < 20)) {
                return false;
            }
        }

        return true;
    }
}

