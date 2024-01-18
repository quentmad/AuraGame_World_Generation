package aura_game.app.GenerationMapS2;

import java.util.Random;

/**
 * Cette classe implémente l'algorithme de bruit de Perlin pour générer des valeurs de bruit cohérentes et continues.
 */
public class PerlinNoise {

    private int[] p;
    private int octaves;
    private long seed;

    /**
     * Constructeur de la classe PerlinNoise.
     *
     * @param octaves Le nombre d'octaves pour le bruit de Perlin.
     * @param seed    La graine pour la génération pseudo-aléatoire.
     */
    public PerlinNoise(int octaves, long seed) {
        this.octaves = octaves;
        this.seed = seed;
        generatePermutation();
    }

    private void generatePermutation() {
        p = new int[512];
        Random random = new Random(seed);

        for (int i = 0; i < 256; i++) {
            p[i] = i;
        }

        for (int i = 0; i < 256; i++) {
            int randomIndex = i + random.nextInt(256 - i);
            int temp = p[i];
            p[i] = p[randomIndex];
            p[randomIndex] = temp;
            p[i + 256] = p[i];
        }
    }

    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private static double grad(int hash, double x) {
        int h = hash & 15;
        double grad = 1 + (h & 7);
        if ((h & 8) != 0) grad = -grad;
        return (grad * x);
    }

    /**
     * Génère une valeur de bruit Perlin bidimensionnelle à la position spécifiée (x, y).
     *
     * @param point Le point 2D pour lequel générer le bruit.
     * @return La valeur de bruit Perlin entre -1 et 1.
     */
    public double noise(double[] point) {
        int[] coordinates = {(int) Math.floor(point[0]) & 255, (int) Math.floor(point[1]) & 255};
        double[] relativeCoordinates = {point[0] - Math.floor(point[0]), point[1] - Math.floor(point[1])};
        double[] fadeValues = {fade(relativeCoordinates[0]), fade(relativeCoordinates[1])};

        int[] indices = new int[4];
        for (int i = 0; i < 2; i++) {
            indices[i] = p[coordinates[i]] + coordinates[1 - i];
            indices[i + 2] = p[coordinates[i] + 1] + coordinates[1 - i];
        }

        double[] gradValues = new double[4];
        for (int i = 0; i < 4; i++) {
            gradValues[i] = grad(p[indices[i]], relativeCoordinates[0] - i % 2) +
                    grad(p[indices[i + 2]], relativeCoordinates[0] - i % 2 - 1);
        }

        double xLerp1 = lerp(fadeValues[0], gradValues[0], gradValues[1]);
        double xLerp2 = lerp(fadeValues[0], gradValues[2], gradValues[3]);
        return lerp(fadeValues[1], xLerp1, xLerp2);
    }
}
