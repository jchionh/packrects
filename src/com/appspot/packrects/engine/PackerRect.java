package com.appspot.packrects.engine;

/**
 * Packer rect is the rect that will be packed by the packer
 * they can be rotated
 * @author jchionh
 *
 */
public class PackerRect extends BasicRect {
    
    private boolean placed = false;
    private PackerRect parent = null;
    private PackerRect rightRect = null;
    private PackerRect bottomRect = null;

    // ctor
    public PackerRect(int x, int y, int w, int h, int id) {
        super(x, y, w, h, id);
    }
    
    // accessor methods
    public boolean isPlaced() {
        return placed;
    }
    public PackerRect getParent() {
        return parent;
    }
    public PackerRect getRightRect() {
        return rightRect;
    }
    public PackerRect getBottomRect() {
        return bottomRect;
    }
    public void setPlaced(boolean truth) {
        placed = truth;
    }
    public void setParent(PackerRect rect) {
        parent = rect;
    }
    public void setRightRect(PackerRect rect) {
        rightRect = rect;
    }
    public void setBottomRect(PackerRect rect) {
        bottomRect = rect;
    }
    
    // transform methods
    public void rotate() {
        // rect will not be rotated if we already have stuff attached to us
        if (rightRect != null || bottomRect != null) {
            return;
        }
        // else we swap width and heights
        int swap = w;
        w = h;
        h = swap;
    }
    
    /**
     * rotate to an orientation where with is minimized
     */
    public void minimizeWidth() {
        if (w > h) {
            rotate();
        }
    }
    
    /**
     * rotate to an orientation where height is minimized
     */
    public void minimizeHeight() {
        if (h > w) {
            rotate();
        }
    }
    
    /**
     * attach rect to ourself
     * @param rect
     */
    public void attachRight(PackerRect rect) {
        rightRect = rect;
        rect.setParent(this);
        rect.x = x + w;
        rect.y = y;
    }
    
    public void attachBottom(PackerRect rect) {
        bottomRect = rect;
        rect.setParent(this);
        rect.x = x;
        rect.y = y + h;
    }
    
}
