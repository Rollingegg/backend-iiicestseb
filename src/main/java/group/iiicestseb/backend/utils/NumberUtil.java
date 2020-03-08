package group.iiicestseb.backend.utils;

/**
 * @author jh
 * @date 2020/3/3
 */
public class NumberUtil {

    public static int string2Int(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        else if (s.charAt(0) <= 'z' && s.charAt(0) >= 'a' ||
                s.charAt(0) <= 'Z' && s.charAt(0) >= 'a') {
            return rome2Arabic(s);
        } else {
            return Integer.parseInt(s);
        }
    }

    /**
     * 把罗马数字转换为阿拉伯数字
     *
     * @param romeNumber 罗马数字
     * @return 阿拉伯数字
     */
    public static int rome2Arabic(String romeNumber) {
        int[] graph = new int[400];
        graph['I'] = 1;
        graph['V'] = 5;
        graph['X'] = 10;
        graph['L'] = 50;
        graph['C'] = 100;
        graph['D'] = 500;
        graph['M'] = 1000;
        char[] num = romeNumber.toCharArray();
        int sum = graph[num[0]];
        for (int i = 0; i < num.length - 1; i++) {
            if (graph[num[i]] >= graph[num[i + 1]]) {
                sum += graph[num[i + 1]];
            } else {
                sum = sum + graph[num[i + 1]] - 2 * graph[num[i]];
            }
        }
        return sum;
    }

    /**
     * 把阿拉伯数字转换为罗马数字
     *
     * @param arabicNumber 阿拉伯数字
     * @return 罗马数字
     */
    public static String arabic2Rome(int arabicNumber) {
        StringBuilder rNumber = new StringBuilder();
        int[] aArray = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] rArray = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X",
                "IX", "V", "IV", "I"};
        if (arabicNumber < 1 || arabicNumber > 3999) {
            rNumber = new StringBuilder("-1");
        } else {
            for (int i = 0; i < aArray.length; i++) {
                while (arabicNumber >= aArray[i]) {
                    rNumber.append(rArray[i]);
                    arabicNumber -= aArray[i];
                }
            }
        }
        return rNumber.toString();
    }

}
