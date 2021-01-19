package strings;

import java.util.Arrays;
import java.util.List;

/**
 * version因多人维护，不规则
 * versions=['1.45.0','1.5','6','3.3.3.3.3.3.3']
 * 要求从小到大排序，注意'1.45'比'1.5'大
 */
public class VersionSort {

    public static void versionSort(List<String> versions) {
        versions.sort((s1, s2) -> {
            int n1 = s1.length();
            int n2 = s2.length();

            int idx1 = 0;
            int idx2 = 0;

            while (idx1 < n1 || idx2 < n2) {
                int v1 = 0;
                int v2 = 0;
                char c;
                while (idx1 < n1 && (c = s1.charAt(idx1++)) != '.') {
                    v1 = v1 * 10 + (c - '0');
                }

                while (idx2 < n2 && (c = s2.charAt(idx2++)) != '.')  {
                    v2 = v2 * 10 + (c - '0');
                }

                if (v1 != v2) {
                    return Integer.compare(v1, v2);
                }
            }
            return 0;
        });
    }

    public static void main(String[] args) {
        List<String> versions = Arrays.asList("1.45", "1.45", "1.5", "1.5.5", "2.0.0.5", "2.0", "2.0.1", "2.1", "2.1.1.1.1.1.1", "2.1.1.1.1.1.0", "2.1.1.1.1.1.2");
        versionSort(versions);
        System.out.println(versions);
    }
}
