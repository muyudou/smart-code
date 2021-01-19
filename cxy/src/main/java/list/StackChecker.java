package list;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.primitives.Chars.asList;

/**
 * 用来检测花括号是否匹配
 */
public class StackChecker {
    public static final Set<Character> LEFT = new HashSet<>();
    public static final Map<Character, Character> RIGHT2LEFT = new HashMap<>();

    static {
        LEFT.addAll(asList('[', '(', '{'));
        RIGHT2LEFT.put(']', '[');
        RIGHT2LEFT.put(')', '(');
        RIGHT2LEFT.put('}', '{');
    }

    public static boolean valid(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }

        Stack<Character> stack = new LStack<>();
        for (int i = 0; i < s.length(); i++) {
            if (LEFT.contains(s.charAt(i))) {
                stack.push(s.charAt(i));
            } else if (RIGHT2LEFT.containsKey(s.charAt(i))) {
                char left = stack.pop();
                if (left != RIGHT2LEFT.get(s.charAt(i))) {
                    throw new RuntimeException(s + ": 括号不匹配");
                }
            }
        }
        if (! stack.isEmpty()) {
            throw new RuntimeException(s + ": 括号不匹配");
        }
        return true;
    }

    public static void main(String[] args) {
        valid("caoxiaoyong");
        valid("{caoxiaoyong}");
        valid("c{a{o((x)(i)((a))[[([o])]])y}o}ng");
        try {
            valid("c{a{o(x)i}aoyong");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
