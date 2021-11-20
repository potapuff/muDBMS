package ua.edu.sumdu.tss.mudbms.utils;

public class StringUtils {
    public static boolean isNullOrBlank(String param) {
        return param == null || param.trim().length() == 0;
    }

    public static int readInt(String param) {
        return ArrayUtils.readInt(param.getBytes(), 0);
    }

    public static String writeInt(int input) {
        byte[] bytes = new byte[4];
        ArrayUtils.writeInt(bytes, 0, input);
        return new String(bytes);
    }
}
