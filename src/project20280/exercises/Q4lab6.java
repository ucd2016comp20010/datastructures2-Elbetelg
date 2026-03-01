package project20280.exercises;

public class Q4lab6 {
    public static int mcCarthy91(int n) {
        if (n > 100) {
            return n - 10; // The "exit" condition
        } else {
            // The nested recursive call
            return mcCarthy91(mcCarthy91(n + 11));
        }
    }
    public static void main(String[] args) {
        int n = 87;
        System.out.println("mcCarthy91(" + n + ") = " + mcCarthy91(n));
    }
}
