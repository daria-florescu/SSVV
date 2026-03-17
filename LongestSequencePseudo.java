import java.util.Scanner;

/**
 * LongestSequencePseudo.java
 *
 * EXACT translation of the pseudocode into Java.
 * No fixes applied. Bugs from the pseudocode are preserved intentionally.
 *
 * Known bugs (from review form):
 *   D_01 - i starts at 0, but array is 1-based -> valE[0] is always 0 (garbage)
 *   D_02 - i only increments in the Else branch -> infinite loop if lungFinala always > lungF
 *   D_03 - still3Values receives only one element -> always returns true -> window never stops
 *   D_04 - typo "pozStar" preserved as comment only (fixed to compile)
 */
public class LongestSequencePseudo {

    static int[] valE;
    static int nrE;
    static int pozF;
    static int lungF;
    static int pozStart;
    static int lungFinala;

    /*
     * Still3Values(valE(i))
     * Pseudocode passes a single element: Still3Values(valE(i))
     * A single element trivially has 1 distinct value -> always returns true.
     * This means the while loop in computeASequence NEVER stops early --
     * it always runs until pozStart > nrE, consuming the whole remaining array.
     */
    static boolean still3Values(int element) {
        return true; // single element always has <= 3 distinct values -- bug!
    }

    /*
     * Subalgorithm ComputeASequence(pozStart, nrE, valE, lungFinala)
     * Input:  pozStart, nrE, valE
     * Output: pozStart, lungFinala
     */
    static void computeASequence() {
        lungFinala = 0;
        while (pozStart <= nrE && still3Values(valE[pozStart])) {
            lungFinala = lungFinala + 1;
            pozStart = pozStart + 1; // typo in pseudocode was "pozStar"
        }
    }

    /*
     * Subalgorithm ComputeMaxSeq(nrE, valE, pozF, lungF)
     */
    static void computeMaxSeq() {
        int i = 0;  // bug: should be 1 for 1-based array
        pozF  = 0;
        lungF = 0;

        while (i <= nrE) {
            pozStart = i;
            computeASequence();

            if (lungFinala > lungF) {
                pozF  = i;
                lungF = lungFinala;
                // bug: i is NOT incremented here -> infinite loop!
            } else {
                i = i + 1;
            }
        }
    }

    static void readSequence() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of elements: ");
        nrE  = sc.nextInt();
        valE = new int[nrE + 1];
        System.out.print("Enter elements: ");
        for (int i = 1; i <= nrE; i++) {
            valE[i] = sc.nextInt();
        }
    }

    static void printSequence() {
        System.out.println("Longest sequence start index : " + pozF);
        System.out.println("Longest sequence length      : " + lungF);
        System.out.print("Elements: ");
        for (int i = pozF; i < pozF + lungF; i++) {
            System.out.print(valE[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        readSequence();
        computeMaxSeq();
        printSequence();
    }
}
