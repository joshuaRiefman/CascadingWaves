package src;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Physics {
    static float strengthFactor = 6f;
    static float speedMultiplier = 2f;
    int size;
    Source[] sources;
    float wavelength = 10;
    float[][][] pathLengths;


    public Physics(int size, Source[] sources) {
        this.size = size;
        this.sources = sources;
        this.pathLengths= new float[size][size][this.sources.length];
    }

    void UpdatePhysics() {
//        for (Source source : this.sources) {
//            source.ProgressMovement(speedMultiplier / 100f);
//        }

        for (Source source : this.sources) {
            source.x += speedMultiplier * (source.v_x / 5);
            source.y += speedMultiplier * (source.v_y / 5);

            if (source.x > this.size || source.x < 0) {
                source.v_x *= -1;

            }

            if (source.y > this.size || source.y < 0) {
                source.v_y *= -1;
            }
        }


        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
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
}

