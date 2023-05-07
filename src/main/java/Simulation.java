package main.java;

import java.time.LocalTime;
import java.lang.System;

import static java.lang.Math.max;

public class Simulation {
    static int WORLD_SIZE = 512;
    static int SOURCE_COUNT = 16;
    static int SIMULATION_LENGTH = 2000; //
    static boolean NORMALIZE_GRAPHICS = true;
    static final long TARGET_FRAMERATE = 30;

    public static void main(String[] args) throws Exception {
        GraphicsEngine graphicsEngine = new GraphicsEngine(WORLD_SIZE);
        PhysicsEngine physicsEngine = new PhysicsEngine(WORLD_SIZE, Source.CreateSources(SOURCE_COUNT, WORLD_SIZE));
        FragmentShader shader = FragmentShader.Bind(physicsEngine, NORMALIZE_GRAPHICS);

        long time = System.currentTimeMillis();
        long tick_length = 1000 / TARGET_FRAMERATE;

        for (int loops = 0; loops < SIMULATION_LENGTH; loops++) {
            physicsEngine.UpdatePhysics();
            graphicsEngine.Render(shader.CalculatePixels());

            long deltaTime = time - System.currentTimeMillis();
            long waitTime = max(0, tick_length - deltaTime);
            Thread.sleep(waitTime);
            time = System.currentTimeMillis();
        }

        System.out.println("Simulation completed.");
    }
}
