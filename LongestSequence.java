import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * LongestSequence.java
 *
 * Problem Statement:
 *   Given a sequence of n integers, find the longest contiguous subsequence
 *   that contains at most 3 distinct values.
 *
 * Design (corrected from pseudocode):
 *   - readSequence()       : reads n and the n elements (1-based array)
 *   - computeMaxSeq()      : finds the start index and length of the longest valid window
 *   - computeASequence()   : extends a window from pozStart while <=3 distinct values
 *   - still3Values()       : checks if adding an element keeps distinct count <= 3
 *   - printSequence()      : prints the result
 *
 * Defects fixed from pseudocode:
 *   1. i initialized to 1 (not 0) for 1-based indexing.
 *   2. i advanced to newPozStart after computeASequence, not just i+1.
 *   3. Typo "pozStar" corrected to "pozStart".
 *   4. still3Values() implemented (was missing in design).
 *   5. pozStart returned from computeASequence via int[] (Java pass-by-value).
 */
public class LongestSequence {

    /**
     * Reads the sequence from standard input.
     * Input format: first line is n; second line contains n space-separated integers.
     *
     * @param nrE  single-element array used to return the number of elements
     * @return     1-based integer array of size nrE[0]+1
     */
    static int[] readSequence(int[] nrE) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of elements: ");
        nrE[0] = sc.nextInt();
        int[] valE = new int[nrE[0] + 1]; // 1-based
        System.out.print("Enter elements: ");
        for (int i = 1; i <= nrE[0]; i++) {
            valE[i] = sc.nextInt();
        }
        return valE;
    }

    /**
     * Returns true if adding valE[pos] to the window tracked by freq
     * keeps the number of distinct values at most 3.
     *
     * @param valE  the sequence array (1-based)
     * @param pos   position of the next candidate element
     * @param freq  frequency map of elements currently in the window
     * @return      true if the window can be extended
     */
    static boolean still3Values(int[] valE, int pos, Map<Integer, Integer> freq) {
        int val = valE[pos];
        if (freq.containsKey(val)) {
            return true; // already tracked, distinct count unchanged
        }
        return freq.size() < 3; // room for one more distinct value
    }

    /**
     * ComputeASequence: extends a contiguous window starting at pozStart
     * for as long as the number of distinct values stays <= 3.
     *
     * @param pozStart  starting position (1-based)
     * @param nrE       total number of elements
     * @param valE      the sequence (1-based)
     * @return          int[]{newPozStart, length}
     *                  newPozStart = first position NOT included in this window
     *                  length      = number of elements in the window
     */
    static int[] computeASequence(int pozStart, int nrE, int[] valE) {
        Map<Integer, Integer> freq = new HashMap<>();
        int lungFinala = 0;

        while (pozStart <= nrE && still3Values(valE, pozStart, freq)) {
            freq.merge(valE[pozStart], 1, Integer::sum);
            lungFinala++;
            pozStart++; // corrected typo: was "pozStar" in pseudocode
        }
        return new int[]{pozStart, lungFinala};
    }

    /**
     * ComputeMaxSeq: scans all starting positions, tracking the longest window.
     *
     * @param nrE   number of elements
     * @param valE  the sequence (1-based)
     * @return      int[]{pozF, lungF} – start index and length of the best window
     */
    static int[] computeMaxSeq(int nrE, int[] valE) {
        int i     = 1;  // Fix: start at 1 (not 0) for 1-based indexing
        int pozF  = 1;
        int lungF = 0;

        while (i <= nrE) {
            int[] result    = computeASequence(i, nrE, valE);
            int newPoz      = result[0];
            int lungFinala  = result[1];

            if (lungFinala > lungF) {
                pozF  = i;
                lungF = lungFinala;
            }
            // Fix: advance i to newPoz (past the current window), not just i+1
            i = (newPoz > i) ? newPoz : i + 1;
        }
        return new int[]{pozF, lungF};
    }

    /**
     * Prints the starting position, length, and elements of the longest sequence.
     *
     * @param valE  the sequence (1-based)
     * @param pozF  start index of the longest sequence
     * @param lungF length of the longest sequence
     */
    static void printSequence(int[] valE, int pozF, int lungF) {
        System.out.println("Longest sequence start index (1-based): " + pozF);
        System.out.println("Longest sequence length               : " + lungF);
        System.out.print("Elements: ");
        for (int i = pozF; i < pozF + lungF; i++) {
            System.out.print(valE[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] nrE  = new int[1];
        int[] valE = readSequence(nrE);

        int[] res = computeMaxSeq(nrE[0], valE);
        printSequence(valE, res[0], res[1]);
    }
}
