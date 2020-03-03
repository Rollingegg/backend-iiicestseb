package group.iiicestseb.backend.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author jh
 * @date 2020/3/3
 */
public class StringUtil {

    private static final Map<Character, Character> ACCENTS = new TreeMap<Character, Character>() {
        {
            put('À', 'A');
            put('Â', 'A');
            put('à', 'a');
            put('â', 'a');
            put('ą', 'a');
            put('ç', 'c');
            put('È', 'E');
            put('É', 'E');
            put('Ê', 'E');
            put('Ë', 'E');
            put('è', 'e');
            put('é', 'e');
            put('ê', 'e');
            put('ë', 'e');
            put('Î', 'I');
            put('Ï', 'I');
            put('î', 'i');
            put('ï', 'i');
            put('Ô', 'O');
            put('ô', 'o');
            put('Û', 'U');
            put('Ù', 'U');
            put('ü', 'u');
            put('û', 'u');
            put('ù', 'u');
        }
    };

    public static String stripAccents(String raw) {
        StringBuilder sb = new StringBuilder(raw);
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (ACCENTS.containsKey(c)){
                sb.setCharAt(i, ACCENTS.get(c));
            }
        }
        return sb.toString();
    }
}
