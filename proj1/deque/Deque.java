package deque;
<<<<<<< HEAD
import java.util.NoSuchElementException;
import java.util.Queue;

// Source: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Deque.html#addFirst(E)
public interface Deque<E> extends Queue {
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

    default boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * Must not mutate the deque.
     *
     * @param index
     * @return
     */
    E get(int index) throws NoSuchElementException;

    boolean equals(Object o);
=======

/* The Deque interface defines the expected behavior for any
* Deque, whether it is an ArrayDeque or LinkedListDeque. A
* Deque is a doubly-ended queue, that allows you to quickly add
* and remove items from the front and back. */
public interface Deque<T> {

>>>>>>> 759b4c4e2d4cd1397a7d85cefc5a88b9f440e6bf
}
