package deque;
import java.util.NoSuchElementException;

// Source: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Deque.html#addFirst(E)
public interface Deque<E> {

    /**
     * Inserts the specified element at the front of this deque if it is possible to do so immediately without violating capacity restrictions,
     * throwing an IllegalStateException if no space is currently available. When using a capacity-restricted deque,
     * it is generally preferable to use method offerFirst(E).
     *
     * @param e - the element to add
     * @throws IllegalStateException    -  if the element cannot be added at this time due to capacity restrictions
     * @throws ClassCastException       - if the class of the specified element prevents it from being added to this deque
     * @throws NullPointerException     - if the specified element is null and this deque does not permit null elements
     * @throws IllegalArgumentException - if some property of the specified element prevents it from being added to this deque
     */
    void addFirst(E e) throws IllegalStateException, ClassCastException, NullPointerException, IllegalArgumentException;

    /**
     * Inserts the specified element at the end of this deque if it is possible to do so immediately without violating capacity restrictions,
     * throwing an IllegalStateException if no space is currently available. When using a capacity-restricted deque, it is generally preferable
     * to use method offerLast(E).
     *
     * @param e - the element to add
     * @throws IllegalStateException    - if the element cannot be added at this time due to capacity restrictions
     * @throws ClassCastException       - if the class of the specified element prevents it from being added to this deque
     * @throws NullPointerException     - if the specified element is null and this deque does not permit null elements
     * @throws IllegalArgumentException - if some property of the specified element prevents it from being added to this deque
     */
    void addLast(E e) throws IllegalStateException, ClassCastException, NullPointerException, IllegalArgumentException;

    /**
     * Retrieves and removes the first element of this deque. This method differs from pollFirst only in that
     * it throws an exception if this deque is empty.
     *
     * @return the head of this deque.
     * @throws NoSuchElementException -if this deque is empty
     */
    E removeFirst() throws NoSuchElementException;

    /**
     * Retrieves and removes the last element of this deque. This method differs from pollLast only in that
     * it throws an exception if this deque is empty.
     *
     * @return
     * @throws NoSuchElementException - if the deque is empty
     */
    E removeLast() throws NoSuchElementException;

//    default boolean isEmpty() {
//        return this.size() == 0;
//    }

    boolean isEmpty();

    int size();

//    boolean contains(E e);

    boolean equals(Object o);

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * Must not mutate the deque.
     *
     * @param index
     * @return
     */
    E get(int index) throws NoSuchElementException;

    /**
     * Prints the deque, separated by whitespace, ends with a new line
     */
    void printDeque();
}
