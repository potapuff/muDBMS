package ua.edu.sumdu.tss.mudbms.core;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class CachedStorage extends Storage {

    static private final int MAX_CASHE_SIZE = 100;
    static private final Map<String, String> cache = Collections.synchronizedMap(new LinkedHashMap<String, String>(MAX_CASHE_SIZE));

    public static String read(String key) {

        String value = cache.get(key);
        if (value != null) {
            return value;
        }
        value = Storage.read(key);
        if (value != null) {
            cache.put(key, value);
        }
        return value;
    }

    public static void write(String key, String value) {
        cache.put(key, value);
        Storage.write(key, value);
    }

}