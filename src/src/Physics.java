package src;

import static java.lang.Math.*;

public class Physics {
    static float strengthFactor = 4f;
    static float speedMultiplier = 5;
    int size;
    Source[] sources;
    float wavelength = 15;
    float[][][] pathLengths;
    public static double pi = Math.toRadians(180);
    public double waveNumber;

    public Physics(int size, Source[] sources) {
        this.size = size;
        this.sources = sources;
        this.pathLengths= new float[size][size][this.sources.length];

        this.waveNumber = 2*pi / wavelength;
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

    static float GetPathLengthSquaredSum(float[] pathLengths) {
        float sum = pathLengths[0];
        for (int i = 1; i < pathLengths.length; i++) {
            sum += 255f * exp(-1f/160000f * pow(pathLengths[i], 2) );
        }
        return sum;
    }

    double GetIntensityAtPoint(int x, int y) {
        double pathLengthDifference = Physics.GetPathLengthDifference(this.pathLengths[x][y]);
        double strength = this.waveNumber*pathLengthDifference;
        return abs( ( strength / (2.0 * pi) ) * Physics.strengthFactor );
//        double pathLengthSquaredSum = Physics.GetPathLengthSquaredSum(this.pathLengths[x][y]);
//        return pathLengthSquaredSum * Physics.strengthFactor;
    }
}

