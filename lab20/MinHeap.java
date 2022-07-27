import java.util.ArrayList;
import java.util.NoSuchElementException;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;
    // TODO: YOUR CODE HERE (no code should be needed here if not 
    // implementing the more optimized version)

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        return 2 * index;
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        return 2 * index + 1;
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        return index / 2;
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        E a = this.getElement(index1);
        E b = this.getElement(index2);
        if (b == null || a == null) {
            return index1;
        }
        return a.compareTo(b) > 0 ? index2 : index1;
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        return this.getElement(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
        //while (index is not the root and arr[index] is smaller than parent) {
           //swap arr[index] with arr[parent]
            //update index to parent
        //}
        int x = this.getParentOf(index);

        if (this.getElement(x) == null) {
            return;
        }
        
        while (index != 1 && min(index, x) == index) {
            swap(index, x);
            index = x;
            x = this.getParentOf(index);
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        //while (there is a child and arr[index] is greater than either child) {
            //swap arr[index] with the SMALLER child
            //update index to the index of the swapped child
        //}
        int x = this.getLeftOf(index);
        int y = this.getRightOf(index);
        E b = this.getElement(x);
//        while (b != null && index != size && (min(index, this.getLeftOf(index)) == this.getLeftOf(index) || min(index, this.getRightOf(index)) == this.getRightOf(index))) {
//            int w = min(this.getLeftOf(index), this.getRightOf(index));
//            swap(index, w);
//            index = w;
//        }
        while(b != null && (this.min(index, y) == y || this.min(index, x) == x)) {
            int w = min(x, y);
            swap(index, w);
            index = w;
            x = this.getLeftOf(w);
            y = this.getRightOf(w);
            b = this.getElement(x);
        }
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        return this.size;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) throws IllegalArgumentException {
        if (this.contains(element)) {
            throw new IllegalArgumentException();
        }
//        this.contents.add(this.size + 1, element);
        this.setElement(this.size + 1, element);
        this.size++;
        bubbleUp(this.size);
    }

    /* Returns and removes the smallest element in the MinHeap. */
    public E removeMin() {
        E res = this.findMin();
        swap(1, size());
        setElement(size(), null);
        this.size--;
        bubbleDown(1);
        return res;
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) throws IllegalArgumentException {
        int loc = contents.indexOf(element);

        if (loc == -1) {
            throw new NoSuchElementException();
        }

        bubbleDown(loc);
        bubbleUp(loc);
    }

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        return contents.contains(element);
    }
}
