package ua.edu.sumdu.tss.mudbms.utils;

public class ArrayUtils {

    public static int readInt(byte[] bytes, int pos) {
        int value = ((bytes[pos++] & 0xFF) << 24);
        value += ((bytes[pos++] & 0xFF) << 16);
        value += ((bytes[pos++] & 0xFF) << 8);
        value += (bytes[pos] & 0xFF);
        return value;
    }

    public static void writeInt(byte[] bytes, int pos, int value) {
        bytes[pos++] = (byte) ((value >>> 24) & 0xFF);
        bytes[pos++] = (byte) ((value >>> 16) & 0xFF);
        bytes[pos++] = (byte) ((value >>> 8) & 0xFF);
        bytes[pos] = (byte) (value & 0xFF);
    }

    public static boolean readBoolean(byte[] bytes, int pos) {
        return bytes[pos] != 0;
    }

    public static void writeBoolean(byte[] bytes, int pos, boolean value) {
        bytes[pos] = (byte) (value ? 1 : 0);
    }
}
