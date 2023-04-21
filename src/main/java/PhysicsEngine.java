package main.java;

import static java.lang.Math.*;

public class PhysicsEngine {
    private static final float STRENGTH_COEFFICIENT = 4f;
    private static final float SPEED_COEFFICIENT = 5;
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
//        for (Source source : this.sources) {
//            source.ProgressMovement(speedMultiplier / 100f);
//        }

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

    static float GetPathLengthDifference(float[] pathLengths) {
        float difference = pathLengths[0];
        for (int i = 1; i < pathLengths.length; i++) {
            difference -= pathLengths[i];
        }
        return difference;
    }

    static float GetPathLengthSquaredSum(float[] pathLengths) {
        float sum = pathLengths[0];
        for (int i = 1; i < pathLengths.length; i++) {
            sum += 255f * exp(-1f/160000f * pow(pathLengths[i], 2) );
        }
        return sum;
    }

    double GetIntensityAtPoint(int x, int y) {
        double pathLengthDifference = PhysicsEngine.GetPathLengthDifference(this.pathLengths[x][y]);
        double strength = this.waveNumber*pathLengthDifference;
        return abs( ( strength / (2.0 * PI) ) * PhysicsEngine.STRENGTH_COEFFICIENT);
//        double pathLengthSquaredSum = Physics.GetPathLengthSquaredSum(this.pathLengths[x][y]);
//        return pathLengthSquaredSum * Physics.strengthFactor;
    }
}

