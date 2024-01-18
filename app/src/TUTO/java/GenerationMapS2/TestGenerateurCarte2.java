package aura_game.app.GenerationMapS2;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.maps.tiled.TiledMap;

import static aura_game.app.GenerationMapS2.GenerateurCarte.*;

public class TestGenerateurCarte2 {
/*
    public static void testGenerateWorld(int[] weights, long randomSeed) {
        WorldDrawer worldDrawer = new WorldDrawer();
        World world = new World(WORLD_X, WORLD_Y, randomSeed);
        TiledMap tileMap = world.getTiledMap(weights);
        worldDrawer.draw(tileMap, true);
    }

    public static void testEmerge(int[] targetWeights, long randomSeed) {
        WorldDrawer worldDrawer = new WorldDrawer();
        World world = new World(WORLD_X, WORLD_Y, randomSeed);
        int[] weights = {targetWeights[OCEAN3] - 1, 0, 0, 0, 0, 0, 0};
        boolean done = false;

        while (!done) {
            TiledMap tileMap = world.getTiledMap(weights);
            worldDrawer.draw(tileMap, false);

            if (weights[OCEAN3] < targetWeights[OCEAN3]) {
                weights[OCEAN3]++;
            } else if (weights[OCEAN2] < targetWeights[OCEAN2]) {
                weights[OCEAN2]++;
            } else if (weights[OCEAN1] < targetWeights[OCEAN1]) {
                weights[OCEAN1]++;
            } else if (weights[BEACH] < targetWeights[BEACH]) {
                weights[BEACH]++;
            } else if (weights[GRASS] < targetWeights[GRASS]) {
                weights[GRASS]++;
            } else if (weights[MOUNTAIN] < targetWeights[MOUNTAIN]) {
                weights[MOUNTAIN]++;
            } else if (weights[SNOW] < targetWeights[SNOW]) {
                weights[SNOW]++;
            } else {
                done = true;
            }
        }

        done = false;
        targetWeights = new int[]{weights[OCEAN3] - 1, 0, 0, 0, 0, 0, 0};

        while (!done) {
            TiledMap tileMap = world.getTiledMap(weights);
            worldDrawer.draw(tileMap, false);

            if (weights[SNOW] >= targetWeights[SNOW] && weights[SNOW] > 0) {
                weights[SNOW]--;
            } else if (weights[MOUNTAIN] >= targetWeights[MOUNTAIN] && weights[MOUNTAIN] > 0) {
                weights[MOUNTAIN]--;
            } else if (weights[GRASS] >= targetWeights[GRASS] && weights[GRASS] > 0) {
                weights[GRASS]--;
            } else if (weights[BEACH] >= targetWeights[BEACH] && weights[BEACH] > 0) {
                weights[BEACH]--;
            } else if (weights[OCEAN1] >= targetWeights[OCEAN1] && weights[OCEAN1] > 0) {
                weights[OCEAN1]--;
            } else if (weights[OCEAN2] >= targetWeights[OCEAN2] && weights[OCEAN2] > 0) {
                weights[OCEAN2]--;
            } else if (weights[OCEAN3] >= targetWeights[OCEAN3] && weights[OCEAN3] > 0) {
                weights[OCEAN3]--;
            } else {
                done = true;
            }
        }
    }*/
}
