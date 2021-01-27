package data.utils;

import com.sun.xml.internal.ws.util.StringUtils;

public class CharUtils {

    public static char decapitalizeChar(Character letter) {
        return StringUtils.decapitalize(letter.toString()).charAt(0);
    }

}
