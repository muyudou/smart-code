package array;

import java.util.Arrays;

/**
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
 *
 *
 * 示例 1：
 *
 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[1,2,3,6,9,8,7,4,5]
 * 示例 2：
 *
 * 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
 * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
 *
 * 限制：
 *
 * 0 <= matrix.length <= 100
 * 0 <= matrix[i].length <= 100
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/shun-shi-zhen-da-yin-ju-zhen-lcof
 */
public class SpiralOrder {
    public static int[] spiralOrder(int[][] matrix) {
        int row = matrix.length;
        int column = matrix[0].length;
        int[] result = new int[row * column];
        int index = 0;

        int left = 0, right = column - 1;
        int top = 0, bottom = row - 1;
        while (left <= right && top <= bottom) {
            for (int i = left; i <= right; i++) {
                result[index++] = matrix[top][i];
            }

            for (int i = top + 1; i <= bottom; i++) {
                result[index++] = matrix[i][right];
            }

            if (left < right && top < bottom) {
                for (int i = right - 1; i >= left; i--) {
                    result[index++] = matrix[bottom][i];
                }

                for (int i = bottom - 1; i > top; i--) {
                    result[index++] = matrix[i][left];
                }
            }

            left++;
            right--;
            top++;
            bottom--;
        }

        return result;
    }

    public static void main(String[] args) {
//        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
//        System.out.println(Arrays.toString(spiralOrder(matrix)));

        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        System.out.println(Arrays.toString(spiralOrder(matrix)));
    }

}
