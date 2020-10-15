package array;

public class BigIntAdd {
    static StringBuilder sb = new StringBuilder();
    static int carryOver = 0;

    public static String add(String a1, String a2) {
        int i = a1.length() - 1;
        int j = a2.length() - 1;
        while (i >= 0 && j >= 0) {
            append((a1.charAt(i--) - '0') + (a2.charAt(j--) - '0'));
        }

        while (i >= 0) {
            append(a1.charAt(i--) - '0');
        }

        while (j >= 0) {
            append(a2.charAt(j--) - '0');
        }

        return sb.reverse().toString();
    }

    public static void append(int sum) {
        sum += carryOver;
        if (sum > 9) {
            carryOver = 1;
            sb.append(sum - 10);
        } else {
            carryOver = 0;
            sb.append(sum);
        }
    }

    public static void main(String[] args) {
        System.out.println(add("2345677", "567"));
    }
}
