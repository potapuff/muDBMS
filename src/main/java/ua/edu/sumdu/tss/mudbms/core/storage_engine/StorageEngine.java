package ua.edu.sumdu.tss.mudbms.core.storage_engine;

public interface StorageEngine {

    String read(String key);

    void write(String key, String value);

}
