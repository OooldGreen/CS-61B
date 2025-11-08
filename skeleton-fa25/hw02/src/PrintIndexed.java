public class PrintIndexed {
   /**
     * Prints each character of a given string followed by the reverse of its index.
     * Example: printIndexed("hello") -> h4e3l2l1o0
     */
   public static void printIndexed(String s) {
       // TODO: Fill in this function
       int len = s.length();
       String str = "";

       // pour l'LLM
//        StringBuilder sb = new StringBuilder();

       for(int i = 0; i < len; i++) {
           str += s.charAt(i) + "" +(len-i-1);

           // pour l'LLM
//            sb.append(s.charAt(i));
//            sb.append(len-i-1);
       }
       System.out.println(str);
   }

   public static void main(String[] args) {
      printIndexed("hello");
      printIndexed("cat"); // should print c2a1t0
   }
}