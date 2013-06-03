package com.appspot.packrects.engine;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * packer is a class that takes in a dataset of rects and packs
 * them into the smallest area possible.
 * @author jchionh
 *
 */
public class Packer {
    
    
    private List<RectData> dataSet = new LinkedList<RectData>();
    private PackerRectAreaComparatorDsc areaDsc = new PackerRectAreaComparatorDsc();
    private RectContainer container = new RectContainer();
    
    
    public void setDataSet(List<RectData> dataSet) {
        this.dataSet = dataSet;
    }
    
    public BasicRect[] pack() {
        List<PackerRect> packedRects = new LinkedList<PackerRect>();
        // first create our packer rects by traversing the dataSet
        for (RectData data : dataSet) {
            PackerRect packerRect = new PackerRect(0, 0, data.w, data.h, data.id);
            // always start by minimizing width
            packerRect.minimizeWidth();
            packedRects.add(packerRect);
        }
        
        // sort our list
        Collections.sort(packedRects, areaDsc);
        
        container = new RectContainer();
        // add rect to the container for packing
        int count = 0;
        for (PackerRect packerRect : packedRects) {
            container.addRect(packerRect);
            count++;
        }
        
        // now that we've packed all into the container
        // let's create an array to return the results
        BasicRect[] result = new BasicRect[count + 1];
        
        // first position is the container
        result[0] = container.getData();
        int index = 1;
        for (PackerRect packerRect : packedRects) {
            result[index] = packerRect.getData();
            index++;
        }
        return result;
    }

}
