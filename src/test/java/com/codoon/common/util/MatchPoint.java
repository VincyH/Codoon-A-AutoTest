package com.codoon.common.util;

/**
 * Created by huangjingqing on 17/4/14.
 */

import android.graphics.Rect;

import java.util.Iterator;
import java.util.List;

public class MatchPoint implements Comparable<MatchPoint> {
    public final int rank;
    public final Point point;
    public Rect rect;

    public MatchPoint(Point point, List<Point> points, double scale) {
        this.point = point;
        this.rank = points.size();
        this.point.x = (int)((double)point.x * scale);
        this.point.y = (int)((double)point.y * scale);
        int left = point.x;
        int right = point.x;
        int top = point.y;
        int bottom = point.y;
        Iterator var9 = points.iterator();

        while(var9.hasNext()) {
            Point p = (Point)var9.next();
            p.x = (int)((double)p.x * scale);
            p.y = (int)((double)p.y * scale);
            if(p.x < left) {
                left = p.x;
            }

            if(p.x > right) {
                right = p.x;
            }

            if(p.y < top) {
                top = p.y;
            }

            if(p.y > bottom) {
                bottom = p.y;
            }
        }

        this.rect = new Rect(left, top, right, bottom);
    }

    public MatchPoint(Point point, int x, int y, int width, int height) {
        this.point = point;
        this.rank = 1000;
        this.rect = new Rect(x - width + 1, y - height + 1, x, y);
    }

    public int width() {
        return this.rect.right - this.rect.left;
    }

    public int height() {
        return this.rect.bottom - this.rect.top;
    }

    public int compareTo(MatchPoint o) {
        return o != null?o.rank - this.rank:1;
    }
}

