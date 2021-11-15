package ua.edu.sumdu.tss.mudbms.test_utils;

import org.apache.commons.lang3.RandomStringUtils;
import ua.edu.sumdu.tss.mudbms.core.Record;

public class Utils {

    public static Record randomRecord(int keyLength, int valueLength) {
        String key = RandomStringUtils.random(keyLength, true, true);
        String value = RandomStringUtils.random(valueLength, true, true);
        return new ua.edu.sumdu.tss.mudbms.core.Record(key, value);
    }

    public static Record randomRecord() {
        return randomRecord(32, 1000);
    }

    public static int getRandomNumber() {
        return (int) (Math.random() * Integer.MAX_VALUE);
    }

}
