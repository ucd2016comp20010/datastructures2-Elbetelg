package project20280.exercises;

public class Q2Q3Fibonnaci {


    public static int F(int n) {
        if(n <= 1) return n;
        return F(n - 1) + F(n - 2);
    }
   /* public static void main(String[] args) {
        int n = 51;
        System.out.println("Fibonacci(" + n + ") = " + F(n));*/


static long[] memo = new long[94];

    public static long fibMemo(int n) {
        if (n <= 1) return n;


        if (memo[n] != 0) {
            return memo[n];
        }


        memo[n] = fibMemo(n - 1) + fibMemo(n - 2);
        return memo[n];
    }

   /* public static void main(String[] args) {
        int n = 93;
        System.out.println("Fibonacci(" + n + ") with memoization: " + fibMemo(n));
    }*/


    static long[] trimemo = new long[94];

    public static long tribMemo(int n) {

        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 0;
        } else if (n == 2) {
            return 1;
        } else if (trimemo[n] != 0) {
            return trimemo[n];
        } else {
            trimemo[n] = tribMemo(n - 1) + tribMemo(n - 2) + tribMemo(n - 3);
            return trimemo[n];
        }
    }

    public static void main(String[] args) {
        int n = 30;
        System.out.println("Tibonacci(" + n + ") = " + tribMemo(n));

    }
}
