package ua.edu.sumdu.tss.mudbms.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.edu.sumdu.tss.mudbms.core.Storage;
import ua.edu.sumdu.tss.mudbms.test_utils.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayUtilsTest {

    @Test
    void readAndWriteInt() {
        byte[] test = new byte[20];
        byte beforeStartIdx = 0;
        byte afterEndIdx = 1 + Storage.INT_SIZE * 2;
        byte beforeStart = test[beforeStartIdx];
        byte afterEnd = test[afterEndIdx];
        int t1 = Utils.getRandomNumber();
        int t2 = Utils.getRandomNumber();
        ArrayUtils.writeInt(test, 1, t1);
        ArrayUtils.writeInt(test, 1 + Storage.INT_SIZE, t2);

        Assertions.assertEquals(beforeStart, test[beforeStartIdx]);
        Assertions.assertEquals(afterEnd, test[afterEndIdx]);
        Assertions.assertEquals(t1, ArrayUtils.readInt(test, 1));
        Assertions.assertEquals(t2, ArrayUtils.readInt(test, 1 + Storage.INT_SIZE));
    }

    @Test
    void readAndWriteBoolean() {
        byte[] test = new byte[20];
        boolean[] array = new boolean[15];
        int beforeStartIdx = 3;
        int afterEndIdx = beforeStartIdx + Storage.BOOL_SIZE * array.length + 1;
        byte beforeStart = test[beforeStartIdx];
        byte afterEnd = test[afterEndIdx];
        for (int i = 0; i < array.length; i++) {
            array[i] = Math.random() > 0.5;
            ArrayUtils.writeBoolean(test, beforeStartIdx + 1 + Storage.BOOL_SIZE * i, array[i]);
        }
        Assertions.assertEquals(beforeStart, test[beforeStartIdx]);
        Assertions.assertEquals(afterEnd, test[afterEndIdx]);
        for (int i = 0; i < array.length; i++) {
            assertEquals(array[i], ArrayUtils.readBoolean(test, beforeStartIdx + 1 + Storage.BOOL_SIZE * i));
        }
    }


}