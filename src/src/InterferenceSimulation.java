package src;

public class InterferenceSimulation {
    static int size = 1000;
    static int sourceCount = 5;
    static int simulationLengthInSeconds = 20;

    public static void main(String[] args) throws InterruptedException {
        Graphics graphics = new Graphics(size);
        Source[] sources = Source.CreateSources(sourceCount, size);
        Physics physics = new Physics(size, sources);
        Pixel[][] pixels;

        for (int loops = 0; loops < simulationLengthInSeconds*240; loops++) {
            Thread.sleep(1000/240);

            physics.UpdatePhysics();
            pixels = Graphics.CalculatePixels(physics, true);

            graphics.Render(pixels);
        }
    }
}
