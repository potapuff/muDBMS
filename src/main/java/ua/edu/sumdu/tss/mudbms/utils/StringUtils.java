package ua.edu.sumdu.tss.mudbms.utils;

public class StringUtils {
    public static boolean isNullOrBlank(String param) {
        return param == null || param.trim().length() == 0;
    }
}
