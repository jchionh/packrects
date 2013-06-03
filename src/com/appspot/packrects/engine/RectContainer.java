package com.appspot.packrects.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * container is a container rectangle with the bounding box
 * the encompass all boxes
 * @author jchionh
 *
 */
public class RectContainer extends BasicRect {
    
    public static final int CONTAINER_ID = -1;
    private static final int INITIAL_ARRAY_SIZE = 64;
    private static final AttachConfigComparatorAsc areaAsc = new AttachConfigComparatorAsc();
    

    // here's the list of already packed rects
    private List<PackerRect> packedRects = new ArrayList<PackerRect>(INITIAL_ARRAY_SIZE);
    
    // ctor
    public RectContainer() {
        super(0, 0, 0, 0, CONTAINER_ID, "#00000000");
        packedRects.clear();
    }
    
    /**
     * adding the rect into the container, transforming the rect if necessary
     * so as to put it in a configuration that takes up the smallest space 
     * possible in the container.
     * @param packerRect
     */
    public void addRect(PackerRect packerRect) {
        // simple case, if our packed rects is empty just add
        if (packedRects.size() == 0) {
            packerRect.setPlaced(true);
            packerRect.x = 0;
            packerRect.y = 0;
            packerRect.setRightRect(null);
            packerRect.setBottomRect(null);
            packedRects.add(packerRect);
            calcBoundingBox();
            return;
        }
        // new a list of possible configs
        List<AttachConfig> possibles = new LinkedList<AttachConfig>();
        // else we'll iterate all and try
        int length = packedRects.size();
        for (int i =0; i < length; ++i) {
            PackerRect curRect = packedRects.get(i);
            // try the rights
            if (curRect.getRightRect() == null) {
                // we can attach right, minimize width first
                packerRect.minimizeWidth();
                curRect.attachRight(packerRect);
                packedRects.add(packerRect);
                calcBoundingBox();
                
                int area = getArea();
                // we only consider this a valid try if it does not collide 
                // with anything
                if (!doesCollide(packerRect)) {
                    possibles.add(new AttachConfig(curRect, AttachConfig.AttachPos.RIGHT, AttachConfig.MinimizeStrategy.WIDTH, area));    
                }
                
                // remove and check the min height rotation
                removeLast();
                
                packerRect.minimizeHeight();
                curRect.attachRight(packerRect);
                packedRects.add(packerRect);
                calcBoundingBox();
                
                
                area = getArea();
                // we only consider this a valid try if it does not collide 
                // with anything
                if (!doesCollide(packerRect)) {
                    possibles.add(new AttachConfig(curRect, AttachConfig.AttachPos.RIGHT, AttachConfig.MinimizeStrategy.HEIGHT, area));
                }
                
                // remove and check next
                removeLast();
            }
            
            // try the bottom rects
            if (curRect .getBottomRect() == null) {
                // we can attach right, minize width first
                packerRect.minimizeWidth();
                curRect.attachBottom(packerRect);
                packedRects.add(packerRect);
                calcBoundingBox();
                
                int area = getArea();
                // we only consider this a valid try if it does not collide 
                // with anything
                if (!doesCollide(packerRect)) {
                    possibles.add(new AttachConfig(curRect, AttachConfig.AttachPos.BOTTOM, AttachConfig.MinimizeStrategy.WIDTH, area));
                }
                
                // remove and check the min height rotation
                removeLast();
                
                packerRect.minimizeHeight();
                curRect.attachBottom(packerRect);
                packedRects.add(packerRect);
                calcBoundingBox();
                area = getArea();
                
                // we only consider this a valid try if it does not collide 
                // with anything
                if (!doesCollide(packerRect)) {
                    possibles.add(new AttachConfig(curRect, AttachConfig.AttachPos.BOTTOM, AttachConfig.MinimizeStrategy.HEIGHT, area));
                }
                
                // remove and check next
                removeLast();
                
            }
        }
        
        // now we have an array of possibles, sort to find the smallest area
        Collections.sort(possibles, areaAsc);
        AttachConfig bestAttachment = possibles.get(0);
        
        switch (bestAttachment.minimize) {
            case WIDTH:
                packerRect.minimizeWidth();
                break;
            case HEIGHT:
                packerRect.minimizeHeight();
                break;
        }
        
        switch (bestAttachment.attachPos) {
            case RIGHT:
                bestAttachment.rect.attachRight(packerRect);
                break;
            case BOTTOM:
                bestAttachment.rect.attachBottom(packerRect);
                break;
        }
        packedRects.add(packerRect);
        calcBoundingBox();
    }
    
    /**
     * iterate though all our packed rects, does this collide with any of them?
     * @param packerRect
     * @return
     */
    public boolean doesCollide(PackerRect packerRect) {
        for (PackerRect rect : packedRects) {
            if (rect == packerRect) {
                continue;
            }
            if (RectUtils.doRectsOverlap(rect, packerRect)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * iterate and add up all the areas of our rects that we contain
     */
    public int calcTotalRectArea() {
        int totalArea = 0;
        // iterate all our rects and find the maximum extents
        for (PackerRect curRect : packedRects) {
            totalArea += curRect.getArea();
        }
        return totalArea;
    }
    
    /**
     * remove the last packed rect from the container
     */
    private void removeLast() {
        int length = packedRects.size();
        if (length == 0) {
            return;
        }
        int lastIndex = length - 1;
        PackerRect toRemove = packedRects.get(lastIndex);
        PackerRect parent = toRemove.getParent();
        if (parent != null) {
            // remove the parent's links
            if (parent.getRightRect() == toRemove) {
                parent.setRightRect(null);
            }
            if (parent.getBottomRect() == toRemove) {
                parent.setBottomRect(null);
            }
            toRemove.setParent(null);
            toRemove.setPlaced(false);
            toRemove.x = 0;
            toRemove.y = 0;
        }
        // remove from list
        packedRects.remove(lastIndex);
        // then re-calculate bounding box
        calcBoundingBox();
    }
    
    /**
     * calcualte our bounding box
     */
    private void calcBoundingBox() {
        int maxH = 0;
        int maxW = 0;
        // iterate all and expand the maxH and maxW
        for (PackerRect curRect : packedRects) {
            int curExtentW = curRect.x + curRect.w;
            // check for max width
            if ( curExtentW > maxW ) {
                maxW = curExtentW;
            }
            
            // now check for height as well
            int curExtentH = curRect.y + curRect.h;
            if ( curExtentH > maxH) {
                maxH = curExtentH;
            }
        }
        // once we iterated all, we'll have our width and height bounding box
        w = maxW;
        h = maxH;
    }
    

}
