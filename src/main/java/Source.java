package main.java;

import java.util.Random;

public class Source {
    public int x;
    public int y;
    public float v_x;
    public float v_y;
    public int[][] points;
    public float position;

    public Source(int x, int y, int v_x, int v_y) {
        this.x = x;
        this.y = y;
        this.v_x = v_x;
        this.v_y = v_y;
    }

    public static Source[] CreateSources(int numSources, int size) {
        Random random = new Random();
        Source[] sources = new Source[numSources];

        for (int i = 0; i < numSources; i++) {
            sources[i] = new Source(random.nextInt(size),random.nextInt(size), (int) (random.nextFloat(10f)), (int) (random.nextFloat(10f)));
        }

        return sources;
    }

    // Bezier Movement (currently unstable and not in use)

    public Source(int size) {
        Random random = new Random();

        this.points = new int[4][2];
        for (int i = 0; i < 3; i++) {
            this.points[i] = new int[]{random.nextInt(size), random.nextInt(size)};
        }
        this.points[3] = this.points[0];

        this.x = this.points[0][0];
        this.y = this.points[0][1];
    }

    public void ProgressMovement(float v) {
        this.position += v;

        while (this.position > 1) {
            this.position -= 1;
        }

        int[] position = BezierPosition(this.points[0], this.points[1], this.points[2], this.points[3], this.position);

        this.x = position[0];
        this.y = position[1];
    }

    public static Source[] CreateSourcesBezier(int numSources, int size) {
        Source[] sources = new Source[numSources];

        for (int i = 0; i < numSources; i++) {
            sources[i] = new Source(2*size);
        }

        return sources;
    }

    static int[] BezierPosition(int[] x1, int[] x2, int[] x3, int[] x4, float t) {
        int[] p0 = QuadraticLinearInterpolate(x1, x2, x3, t);
        int[] p1 = QuadraticLinearInterpolate(x2, x3, x4, t);
        return LinearInterpolate(p0, p1, t);
    }

    static int[] QuadraticLinearInterpolate(int[] x1, int[] x2, int[] x3, float t) {
        int[] p0 = LinearInterpolate(x1, x2, t);
        int[] p1 = LinearInterpolate(x1, x3, t);
        return LinearInterpolate(p0, p1, t);
    }

    static int[] LinearInterpolate(int[] x1, int[] x2, float t) {
        int pos_x = (int) (x1[0] + (x2[0] - x1[0]) * t);
        int pos_y = (int) (x1[1] + (x2[1] - x1[1]) * t);
        return new int[]{pos_x, pos_y};
    }
}
