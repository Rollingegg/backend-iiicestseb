package group.iiicestseb.backend.utils;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @author jh
 * @date 2020/3/3
 */
public class StringUtil {

    private static final Map<Character, Character> ACCENTS = new TreeMap<Character, Character>() {
        {
            put('Á', 'A');
            put('À', 'A');
            put('Â', 'A');
            put('á', 'a');
            put('à', 'a');
            put('â', 'a');
            put('ä', 'a');
            put('ą', 'a');
            put('ß', 'B');
            put('ç', 'c');
            put('ć', 'c');
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
            put('⊘', 'o');
            put('Ô', 'O');
            put('Ö', 'O');
            put('ø', 'o');
            put('ó', 'o');
            put('ô', 'o');
            put('ö', 'o');
            put('õ', 'o');
            put('ò', 'o');
            put('Û', 'U');
            put('Ù', 'U');
            put('ü', 'u');
            put('û', 'u');
            put('ù', 'u');
        }
    };

    public static String toUUID(Integer id, String type) {
        return type + "-" + id;
    }

    public static String stripAccents(String raw) {
        if (raw == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(raw);
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (ACCENTS.containsKey(c)) {
                sb.setCharAt(i, ACCENTS.get(c));
            }
        }
        return sb.toString();
    }


}
