package aura_game.app.oldS2;


import java.util.Arrays;

public class World {

    private double[][] noiseMap;
    private double minValue;
    private double maxValue;

    public World(int sizeX, int sizeY, PerlinNoise perlinNoise) {
        generateNoiseMap(sizeX, sizeY, perlinNoise);

        // Get min and max noise values
        double[] flatList = Arrays.stream(noiseMap).flatMapToDouble(Arrays::stream).toArray();
        this.minValue = Arrays.stream(flatList).min().orElse(0);
        this.maxValue = Arrays.stream(flatList).max().orElse(0);
    }

    private void generateNoiseMap(int sizeX, int sizeY, PerlinNoise perlinNoise) {
        noiseMap = new double[sizeY + 1][sizeX + 1];

        int xPix = sizeX + 1;
        int yPix = sizeY + 1;
        for (int j = 0; j < yPix; j++) {
            double[] row = new double[xPix];
            for (int i = 0; i < xPix; i++) {
                double noiseVal = perlinNoise.noise(i / (double) xPix, j / (double) yPix);
                row[i] = noiseVal;
            }
            noiseMap[j] = row;
        }
    }

    public double[][] getNoiseMap() {
        return noiseMap;
    }

    public int[][] getTiledMap(int[] weights) {
        double totalWeights = Arrays.stream(weights).sum();
        double totalRange = maxValue - minValue;

        // calculate maximum height for each terrain type, based on weight values
        double[] maxTerrainHeights = new double[Config.ALL_TERRAIN_TYPES.length];
        double previousHeight = minValue;
        for (int terrainType : Config.ALL_TERRAIN_TYPES) {
            double height = totalRange * (weights[terrainType] / totalWeights) + previousHeight;
            maxTerrainHeights[terrainType] = height;
            previousHeight = height;
        }
        maxTerrainHeights[Config.SNOW] = maxValue;

        int[][] mapInt = new int[noiseMap.length][noiseMap[0].length];

        for (int i = 0; i < noiseMap.length; i++) {
            for (int j = 0; j < noiseMap[0].length; j++) {
                double value = noiseMap[i][j];
                for (int terrainType : Config.ALL_TERRAIN_TYPES) {
                    if (value <= maxTerrainHeights[terrainType]) {
                        mapInt[i][j] = terrainType;
                        break;
                    }
                }
            }
        }

        return mapInt;
    }
}















/*

import java.util.Arrays;
import java.util.Random;

public class World {

    private double[][] noiseMap;
    private double minValue;
    private double maxValue;

    public World(int sizeX, int sizeY, long randomSeed) {
        generateNoiseMap(sizeX, sizeY, randomSeed);

        // Get min and max noise values
        double[] flatList = Arrays.stream(noiseMap).flatMapToDouble(Arrays::stream).toArray();
        this.minValue = Arrays.stream(flatList).min().orElse(0);
        this.maxValue = Arrays.stream(flatList).max().orElse(0);
    }

    private void generateNoiseMap(int sizeX, int sizeY, long randomSeed) {
        noiseMap = new double[sizeY + 1][sizeX + 1];

        PerlinNoise noise1 = new PerlinNoise(3, randomSeed);
        PerlinNoise noise2 = new PerlinNoise(6, randomSeed);
        PerlinNoise noise3 = new PerlinNoise(12, randomSeed);
        PerlinNoise noise4 = new PerlinNoise(24, randomSeed);

        int xPix = sizeX + 1;
        int yPix = sizeY + 1;
        for (int j = 0; j < yPix; j++) {
            double[] row = new double[xPix];
            for (int i = 0; i < xPix; i++) {
                double noiseVal = noise1.noise(new double[]{i / (double) xPix, j / (double) yPix});
                noiseVal += 0.5 * noise2.noise(new double[]{i / (double) xPix, j / (double) yPix});
                noiseVal += 0.25 * noise3.noise(new double[]{i / (double) xPix, j / (double) yPix});
                noiseVal += 0.125 * noise4.noise(new double[]{i / (double) xPix, j / (double) yPix});
                row[i] = noiseVal;
            }
            noiseMap[j] = row;
        }
    }

    public double[][] getNoiseMap() {
        return noiseMap;
    }

    public int[][] getTiledMap(int[] weights) {
        double totalWeights = Arrays.stream(weights).sum();
        double totalRange = maxValue - minValue;

        // calculate maximum height for each terrain type, based on weight values
        double[] maxTerrainHeights = new double[Config.ALL_TERRAIN_TYPES.length];
        double previousHeight = minValue;
        for (int terrainType : Config.ALL_TERRAIN_TYPES) {
            double height = totalRange * (weights[terrainType] / totalWeights) + previousHeight;
            maxTerrainHeights[terrainType] = height;
            previousHeight = height;
        }
        maxTerrainHeights[Config.SNOW] = maxValue;

        int[][] mapInt = new int[noiseMap.length][noiseMap[0].length];

        for (int i = 0; i < noiseMap.length; i++) {
            for (int j = 0; j < noiseMap[0].length; j++) {
                double value = noiseMap[i][j];
                for (int terrainType : Config.ALL_TERRAIN_TYPES) {
                    if (value <= maxTerrainHeights[terrainType]) {
                        mapInt[i][j] = terrainType;
                        break;
                    }
                }
            }
        }

        return mapInt;
    }
}
*/