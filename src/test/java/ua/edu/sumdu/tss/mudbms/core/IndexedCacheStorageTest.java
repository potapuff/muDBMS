package ua.edu.sumdu.tss.mudbms.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.sumdu.tss.mudbms.core.storage_engine.IndexedCachedStorage;
import ua.edu.sumdu.tss.mudbms.test_utils.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IndexedCacheStorageTest {

    private IndexedCachedStorage storage;

    @BeforeEach
    void storageClean() {
        storage = new IndexedCachedStorage("idxStorage.data");
        storage.getStoragePath().toFile().delete();

    }

    @Test
    void readAndWriteTest() {
        Record[] array = new Record[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = Utils.randomRecord(10, 10);
            storage.write(array[i].key, array[i].value);
        }
        for (int i = 0; i < array.length; i++) {
            System.out.println(i);
            String value = storage.read(array[i].key);
            assertEquals(array[i].value, value);
        }
        for (int i = array.length - 1; i >= 0; i--) {
            System.out.println(i);
            String value = storage.read(array[i].key);
            assertEquals(array[i].value, value);
        }
    }
}
