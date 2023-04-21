package main.java;

import javax.swing.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

public class GraphicsEngine extends JFrame {
    private int windowSize;
    private BufferedImage image;

    public GraphicsEngine(int windowSize) {
        this.windowSize = windowSize;
        this.image = new BufferedImage(windowSize, windowSize, BufferedImage.TYPE_INT_RGB);

        setTitle("Cascading Waves");
        setSize(windowSize, windowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new JLabel(new ImageIcon(image)));
        setVisible(true);
    }

    public void Render(Pixel[][] pixels) {
        for (int i = 0; i < this.windowSize; i++) {
            for (int j = 0; j < this.windowSize; j++) {
                int[] RGBA = pixels[i][j].ToRGBA();
                int rgb = (RGBA[0] << 16) | (RGBA[1] << 8) | RGBA[2];
                this.image.setRGB(i, j, rgb);
            }
        }

        this.repaint();
    }
}