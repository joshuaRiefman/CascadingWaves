package src;

public class Pixel {
    public int red;
    public int green;
    public int blue;

    public Pixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int[] ToRGBA() {
        return new int[]{this.red, this.green, this.blue};
    }
}
