import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {
    @Test
    /** This test verifies that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");
        assertThat(lld1.toList()).containsExactly("front", "middle", "back");
    }

    @Test
    /** This test verifies that addLast works correctly. */
    public void addLastTestBasic() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toList()).containsExactly("front", "middle", "back");
    }

    @Test
    /** This test verifies that addFirst and addLast methods works correctly. */
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        assertThat(lld1.toList()).containsExactly(5, 4, 1, 2, 3);
        lld1.addLast(6);
        assertThat(lld1.toList()).containsExactly(5, 4, 1, 2, 3, 6);
    }

    @Test
    /** This test is for get method. */
    public void getTest() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addLast("middle");
        lld1.addLast("back");
        lld1.addFirst("front");
        assertThat(lld1.get(4)).isEqualTo(null);
        assertThat(lld1.get(-1)).isEqualTo(null);
        assertThat(lld1.get(0)).isEqualTo("front");
        assertThat(lld1.get(2)).isEqualTo("back");
    }

    @Test
    /** This test verifies isEmpty and size methods. */
    public void isEmptyAndSizeTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.size()).isEqualTo(0);

        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld1.size()).isEqualTo(3);
    }

    @Test
    /** This test is for removeFirst and removeLast */
    public void removeFirstAndRemoveLastTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(1);
        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly();
        assertThat(lld1.removeFirst()).isEqualTo(null);

        lld1.addLast(2);
        lld1.addLast(3);
        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly(3);
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly();
        assertThat(lld1.removeLast()).isEqualTo(null);
    }

    @Test
    /** This test verifies that resize method works correctly for addFirst and also test the resizeDown method. */
    public void addFirstResizeTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        lld1.addLast(6);
        lld1.addLast(7);
        lld1.addLast(8);
        lld1.addFirst(9);
        assertThat(lld1.toList()).containsExactly(9, 5, 4, 1, 2, 3, 6, 7, 8);

        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly(9, 5);
    }

    @Test
    /** This test verifies that resize method works correctly. */
    public void addLastResizeTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        lld1.addLast(6);
        lld1.addLast(7);
        lld1.addLast(8);
        lld1.addLast(9);
        assertThat(lld1.toList()).containsExactly(5, 4, 1, 2, 3, 6, 7, 8, 9);

        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly( 8, 9);
    }
}
