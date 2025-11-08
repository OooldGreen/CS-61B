import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class ListExercises {
    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        // TODO: Implement this method
        int sum = 0;

        for (int i : L) {
          sum += i;
        }

        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Implement this method
        List<Integer> evens = new ArrayList<>();

        for (int i : L) {
            if (i % 2 == 0) {
                evens.add(i);
            }
        }

        return evens;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Implement this method
        List<Integer> commonL = new ArrayList<>();

        for (int i : L1) {
            for (int j : L2) {
                if (i == j) {
                    commonL.add(i);
                }
            }
        }

        return commonL;
    }

    public static int countOccurrencesOfWord(List<String> words, String w) {
        // TODO: Implement this method

        return 0;
    }

    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Implement this method
        int count = 0;

        for (String ele : words) {
            for (int i = 0; i < ele.length(); i++) {
                if (ele.charAt(i) == c) {
                    count ++;
                }
            }
        }
        return count;
    }
}
