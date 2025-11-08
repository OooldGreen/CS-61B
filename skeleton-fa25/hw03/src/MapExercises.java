import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        // TODO: Implement this method
        Map<Character, Integer> map = new HashMap<>();
        int num = 1;

        for (char c = 'a'; c <= 'z'; c++) {
            map.put(c, num);
            num++;
        }
        return map;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        // TODO: Implement this method
        Map<Integer, Integer> map = new HashMap<>();

        for (int i : nums) {
            map.put(i, i * i);
        }
        return map;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        // TODO: Implement this method
        Map<String, Integer> map = new HashMap<>();

        for (String i : words) {
            if (map.containsKey(i)) {
                map.compute(i, (key, val) -> val + 1);
            } else {
                map.put(i, 1);
            }
        }

        return map;
    }
}
