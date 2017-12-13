package fr.ip.model.util;

import java.util.Scanner;

public class StringUtil {
    public StringUtil() {}

    public static String removeWhiteSpace(String s) {
        String out = new String(s);
        if(out.contains(" "))
            out.join("", out.split(" "));
        if(out.contains("\t"))
            out.join("", out.split("\t"));
        return out;
    }

    public static boolean isEmpty(String s) {
        for(int i = 0; i < s.length(); i ++)
            if(s.charAt(i) != ' ' && s.charAt(i) != '\t' && s.charAt(i) != '\n')
                return false;

        return true;
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
