package array;

/**
 * 把一个(n*n) 的二维数组顺时针旋转90度, 要求空间复杂度O(1)
 * 要点：如何写出对缓存友好的算法
 *
 * @date  2018-06-15
 */
public class RotateImage {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n - i; k++) {
                int temp = matrix[i][k];
                int ri = n - 1 - k;
                int ci = n - 1 - i;
                matrix[i][k] = matrix[ri][ci];
                matrix[ri][ci] = temp;
            }
        }

        int i = -1, j = n;
        while (++i < --j) {
            int[] tmp = matrix[i];
            matrix[i] = matrix[j];
            matrix[j] = tmp;
        }
    }
}
