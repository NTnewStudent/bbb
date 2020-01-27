package com.example.calculatorapplication.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LensManager implements Iterable<Lens> {

    List<Lens> data = new ArrayList<>();
    static LensManager manager = null;

    public void add(Lens lens) {
        data.add(lens);
    }

    private LensManager() {
    }

    public static LensManager getInstance() {
        if (manager == null) {
            return new LensManager();
        }
        return manager;
    }


    public Lens getLens(int index) {
        return data.get(index);
    }

    public int getSize() {
        return data.size();
    }

    @Override
    public Iterator<Lens> iterator() {
        return data.iterator();
    }
}
