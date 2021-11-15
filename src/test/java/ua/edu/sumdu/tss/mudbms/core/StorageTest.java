package ua.edu.sumdu.tss.mudbms.core;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.sumdu.tss.mudbms.core.engine.Engine;
import ua.edu.sumdu.tss.mudbms.core.engine.Storage;
import ua.edu.sumdu.tss.mudbms.test_utils.Utils;

import static org.junit.jupiter.api.Assertions.*;


class StorageTest {

    private Storage storage;

    @BeforeEach
    void storageClean() {
        storage = Engine.getInstance();
        storage.getStoragePath().toFile().delete();
    }

    @Test
    void readAndWriteTest() {
        Record[] array = new Record[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = Utils.randomRecord(10, 10);
            storage.write(array[i].key, array[i].value);
        }
        for (int i = 0; i < array.length; i++) {
            String value = storage.read(array[i].key);
            assertEquals(array[i].value, value);
        }
        for (int i = array.length - 1; i >= 0; i--) {
            String value = storage.read(array[i].key);
            assertEquals(array[i].value, value);
        }
    }

    @Test
    void readUnfoundedTest() {
        for (int i = 0; i < 10; i++) {
            Record record = Utils.randomRecord(32, 32);
            storage.write(record.key, record.value);
        }
        String value = storage.read(RandomStringUtils.random(32));
        assertNull(value);
    }

    @Test
    void inplaceWriteTest() {
        Record[] array = new Record[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = Utils.randomRecord(10, 100);
            storage.write(array[i].key, array[i].value);
        }
        long oldSize = storage.getStoragePath().toFile().length();
        for (int i = 0; i < array.length; i++) {
            array[i].value = RandomStringUtils.random(50, true, true);
            storage.write(array[i].key, array[i].value);
        }
        long newSize = storage.getStoragePath().toFile().length();
        assertEquals(oldSize, newSize);

        for (int i = 0; i < array.length; i++) {
            String value = storage.read(array[i].key);
            assertEquals(array[i].value, value);
        }
    }

    @Test
    void deleteAndWriteWriteTest() {
        Record[] array = new Record[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = Utils.randomRecord(10, 50);
            storage.write(array[i].key, array[i].value);
        }
        long oldSize = storage.getStoragePath().toFile().length();
        for (int i = 0; i < array.length; i++) {
            array[i].value = RandomStringUtils.random(100, true, true);
            storage.write(array[i].key, array[i].value);
        }
        long newSize = storage.getStoragePath().toFile().length();
        assertNotEquals(oldSize, newSize);
        for (int i = 0; i < array.length; i++) {
            String value = storage.read(array[i].key);
            assertEquals(array[i].value, value);
        }
    }
}