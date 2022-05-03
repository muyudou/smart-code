package array;

/**
 * 把一个(n*n) 的二维数组顺时针旋转90度, 要求空间复杂度O(1)
 * 要点：如何写出对缓存友好的算法
 *
 * @date  2018-06-15
 */
public class RotateImage {
    /**
     * a[i][j] --> a[j][n-1 - i]
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < (n + 1) / 2; j++) {
                /**
                 * 假设正方形的四个定点分别叫 A1, A2, A3, A4
                 * A1 = matrix[i][j]
                 * A2 = matrix[j][n-1 -i]
                 * A3 = matrix[n-1 - i][n-1 -j]
                 * A4 = matrix[n-1 - j][i]
                 */
                // A1 --> A2
                int a2 = matrix[j][n-1 - i];
                matrix[j][n-1 - i] = matrix[i][j];

                // A4 --> A1
                matrix[i][j] = matrix[n-1 - j][i];

                // A3 --> A4
                matrix[n-1 - j][i] = matrix[n-1 - i][n-1 - j];

                // A2 --> A3
                matrix[n-1 - i][n-1 - j] = a2;
            }
        }
    }
}
