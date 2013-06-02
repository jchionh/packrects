package com.appspot.packrects.engine;

public class BasicRect {
    
    public int x;
    public int y;
    public int w;
    public int h;
    public String color;
    
    // ctor
    public BasicRect(int x, int y, int w, int h) {
        String color = RectUtils.COLORS[(int)Math.floor(Math.random() * RectUtils.COLORS.length)];
        init(x, y, w, h, color);
    }
    
    // ctor
    public BasicRect(int x, int y, int w, int h, String color) {
        init(x, y, w, h, color);
    }
    
    private void init(int x, int y, int w, int h, String color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
    }
    
    /**
     * return the area of the rect
     * @return
     */
    public int getArea() {
        return w * h;
    }

}
