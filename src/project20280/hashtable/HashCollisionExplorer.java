package project20280.hashtable;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class HashCollisionExplorer {
    // (a) & (b) Polynomial Accumulation
    public static int hash_poly(String s, int a) {
        int h = 0;
        for (int i = 0; i < s.length(); i++) {
            int v = s.charAt(i)  * ((int)Math.pow(a,s.length() - i - 1));
            h += v;
        }
        return h;
    }

    // (c) & (d) Cyclic Shift Hash
    public static int hash_cyclic(String s, int shift) {
        int h = 0;
        for (int i = 0; i < s.length(); i++) {
            // Cyclic shift: shift bits left and move overflowing bits to the right
            h = (h << shift) | (h >>> (32 - shift));
            h += (int) s.charAt(i);
        }
        return h;
    }

    // (e) Old Java HashCode
    public static int hashCode(String s) {
        int hash = 0;
        int skip = Math.max(1, s.length() / 8);
        for (int i = 0; i < s.length(); i += skip) {
            hash = (hash * 37) + s.charAt(i);
        }
        return hash;
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<String> words = new ArrayList<>();
        Scanner scanner = new Scanner(new File("/home/elbetel/SecondSemester/DS/datastructures2-Elbetelg/src/project20280/hashtable/words.txt"));
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }
        scanner.close();

        System.out.println("(a) Poly (a=41) collisions: " + countCollisions(words, "poly", 41));
        System.out.println("(b) Poly (a=17) collisions: " + countCollisions(words, "poly", 17));
        System.out.println("(c) Cyclic (shift=7) collisions: " + countCollisions(words, "cyclic", 7));

        // (d) Best cyclic shift
        int minCollisions = Integer.MAX_VALUE;
        int bestShift = 0;
        for (int i = 0; i <= 31; i++) {
            int c = countCollisions(words, "cyclic", i);
            if (c < minCollisions) {
                minCollisions = c;
                bestShift = i;
            }
        }
        System.out.println("(d) Best shift: " + bestShift + " with " + minCollisions + " collisions");
        System.out.println("(e) Old Java Hash collisions: " + countCollisions(words, "old", 0));
    }

    private static int countCollisions(List<String> words, String type, int param) {
        Set<Integer> seenHashes = new HashSet<>();
        int collisions = 0;
        for (String w : words) {
            int h;
            if (type.equals("poly")) h = hash_poly(w, param);
            else if (type.equals("cyclic")) h = hash_cyclic(w, param);
            else h = hashCode(w);

            if (!seenHashes.add(h)) {
                collisions++;
            }
        }
        return collisions;
    }
}
