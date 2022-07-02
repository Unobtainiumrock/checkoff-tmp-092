package deque;
import java.util.*;


public class ArrayDequeTest {
    public static void main(String[] args) {
        List<Integer> test = new ArrayList<>();

        test.add(1);
        test.add(2);
        test.add(3);

        Deque<Integer> dal = new ArrayDeque<>(test);

        Arrays.asList(((ArrayDeque<Integer>) dal)._items)
                .forEach((e) -> {
                    System.out.println(e);
                });

        System.out.printf("My size is: %s", dal.size());
    }
}