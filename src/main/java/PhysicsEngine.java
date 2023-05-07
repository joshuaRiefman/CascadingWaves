package main.java;

import static java.lang.Math.*;

public class PhysicsEngine {
    private static final float STRENGTH_COEFFICIENT = 7f;
    private static final float SPEED_COEFFICIENT = 1f;
    private static final float WAVELENGTH = 20f;
    private static final float PI = (float)Math.toRadians(180);
    private Source[] sources;
    private float[][][] pathLengths;
    public int worldSize;
    public float waveNumber;

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
    float GetSourcePowerSum(int x, int y, Source[] sources) {
        float power = 0;
        for (Source source: sources) {
            float distance = GetPathLength(x, y, source);
            power += sin(this.waveNumber * distance);
        }
        return power;
    }

    float GetIntensityAtPoint(int x, int y) {
        float sourcePowerSum = STRENGTH_COEFFICIENT * GetSourcePowerSum(x, y, sources);
        return (float)pow(sourcePowerSum, 2);
    }
}

