package cache;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LRUCache<K, V> {

    public static void main(String[] args) {
        List<Record>  records = Arrays.asList(
                new Record(1, "1"),
                new Record(1, "1"),
                new Record(2, "2"),
                new Record(2, "2"),
                new Record(3, "3")
        );

        List<Record> uniq = records.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(uniq);
    }



    public static class Record {
        private int id;
        private String name;

        public Record(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Record record = (Record) o;
            return id == record.id && Objects.equals(name, record.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "Record{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}
