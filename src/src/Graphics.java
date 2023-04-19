package src;

import javax.swing.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

public class Graphics extends JFrame {
    int size;
    public BufferedImage image;

    public Graphics(int size) {
        this.size = size;
        this.image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        setTitle("Fluid Simulation");
        setSize(size, size);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new JLabel(new ImageIcon(image)));
        setVisible(true);
    }

    public void Render(Pixel[][] pixels) {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                int[] RGBA = pixels[i][j].ToRGBA();
                int rgb = (RGBA[0] << 16) | (RGBA[1] << 8) | RGBA[2];
                this.image.setRGB(i, j, rgb);
            }
        }

        this.repaint();
    }

    public static Pixel[][] CalculatePixels(Physics physics, Boolean normalize) {
        int width = physics.size;
        int height = physics.size;
        Pixel[][] pixels = new Pixel[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double rawValue = physics.GetIntensityAtPoint(i, j);
                int value = SquareNormalized(rawValue);

                int blue = normalize ? max(value, 0) : value;
                int green = normalize ? max(value - (2 * 255), 0) : 0;
                int red = normalize ? max(value - (4 * 255), 0) : 0;

                pixels[i][j] = new Pixel(red, green, blue);
            }
        }
        return pixels;
    }

    public static int SquareNormalized(double rawValue) {
        return (int)(pow((rawValue/255f),2) * 255);
    }
}