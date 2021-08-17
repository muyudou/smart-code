package rejectionsample;

import java.util.Random;

/**
 * 需要对圆形，扇形，环形面积有认识
 * https://leetcode-cn.com/problems/generate-random-point-in-a-circle
 */
public class RandPointInCircle {
    private double radius;
    private double x_center;
    private double y_center;
    private Random random = new Random();

    public RandPointInCircle(double radius, double x_center, double y_center) {
        this.radius = radius;
        this.x_center = x_center;
        this.y_center = y_center;
    }

    public double[] randPoint() {
        return null;
    }
}
