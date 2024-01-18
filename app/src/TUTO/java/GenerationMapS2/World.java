package aura_game.app.GenerationMapS2;

import java.util.ArrayList;
import java.util.List;

import static aura_game.app.GenerationMapS2.GenerateurCarte.ALL_TERRAIN_TYPES;
import static aura_game.app.GenerationMapS2.GenerateurCarte.SNOW;

public class World {

    private List<List<Double>> noiseMap;
    private double minValue;
    private double maxValue;

    public World(int sizeX, int sizeY, long randomSeed) {
        generateNoiseMap(sizeX, sizeY, randomSeed);
        initializeMinMaxValues();
    }

    private void generateNoiseMap(int sizeX, int sizeY, long randomSeed) {
        noiseMap = new ArrayList<>();

        PerlinNoise noise1 = new PerlinNoise(3, randomSeed);
        PerlinNoise noise2 = new PerlinNoise(6, randomSeed);
        PerlinNoise noise3 = new PerlinNoise(12, randomSeed);
        PerlinNoise noise4 = new PerlinNoise(24, randomSeed);

        int xPix = sizeX + 1;
        int yPix = sizeY + 1;

        for (int j = 0; j < yPix; j++) {
            List<Double> row = new ArrayList<>();
            for (int i = 0; i < xPix; i++) {
                double noiseVal = noise1.noise(new double[]{(double) i / xPix, (double) j / yPix});
                noiseVal += 0.5 * noise2.noise(new double[]{(double) i / xPix, (double) j / yPix});
                noiseVal += 0.25 * noise3.noise(new double[]{(double) i / xPix, (double) j / yPix});
                noiseVal += 0.125 * noise4.noise(new double[]{(double) i / xPix, (double) j / yPix});
                row.add(noiseVal);
            }
            noiseMap.add(row);
        }
    }

    private void initializeMinMaxValues() {
        List<Double> flatList = new ArrayList<>();
        for (List<Double> sublist : noiseMap) {
            flatList.addAll(sublist);
        }
        minValue = flatList.stream().min(Double::compareTo).orElse(0.0);
        maxValue = flatList.stream().max(Double::compareTo).orElse(0.0);
    }

    public List<List<Double>> getNoiseMap() {
        return noiseMap;
    }

    public List<List<Integer>> getTiledMap(int[] weights) {
        double totalWeights = 0;
        for (int weight : weights) {
            totalWeights += weight;
        }

        double totalRange = maxValue - minValue;

        List<Double> maxTerrainHeights = new ArrayList<>();
        double previousHeight = minValue;

        for (int terrainType : ALL_TERRAIN_TYPES) {
            double height = totalRange * (weights[terrainType] / totalWeights) + previousHeight;
            maxTerrainHeights.add(height);
            previousHeight = height;
        }

        maxTerrainHeights.set(SNOW, maxValue);

        List<List<Integer>> mapInt = new ArrayList<>();

        for (List<Double> row : noiseMap) {
            List<Integer> mapRow = new ArrayList<>();
            for (double value : row) {
                for (int terrainType : ALL_TERRAIN_TYPES) {
                    if (value <= maxTerrainHeights.get(terrainType)) {
                        mapRow.add(terrainType);
                        break;
                    }
                }
            }
            mapInt.add(mapRow);
        }

        return mapInt;
    }
}
