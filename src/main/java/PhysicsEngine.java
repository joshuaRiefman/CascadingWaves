package main.java;

import static java.lang.Math.*;

public class PhysicsEngine {
    private static final float STRENGTH_COEFFICIENT = 400f;
    private static final float FALLOFF_COEFFICIENT = 1f;
    private static final float SPEED_COEFFICIENT = 2f;
    private static final float WAVELENGTH = 15;
    private static final double PI = Math.toRadians(180);
    private Source[] sources;
    private float[][][] pathLengths;
    public int worldSize;
    public double waveNumber;

    public PhysicsEngine(int worldSize, Source[] sources) {
        this.worldSize = worldSize;
        this.sources = sources;
        this.pathLengths= new float[worldSize][worldSize][this.sources.length];

        this.waveNumber = 2* PI / WAVELENGTH;
    }

    void UpdatePhysics() {
        for (Source source : this.sources) {
            source.x += SPEED_COEFFICIENT * (source.v_x / 5);
            source.y += SPEED_COEFFICIENT * (source.v_y / 5);

            if (source.x > this.worldSize || source.x < 0) {
                source.v_x *= -1;

            }

            if (source.y > this.worldSize || source.y < 0) {
                source.v_y *= -1;
            }
        }


        for (int i = 0; i < this.worldSize; i++) {
            for (int j = 0; j < this.worldSize; j++) {
                for (int k = 0; k < this.sources.length; k++) {
                    this.pathLengths[i][j][k] = GetPathLength(i, j, this.sources[k]);
                }
            }
        }
    }

    static float GetPathLength(int x, int y, Source source) {
        return (float) sqrt(pow(source.x - x, 2) + pow(source.y - y, 2));
    }

    double GetSourcePowerSum(int x, int y, Source[] sources) {
        double power = 0;
        for (Source source : sources) {
            double distance = GetPathLength(x, y, source);
            power += STRENGTH_COEFFICIENT * GaussianNormal(distance);
            System.out.println("Power is: " + distance);
        }
        return abs(power);
    }

    double GetIntensityAtPoint(int x, int y) {
        return this.GetSourcePowerSum(x, y, sources);
    }

    double GaussianNormal(double x) { return exp(-pow(x / (FALLOFF_COEFFICIENT * 56f), 2)); }
}

