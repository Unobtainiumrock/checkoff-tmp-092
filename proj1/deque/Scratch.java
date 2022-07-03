package deque;

import java.util.Arrays;

public class Scratch {
    public static void main(String[] args) {
        int[] src = {96, 0, 0, 0, 0, 0, 0, 0, 0, 69, 5, 4, 3, 2 , 0, 0}; // 7
        int[] dest = new int[8];
        int F = 9; // F is 69
        int L = 0; // L is 96

        // build thing
        for (int i = 0; i < dest.length; i++) {
            dest[i] = src[cyclic(F + i, src.length)];
        }

        int[] newSrc = new int[8];

        // Make copy
        for (int i = 0; i < newSrc.length; i++) {
            newSrc[i] = dest[i];
        }

        // Offset with a back-shift
        for (int i = 0; i < dest.length; i++) {
            dest[i] = newSrc[cyclic(i + (src.length - F), dest.length)];
        }

        Arrays.stream(dest).forEach((e) -> {
            System.out.println(e);
        });

    }

    public static int cyclic(int k, int size) {
        return k % size;
    }
}
