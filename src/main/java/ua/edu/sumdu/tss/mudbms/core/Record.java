package ua.edu.sumdu.tss.mudbms.core;

import ua.edu.sumdu.tss.mudbms.core.storage_engine.ImmediateDiskStorage;
import ua.edu.sumdu.tss.mudbms.utils.ArrayUtils;
import ua.edu.sumdu.tss.mudbms.utils.StringUtils;

import java.io.Serializable;

public class Record implements Serializable {

    private static final int MAX_KEY_LENGTH = 32;

    boolean isValid;
    String key;
    String value;

    public Record(String key, String value) {
        this(true, key, value);
    }

    private Record(boolean isValid, String key, String value) {
        if (StringUtils.isNullOrBlank(key)) {
            throw new IllegalArgumentException("Key can't by empty");
        }
        if (key.length() > Record.MAX_KEY_LENGTH) {
            throw new IllegalArgumentException("Currently key length limited to " + Record.MAX_KEY_LENGTH + "char");
        }
        this.isValid = isValid;
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Record fromBytes(byte[] bytes) {
        boolean isValid = ArrayUtils.readBoolean(bytes, 0);
        int keyLength = ArrayUtils.readInt(bytes, ImmediateDiskStorage.BOOL_SIZE);
        int dataOffset = ImmediateDiskStorage.BOOL_SIZE + ImmediateDiskStorage.INT_SIZE;
        byte[] keyArray = new byte[keyLength];
        System.arraycopy(bytes, dataOffset, keyArray, 0, keyArray.length);
        int unusedTail = 0;
        for (int i = bytes.length - 1; i > 0; i--) {
            if (bytes[i] != 0) {
                break;
            }
            unusedTail += 1;
        }
        int predictedValueLength = Math.max(bytes.length - keyLength - dataOffset - unusedTail, 0);
        byte[] valueArray = new byte[predictedValueLength];
        System.arraycopy(bytes, dataOffset + keyArray.length, valueArray, 0, predictedValueLength);
        return new Record(isValid, new String(keyArray), new String(valueArray));
    }

    public String toString() {
        return String.format("Record[isValid=%b, key=%s, value=%s]", isValid, key, value);
    }

    public byte[] getBytes() {
        int dataOffset = ImmediateDiskStorage.INT_SIZE + ImmediateDiskStorage.BOOL_SIZE;
        byte[] output = new byte[key.length() + value.length() + ImmediateDiskStorage.INT_SIZE + ImmediateDiskStorage.BOOL_SIZE];
        ArrayUtils.writeBoolean(output, 0, isValid);
        ArrayUtils.writeInt(output, ImmediateDiskStorage.BOOL_SIZE, key.length());
        System.arraycopy(key.getBytes(), 0, output, dataOffset, key.length());
        System.arraycopy(value.getBytes(), 0, output, key.length() + dataOffset, value.length());
        return output;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Record compare = (Record) other;
        return key.equals(compare.key) && value.equals(compare.value);
    }
}
