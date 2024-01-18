package aura_game.app.GenerationMapS1;

import java.util.Random;
import com.badlogic.gdx.math.Vector2;

/**
 * Cette classe génère des cartes de bruit aléatoire 2D en utilisant l'algorithme de bruit de Perlin
 * avec des octaves pour créer des variations de hauteur réalistes.
 */
public class MapGenerator {

    /**
     * Génère une carte de bruit aléatoire 2D en utilisant l'algorithme de bruit de Perlin.
     *
     * @param mapWidth Largeur de la carte.
     * @param mapHeight Hauteur de la carte.
     * @param seed Graine pour la génération aléatoire.
     * @param scale Échelle de la carte.
     * @param octaves Nombre d'octaves pour créer des détails.
     * @param persistance Facteur de persistance (entre 0 et 1) pour atténuer l'amplitude des octaves successifs.
     * @param lacunarity Facteur de lacunarité (> 1) pour augmenter la fréquence des octaves successifs.
     * @param offset Décalage de la carte.
     * @return Une carte de bruit aléatoire normalisée entre 0 et 1.
     */
    public static float[][] GenerateNoiseMap(int mapWidth, int mapHeight, int seed, float scale, int octaves, float persistance, float lacunarity, Vector2 offset) {
        float[][] noiseMap = new float[mapWidth][mapHeight];

        Random prng = new Random(seed);
        Vector2[] octaveOffsets = new Vector2[octaves];
        for (int i = 0; i < octaves; i++) {
            float offsetX = prng.nextFloat() * 100000 - 50000 + offset.x;
            float offsetY = prng.nextFloat() * 100000 - 50000 + offset.y;
            octaveOffsets[i] = new Vector2(offsetX, offsetY);
        }

        if (scale <= 0) {
            scale = 0.0001f;
        }

        float maxNoiseHeight = Float.MIN_VALUE;
        float minNoiseHeight = Float.MAX_VALUE;

        float halfWidth = mapWidth / 2f;
        float halfHeight = mapHeight / 2f;

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                float amplitude = 1;
                float frequency = 1;
                float noiseHeight = 0;

                for (int i = 0; i < octaves; i++) {
                    float sampleX = (x - halfWidth) / scale * frequency + octaveOffsets[i].x;
                    float sampleY = (y - halfHeight) / scale * frequency + octaveOffsets[i].y;

                    float perlinValue = (float) (PerlinNoise.noise(sampleX, sampleY) * 2 - 1);
                    noiseHeight += perlinValue * amplitude;

                    amplitude *= persistance;
                    frequency *= lacunarity;
                }

                if (noiseHeight > maxNoiseHeight) {
                    maxNoiseHeight = noiseHeight;
                } else if (noiseHeight < minNoiseHeight) {
                    minNoiseHeight = noiseHeight;
                }
                noiseMap[x][y] = noiseHeight;
            }
        }

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                noiseMap[x][y] = (noiseMap[x][y] - minNoiseHeight) / (maxNoiseHeight - minNoiseHeight);
            }
        }

        return noiseMap;
    }

    /**
     * Modifie en place une carte de bruit aléatoire 2D avec l'algorithme de bruit de Perlin.
     *
     * @param noiseMap La carte à modifier.
     * @param mapWidth Largeur de la carte.
     * @param mapHeight Hauteur de la carte.
     * @param seed Graine pour la génération aléatoire.
     * @param scale Échelle de la carte.
     * @param octaves Nombre d'octaves pour créer des détails.
     * @param persistance Facteur de persistance (entre 0 et 1) pour atténuer l'amplitude des octaves successifs.
     * @param lacunarity Facteur de lacunarité (> 1) pour augmenter la fréquence des octaves successifs.
     * @param offset Décalage de la carte.
     */
    public static void GenerateNoiseMap(float[][] noiseMap, int mapWidth, int mapHeight, int seed, float scale, int octaves, float persistance, float lacunarity, Vector2 offset) {
        Random prng = new Random(seed);
        Vector2[] octaveOffsets = new Vector2[octaves];
        for (int i = 0; i < octaves; i++) {
            float offsetX = prng.nextFloat() * 100000 - 50000 + offset.x;
            float offsetY = prng.nextFloat() * 100000 - 50000 + offset.y;
            octaveOffsets[i] = new Vector2(offsetX, offsetY);
        }

        if (scale <= 0) {
            scale = 0.0001f;
        }

        float maxNoiseHeight = Float.MIN_VALUE;
        float minNoiseHeight = Float.MAX_VALUE;

        float halfWidth = mapWidth / 2f;
        float halfHeight = mapHeight / 2f;

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                float amplitude = 1;
                float frequency = 1;
                float noiseHeight = 0;

                for (int i = 0; i < octaves; i++) {
                    float sampleX = (x - halfWidth) / scale * frequency + octaveOffsets[i].x;
                    float sampleY = (y - halfHeight) / scale * frequency + octaveOffsets[i].y;

                    float perlinValue = (float) (PerlinNoise.noise(sampleX, sampleY) * 2 - 1);
                    noiseHeight += perlinValue * amplitude;

                    amplitude *= persistance;
                    frequency *= lacunarity;
                }

                if (noiseHeight > maxNoiseHeight) {
                    maxNoiseHeight = noiseHeight;
                } else if (noiseHeight < minNoiseHeight) {
                    minNoiseHeight = noiseHeight;
                }
                noiseMap[x][y] = noiseHeight;
            }
        }

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                noiseMap[x][y] = (noiseMap[x][y] - minNoiseHeight) / (maxNoiseHeight - minNoiseHeight);
            }
        }
    }
}
