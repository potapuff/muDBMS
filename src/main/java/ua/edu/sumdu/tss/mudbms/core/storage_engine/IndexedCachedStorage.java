package ua.edu.sumdu.tss.mudbms.core.storage_engine;

import ua.edu.sumdu.tss.mudbms.core.Record;
import ua.edu.sumdu.tss.mudbms.utils.StringUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class IndexedCachedStorage extends CachedStorage {

    private final Map<String, String> cache;
    private final CachedStorage index;

    public IndexedCachedStorage(String dataFile) {
        this(dataFile, 100);
    }

    public IndexedCachedStorage(String dataFile, int maxCacheSize) {
        super(dataFile);
        cache = Collections.synchronizedMap(new LinkedHashMap<String, String>(maxCacheSize));
        //TODO: fill index
        index = new CachedStorage(dataFile + ".idx");
    }

    public String read(String key) {

        String value = cache.get(key);
        if (value != null) {
            return value;
        }
        String address = index.read(key);
        if (address != null) {
            Record rec = super.byAddress(StringUtils.readInt(address));
            if (rec == null) {
                return null;
            }
            value = rec.getValue();
        }

        if (value != null) {
            cache.put(key, value);
        }
        return value;
    }

    public void write(String key, String value) {
        cache.put(key, value);
        String address = StringUtils.writeInt(super.writeRecord(new Record(key, value)));
        index.write(key, address);
    }

}