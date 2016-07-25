package com.solium.pcd.util;

import java.util.regex.Pattern;

public class Util {

    private Util() {
    }

    /**
     * @param param
     * @param regex
     * @return boolean value to determine if regex matches
     */
    public static boolean regexMatches(String param, Pattern regex) {
        return regex.matcher(param).matches();
    }
}
