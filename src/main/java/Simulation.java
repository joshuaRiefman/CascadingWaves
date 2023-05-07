package main.java;

public class Simulation {
    static int WORLD_SIZE = 256;
    static int SOURCE_COUNT = 6;
    static int SIMULATION_LENGTH = 20;
    static boolean NORMALIZE_GRAPHICS = true;
    static final long TICK_LENGTH = 8;

    public static void main(String[] args) throws Exception {
        GraphicsEngine graphicsEngine = new GraphicsEngine(WORLD_SIZE);
        PhysicsEngine physicsEngine = new PhysicsEngine(WORLD_SIZE, Source.CreateSources(SOURCE_COUNT, WORLD_SIZE));
        FragmentShader shader = FragmentShader.Bind(physicsEngine, NORMALIZE_GRAPHICS);

        for (int loops = 0; loops < 1000; loops++) {
            Thread.sleep(TICK_LENGTH);

            physicsEngine.UpdatePhysics();
            graphicsEngine.Render(shader.CalculatePixels());
        }
    }
}
