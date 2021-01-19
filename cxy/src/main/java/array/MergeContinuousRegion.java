package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 合并连续区间，假设输入是: [[1, 3], [2, 6], [9, 10]]
 * 其中[1,3]和[2,6]可以合并成[1, 6]
 *
 * 思路1： 排序时可以的，按照区间的start排序
 * 思路2: 能不能从end上面做文章
 */
public class MergeContinuousRegion {

    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        Arrays.sort(intervals, Comparator.comparingInt((int[] a) -> a[0]));
        List<int[]> result = new ArrayList<>();
        int start = intervals[0][0];
        int end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (end < intervals[i][0]) {
                result.add(new int[]{start, end});
                start = intervals[i][0];
                end = intervals[i][1];
            } else if (end < intervals[i][1]){
                // 有可能会出现完全包含的场景
                end = intervals[i][1];
            }
        }
        result.add(new int[]{start, end});

        return result.toArray(new int[result.size()][]);
    }

    public static void main(String[] args) {
        MergeContinuousRegion merger = new MergeContinuousRegion();
        int[][] result = merger.merge(new int[][]{{1, 3}, {9, 10}, {2, 6}, {10, 12}});
        for (int[] regions: result) {
            System.out.println(Arrays.toString(regions));
        }
    }
}
