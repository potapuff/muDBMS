package ua.edu.sumdu.tss.mudbms.core;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.sumdu.tss.mudbms.test_utils.Utils;

import static org.junit.jupiter.api.Assertions.*;


class StorageTest {

    @BeforeEach
    void storageClean() {
        Storage.getStoragePath().toFile().delete();
    }

    @Test
    void readAndWriteTest() {
        Record[] array = new Record[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = Utils.randomRecord(10, 10);
            Storage.write(array[i].key, array[i].value);
        }
        for (int i = 0; i < array.length; i++) {
            String value = Storage.read(array[i].key);
            assertEquals(array[i].value, value);
        }
        for (int i = array.length - 1; i >= 0; i--) {
            String value = Storage.read(array[i].key);
            assertEquals(array[i].value, value);
        }
    }

    @Test
    void readUnfoundedTest() {
        for (int i = 0; i < 10; i++) {
            Record record = Utils.randomRecord(32, 32);
            Storage.write(record.key, record.value);
        }
        String value = Storage.read(RandomStringUtils.random(32));
        assertNull(value);
    }

    @Test
    void inplaceWriteTest() {
        Record[] array = new Record[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = Utils.randomRecord(10, 100);
            Storage.write(array[i].key, array[i].value);
        }
        long oldSize = Storage.getStoragePath().toFile().length();
        for (int i = 0; i < array.length; i++) {
            array[i].value = RandomStringUtils.random(50, true, true);
            Storage.write(array[i].key, array[i].value);
        }
        long newSize = Storage.getStoragePath().toFile().length();
        assertEquals(oldSize, newSize);

        for (int i = 0; i < array.length; i++) {
            String value = Storage.read(array[i].key);
            assertEquals(array[i].value, value);
        }
    }

    @Test
    void deleteAndWriteWriteTest() {
        Record[] array = new Record[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = Utils.randomRecord(10, 50);
            Storage.write(array[i].key, array[i].value);
        }
        long oldSize = Storage.getStoragePath().toFile().length();
        for (int i = 0; i < array.length; i++) {
            array[i].value = RandomStringUtils.random(100, true, true);
            Storage.write(array[i].key, array[i].value);
        }
        long newSize = Storage.getStoragePath().toFile().length();
        assertNotEquals(oldSize, newSize);
        for (int i = 0; i < array.length; i++) {
            String value = Storage.read(array[i].key);
            assertEquals(array[i].value, value);
        }
    }
}