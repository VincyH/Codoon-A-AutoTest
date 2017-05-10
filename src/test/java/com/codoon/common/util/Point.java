package com.codoon.common.util;

/**
 * Created by huangjingqing on 17/4/14.
 */


public class Point {
    public int x;
    public int y;

    public Point(String x, String y) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object o) {
        if(o != null) {
            if(o instanceof Point) {
                Point p = (Point)o;
                return p.x == this.x && p.y == this.y;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
