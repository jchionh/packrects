package com.appspot.packrects.engine;

import java.util.Comparator;

public class PackerRectAreaComparatorDsc implements Comparator<PackerRect> {

    @Override
    public int compare(PackerRect a, PackerRect b) {
        return b.getArea() - a.getArea();
    }

}
