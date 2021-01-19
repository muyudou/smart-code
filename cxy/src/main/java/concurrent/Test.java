package concurrent;

public class Test {
    volatile int a = 0;
    volatile int b = 0;

    public static void main(String[] args) {
        Test t = new Test();

        new Thread(() -> {
            while (true) {
                t.a++;
                t.b++;
            }
        }).start();

        while (true) {
            if (t.a < t.b) {
                System.out.println(t.a + "-----------" + t.b);
            }
        }
    }
}
