
package project20280.hashtable;

import project20280.interfaces.Entry;
import java.util.*;
import java.io.*;

public class WordFrequencyCounter {
    public static void main(String[] args) throws FileNotFoundException {

        ChainHashMap<String, Integer> counter = new ChainHashMap<>(19);

        File f = new File("/home/elbetel/SecondSemester/DS/datastructures2-Elbetelg/src/project20280/hashtable/sample_text.txt");
        Scanner scanner = new Scanner(f);

        while (scanner.hasNext()) {
            // Clean the word: lowercase and remove punctuation
            String word = scanner.next().toLowerCase().replaceAll("[^a-z]", "");

            if (!word.isEmpty()) {
                Integer count = counter.get(word);
                if (count == null) {
                    counter.put(word, 1);
                } else {
                    counter.put(word, count + 1);
                }
            }
        }
        scanner.close();

        // Convert the entrySet() to a List for sorting
        List<Entry<String, Integer>> list = new ArrayList<>();
        for (Entry<String, Integer> entry : counter.entrySet()) {
            list.add(entry);
        }

        // Sort by value (frequency) in descending order
        list.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Print Top 10
        System.out.println("Top 10 most frequent words:");
        for (int i = 0; i < Math.min(10, list.size()); i++) {
            Entry<String, Integer> e = list.get(i);
            System.out.println(e.getKey() + ": " + e.getValue());
        }
    }
}
