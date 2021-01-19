package array;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Supplier;

public class Test {
    public static void main(String[] args) {
        int count = 10000;
        for (int i = 0; i < 1000; i++) {
            List<String> list1 = new LinkedList<>();
            fill(list1, i + 1);
            remove(list1, i);

            list1 = new ArrayList<>(i + 1);
            fill(list1, count);
            remove(list1, i);
            System.out.println(i);
        }

        List<List<String>> arrays = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            List<String> list = new ArrayList<>(count);
            fill(list, count);
            arrays.add(list);
        }

        List<List<String>> links = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            List<String> list = new LinkedList<>();
            fill(list, count);
            links.add(list);
        }

        Iterator<List<String>> arrayIt = arrays.iterator();
        Iterator<List<String>> linkIt = links.iterator();


        test(() -> arrayIt.next(), "\n\narray list", count);
        test(() -> linkIt.next(), "\n\nlinked list", count);
    }

    public static void test(Supplier<List<String>> gen, String msg, int count) {
        Instant start = Instant.now();
        for (int i = 0; i < 100; i++) {
            List<String> list = gen.get();
            fill(list, count);
            remove(list, i);
        }
        System.out.println(msg + " cost: " + start.until(Instant.now(), ChronoUnit.MILLIS));
    }

    public static void remove(List<String> list, int seed) {
        int size = list.size();
        int i = 0;
        boolean flag = true;
        list.set(seed, null);
        int index = (size - i) / 2;
        flag &= list.remove(index) != null;
        System.out.print(flag);
    }

    public static void fill(List<String> list, int size) {
        String uuid = UUID.randomUUID().toString();
        for (int i = 0; i < size; i++) {
            list.add(uuid + i);
        }
    }
}
