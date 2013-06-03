package com.appspot.packrects.engine;

import java.util.Comparator;

public class AttachConfigComparatorAsc implements Comparator<AttachConfig> {
    @Override
    public int compare(AttachConfig a, AttachConfig b) {
        return a.area - b.area;
    }
}
