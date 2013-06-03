package com.appspot.packrects.engine;

public class AttachConfig {
    
    public enum AttachPos {
        RIGHT,
        BOTTOM
    }
    public enum MinimizeStrategy {
        WIDTH,
        HEIGHT
    }
    
    public PackerRect rect;
    public AttachPos attachPos;
    public MinimizeStrategy minimize;
    public int area;
    
    // ctor
    public AttachConfig (PackerRect rect, AttachPos attachPos, MinimizeStrategy minimize, int area) {
        this.rect = rect;
        this.attachPos = attachPos;
        this.minimize = minimize;
        this.area = area;
    }
}
