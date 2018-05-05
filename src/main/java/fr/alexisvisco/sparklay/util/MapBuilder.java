package fr.alexisvisco.sparklay.util;

import java.util.HashMap;

public class MapBuilder<T, U> extends HashMap<T, U> {

    public MapBuilder<T, U> set(T key, U value) {
        super.put(key, value);
        return this;
    }
}
