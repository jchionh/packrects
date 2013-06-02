package com.appspot.packrects.engine;

public class RectUtils {

    public static final String[] COLORS = {
        "#DEADFA",
        "#C0C0C0",
        "#808080",
        "#BADF00",
        "#BF0000",
        "#800000",
        "#BFBF00",
        "#808000",
        "#00FF00",
        "#008000",
        "#00BFBF",
        "#008080",
        "#0000BF",
        "#000080",
        "#BF00BF",
        "#800080"
    };
    
    /**
     * given 2 basic rects, do they overlap?
     * @param a
     * @param b
     * @return
     */
    public static boolean doRectsOverlap(BasicRect a, BasicRect b) {
        int aMinX = a.x;
        int aMinY = a.y;
        int aMaxX = a.x + a.w;
        int aMaxY = a.y + a.h;

        int bMinX = b.x;
        int bMinY = b.y;
        int bMaxX = b.x + b.w;
        int bMaxY = b.y + b.h;

        if (aMaxX <= bMinX) {
            return false; // a is left of b
        }
        if (aMinX >= bMaxX) {
            return false; // a is right of b
        }
        if (aMaxY <= bMinY) {
            return false; // a is above b
        }
        if (aMinY >= bMaxY) {
            return false; // a is below b
        }
        return true; // boxes overlap
    }
    
}
