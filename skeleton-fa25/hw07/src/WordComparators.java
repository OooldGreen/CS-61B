import java.util.Comparator;
import java.util.List;

public class WordComparators {

    private static int count(String a, char c) {
        if (a == null) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == c) {
               count += 1;
            }
        }
        return count;
    }

    /** Returns a comparator that orders strings by the number of lowercase 'x' characters (ascending). */
    public static Comparator<String> getXComparator() {
        // TODO: Implement this.
        return Comparator.comparingInt(s -> count(s, 'x'));
    }

    /** Returns a comparator that orders strings by the count of the given character (ascending). */
    public static Comparator<String> getCharComparator(char c) {
        // TODO: Implement this.
        return Comparator.comparingInt(s -> count(s, c));
    }

    /** Returns a comparator that orders strings by the total count of the given characters (ascending). */
    public static Comparator<String> getCharListComparator(List<Character> chars) {
        // TODO: Implement this.

        return (s1, s2) -> {
            int num1 = 0;
            int num2 = 0;
            for (Character c : chars) {
                num1 += count(s1, c);
                num2 += count(s2, c);
            }
            return num1 - num2;
        };
    }
}
