package subgraph;

//////////////////////////////////////
// subgraph.Permutation
//
// You do not need to write the code below here.
// You just need to be able to USE it.
//////////////////////////////////////

// The algorithm is from Applied Combinatorics, by Alan Tucker.
// Based on code from koders.com

class Permutation {
    private int n, r;
    private long[] index;
    private boolean hasNext = true;

    public Permutation(long[] index, int n, int r) {
        this.n = n;
        this.r = r;
        this.index = index;
        reverseAfter(r - 1);
    }

    public boolean hasNext() {
        return hasNext;
    }

    // Based on code from KodersCode:
    // The algorithm is from Applied Combinatorics, by Alan Tucker.
    // Move the index forward a notch. The algorithm first finds the
    // rightmost index that is less than its neighbor to the right. This
    // is the dip point. The algorithm next finds the least element to
    // the right of the dip that is greater than the dip. That element is
    // switched with the dip. Finally, the list of elements to the right
    // of the dip is reversed.
    //
    // For example, in a permutation of 5 items, the index may be
    // {1, 2, 4, 3, 0}. The dip is 2  the rightmost element less
    // than its neighbor on its right. The least element to the right of
    // 2 that is greater than 2 is 3. These elements are swapped,
    // yielding {1, 3, 4, 2, 0}, and the list right of the dip point is
    // reversed, yielding {1, 3, 0, 2, 4}.

    private void moveIndex() {
        // find the index of the first element that dips
        int i = rightmostDip();
        if (i < 0) {
            hasNext = false;
            return;
        }

        // find the smallest element to the right of the dip
        int smallestToRightIndex = i + 1;
        for (int j = i + 2; j < n; j++)
            if ((index[j] < index[smallestToRightIndex]) && (index[j] > index[i]))
                smallestToRightIndex = j;

        // switch dip element with smallest element to its right
        swap(index, i, smallestToRightIndex);

        if (r - 1 > i) {
            // reverse the elements to the right of the dip
            reverseAfter(i);
            // reverse the elements to the right of r - 1
            reverseAfter(r - 1);
        }
    }

    public long[] next() {
        if (!hasNext) return null;
        long[] result = new long[r];
        //for (int i = 0; i < r; i++) result[i] = index[i];
        System.arraycopy(index, 0, result, 0, r);
        moveIndex();
        return result;
    }

    // Reverse the index elements to the right of the specified index.
    private void reverseAfter(int i) {
        int start = i + 1;
        int end = n - 1;
        while (start < end) {
            swap(index, start, end);
            start++;
            end--;
        }
    }

    // return int the index of the first element from the right
    // that is less than its neighbor on the right.
    private int rightmostDip() {
        for (int i = n - 2; i >= 0; i--)
            if (index[i] < index[i + 1])
                return i;
        return -1;
    }

    private void swap(long[] a, int i, int j) {
        long temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}