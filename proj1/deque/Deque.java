package deque;
import java.util.NoSuchElementException;
import java.util.Queue;

// Source: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Deque.html#addFirst(E)
public interface Deque<E>  extends Queue {
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
    public void addFirst(E e) throws IllegalStateException, ClassCastException, NullPointerException, IllegalArgumentException;

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
    public void addLast(E e) throws IllegalStateException, ClassCastException, NullPointerException, IllegalArgumentException;

    /**
     * Retrieves and removes the first element of this deque. This method differs from pollFirst only in that
     * it throws an exception if this deque is empty.
     *
     * @return the head of this deque.
     * @throws NoSuchElementException -if this deque is empty
     */
    public E removeFirst() throws NoSuchElementException;

    /**
     * Retrieves and removes the last element of this deque. This method differs from pollLast only in that
     * it throws an exception if this deque is empty.
     *
     * @return
     * @throws NoSuchElementException - if the deque is empty
     */
    public E removeLast() throws NoSuchElementException;

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * Must not mutate the deque.
     *
     * @param index
     * @return
     */
    public E get(int index) throws NoSuchElementException;

}
