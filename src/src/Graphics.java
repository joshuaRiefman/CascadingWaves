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

    public static Pixel[][] CalculatePixels(Physics physics) {
        double pi  = Math.toRadians(180);
        int width = physics.size;
        int height = physics.size;
        Pixel[][] pixels = new Pixel[width][height];

        double k = 2*pi / physics.wavelength;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double pathLengthDifference = Physics.GetPathLengthDifference(physics.pathLengths[i][j]);
                double strength = k*pathLengthDifference;

                int value = (int)abs(( (strength / ( 2.0 * pi ) ) * Physics.strengthFactor));
                value = (int) (pow((value/255f),2) * 255);

                int blue = max(value, 0);
                int green = max(value - (2 * 255), 0);
                int red = max(value - (4 * 255), 0);

                pixels[i][j] = new Pixel(red, green, blue);
            }
        }
        return pixels;
    }
}