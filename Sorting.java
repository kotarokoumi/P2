import java.util.*;

/**
 * The Sorting class provides static methods for performing in-place quicksort and heapsort on a List of elements that implement Comparable.
 * Both methods return the number of element-to-element comparisons made during sorting.
 */
public class Sorting {

    /**
     * Sorts the given list in-place using the quicksort algorithm, with the first element as the pivot, and returns the number of element-to-element comparisons made during the sort.
     *
     * @param <T>  the type of elements in the list, which must implement Comparable
     * @param list  the list to be sorted in-place
     * @return the number of element comparisons performed
     */
    public static <T extends Comparable<? super T>> int quicksort(List<T> list) {
        return quicksortHelper(list, 0, list.size() - 1);
    }

    // Helper method for recursive quicksort.
    private static <T extends Comparable<? super T>> int quicksortHelper(List<T> list, int low, int high) {
        if (low >= high) return 0;
        int[] result = partition(list, low, high);
        int pivotIndex = result[0];
        int comparisons = result[1];
        comparisons += quicksortHelper(list, low, pivotIndex - 1);
        comparisons += quicksortHelper(list, pivotIndex + 1, high);
        return comparisons;
    }

    // Partitions the list using the first element as pivot.
    // Returns {pivotIndex, comparisonsCount}.
    private static <T extends Comparable<? super T>> int[] partition(List<T> list, int low, int high) {
        T pivot = list.get(low);
        int left = low + 1;
        int right = high;
        int comparisons = 0;

        while (true) {
            while (left <= right && list.get(left).compareTo(pivot) <= 0) {
                left++;
                comparisons++;
            }
            if (left <= right) comparisons++;

            while (left <= right && list.get(right).compareTo(pivot) > 0) {
                right--;
                comparisons++;
            }
            if (left <= right) comparisons++;

            if (left > right) break;

            swap(list, left, right);
            left++;
            right--;
        }

        swap(list, low, right);
        return new int[]{right, comparisons};
    }

    /**
     * Sorts the given list in-place using the heapsort algorithm, built upon a max-heap, and returns the number of element-to-element comparisons made during the sort.
     *
     * @param <T>  the type of elements in the list, which must implement Comparable
     * @param list  the list to be sorted in-place
     * @return the number of element comparisons performed
     */
    public static <T extends Comparable<? super T>> int heapsort(List<T> list) {
        int n = list.size();
        int comparisons = 0;

        for (int i = n / 2 - 1; i >= 0; i--) {
            comparisons += heapify(list, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(list, 0, i);
            comparisons += heapify(list, i, 0);
        }

        return comparisons;
    }

    // Maintains the max-heap property for the subtree rooted at index i.
    // Returns the number of comparisons made.
    private static <T extends Comparable<? super T>> int heapify(List<T> list, int heapSize, int i) {
        int comparisons = 0;
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < heapSize) {
            comparisons++;
            if (list.get(left).compareTo(list.get(largest)) > 0) {
                largest = left;
            }
        }

        if (right < heapSize) {
            comparisons++;
            if (list.get(right).compareTo(list.get(largest)) > 0) {
                largest = right;
            }
        }

        if (largest != i) {
            swap(list, i, largest);
            comparisons += heapify(list, heapSize, largest);
        }

        return comparisons;
    }

    // Swaps two elements in the list.
    private static <T> void swap(List<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    /**
     * The main method tests both quicksort and heapsort on two identical ArrayLists of 20,000 random integers. 
     * It verifies that the lists are correctly sorted and prints the number of comparisons made by each algorithm.
     *
     * @param args  not used
     */
    public static void main(String[] args) {
        final int SIZE = 20000;
        Random rand = new Random();

        List<Integer> list1 = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            list1.add(rand.nextInt(1_000_000));
        }

        List<Integer> list2 = new ArrayList<>(list1);

        int quickComparisons = quicksort(list1);
        int heapComparisons = heapsort(list2);

        System.out.println("Quicksort comparisons: " + quickComparisons);
        System.out.println("Heapsort comparisons: " + heapComparisons);

        System.out.println("Quicksort sorted correctly: " + isSorted(list1));
        System.out.println("Heapsort sorted correctly: " + isSorted(list2));
    }

    // Verifies that a list is sorted in non-decreasing order.
    private static <T extends Comparable<? super T>> boolean isSorted(List<T> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }
}