package main.java;

import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.pow;

public class FragmentShader {
    public boolean normalize;
    public PhysicsEngine physicsEngine;

    public Pixel[][] CalculatePixels() throws Exception {
        if (this.physicsEngine == null) {
            throw new Exception("Physics Engine is not bound!");
        }

        int width = this.physicsEngine.worldSize;
        int height = this.physicsEngine.worldSize;
        Pixel[][] pixels = new Pixel[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double rawValue = this.physicsEngine.GetIntensityAtPoint(i, j);
                int value = SquareNormalized(rawValue);
                value = (int) rawValue;
                int blue = this.normalize ? max(value, 0) : value;
                int green = this.normalize ? max(value - (2 * 255), 0) : 0;
                int red = this.normalize ? max(value - (3 * 255), 0) : 0;

                pixels[i][j] = new Pixel(red, green, blue);
            }
        }
        return pixels;
    }

    public static FragmentShader Bind(PhysicsEngine physicsEngine, boolean normalize) {
        FragmentShader shader = new FragmentShader();

        shader.physicsEngine = physicsEngine;
        shader.normalize = normalize;

        return shader;
    }

    public static int SquareNormalized(double rawValue) {
        return (int)(pow((rawValue/255f),2) * 255);
    }
}
