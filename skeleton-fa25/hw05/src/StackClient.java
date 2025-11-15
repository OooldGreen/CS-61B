public class StackClient {
    public static void flippedHelper (Stack original, Stack flipped) {
        if (original.size() == 0) {
            return;
        }

        int x = original.pop();
        flipped.push(x);
        flippedHelper(original, flipped);
    }

    public static Stack flipped(Stack s) {
        Stack flippedStack = new Stack();

        // 1. irritation

//        while (s.size() > 0) {
//            int x = s.pop();
//            flippedStack.push(x);
//        }

        // 2. recursion
        flippedHelper(s, flippedStack);

        return flippedStack;
    }

}