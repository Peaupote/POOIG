package fr.ip.model.util;

import java.util.Scanner;

public class StringUtil {
    public StringUtil() {}

    public static String removeWhiteSpace(String str) {
        if(str.contains(" "))
            str.join("", str.split(" "));
        if(str.contains("\t"))
            str.join("", str.split(" "));
        return str;
    }

    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        Scanner sc = new Scanner(s.trim());
        if(!sc.hasNextInt(radix))
            return false;
        sc.nextInt(radix);
        return !sc.hasNext();
    }
}
