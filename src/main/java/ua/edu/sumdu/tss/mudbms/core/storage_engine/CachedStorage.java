package ua.edu.sumdu.tss.mudbms.core.storage_engine;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class CachedStorage extends ImmediateDiskStorage implements StorageEngine {

    private final Map<String, String> cache;

    public CachedStorage(String dataFile) {
        super(dataFile);
        cache = Collections.synchronizedMap(new LinkedHashMap<String, String>(100));
    }

    public CachedStorage(String dataFile, int maxCacheSize) {
        super(dataFile);
        cache = Collections.synchronizedMap(new LinkedHashMap<String, String>(maxCacheSize));
    }

    public String read(String key) {

        String value = cache.get(key);
        if (value != null) {
            return value;
        }
        value = super.read(key);
        if (value != null) {
            cache.put(key, value);
        }
        return value;
    }

    public void write(String key, String value) {
        cache.put(key, value);
        super.write(key, value);
    }

}