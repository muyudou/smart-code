package iterator;

import java.util.Optional;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        Optional<Long> x1 = Optional.of(1L);
        Optional<Long> x2 = Optional.of(2L);

        Long sum = Stream.of(x1, x2).filter(Optional::isPresent).map(Optional::get).reduce((l1, l2) -> l1 + l2).get();
        System.out.println(sum);
    }
}
