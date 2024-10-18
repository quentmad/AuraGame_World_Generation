package aura_game.app;

import java.util.Random;

/**
 * Classe générant un bruit de Perlin en 2D basé sur une seed.
 */
public class PerlinNoiseV3 {
    private final int[] permutation; // Tableau de permutation pour les gradients
    private static final int PERMUTATION_SIZE = 512;

    /**
     * Crée un générateur de bruit de Perlin à partir d'une seed donnée.
     *
     * @param seed La graine aléatoire utilisée pour générer les valeurs de bruit.
     */
    public PerlinNoiseV3(long seed) {
        permutation = new int[PERMUTATION_SIZE];
        Random random = new Random(seed);

        // Génère une permutation aléatoire des valeurs [0, 255]
        int[] p = new int[256];
        for (int i = 0; i < 256; i++) {
            p[i] = i;
        }

        // Mélange l'array
        for (int i = 255; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = p[i];
            p[i] = p[j];
            p[j] = tmp;
        }

        // Remplir le tableau de permutation deux fois
        for (int i = 0; i < 256; i++) {
            permutation[i] = p[i];
            permutation[i + 256] = p[i];
        }
    }

    /**
     * Génère un bruit de Perlin 2D à une position donnée.
     *
     * @param x La coordonnée X dans l'espace.
     * @param y La coordonnée Y dans l'espace.
     * @return La valeur du bruit de Perlin (entre -1 et 1).
     */
    public float noise(float x, float y) {
        // Trouve les coordonnées du coin inférieur
        int xi = (int) Math.floor(x) & 255;
        int yi = (int) Math.floor(y) & 255;

        // Coordonnées internes dans la cellule
        float xf = x - (int) Math.floor(x);
        float yf = y - (int) Math.floor(y);

        // Appliquer le lissage avec une fonction de fade
        float u = fade(xf);
        float v = fade(yf);

        // Obtenir les vecteurs de gradients
        int aa = permutation[permutation[xi] + yi];
        int ab = permutation[permutation[xi] + yi + 1];
        int ba = permutation[permutation[xi + 1] + yi];
        int bb = permutation[permutation[xi + 1] + yi + 1];

        // Calculer les contributions de chaque coin
        float x1, x2, y1, y2;
        x1 = lerp(grad(aa, xf, yf), grad(ba, xf - 1, yf), u);
        x2 = lerp(grad(ab, xf, yf - 1), grad(bb, xf - 1, yf - 1), u);
        y1 = lerp(x1, x2, v);

        return y1;
    }

    /**
     * Génère un bruit de Perlin 2D sur une zone donnée avec un facteur d'échelle.
     *
     * @param x La coordonnée X.
     * @param y La coordonnée Y.
     * @param scale L'échelle appliquée (plus c'est petit, plus le bruit est détaillé).
     * @return La valeur du bruit (entre -1 et 1).
     */
    public float noise(float x, float y, float scale) {
        return noise(x * scale, y * scale);
    }

    /**
     * Fonction de lissage (fade) utilisée pour rendre les transitions plus douces.
     *
     * @param t Le paramètre à lisser.
     * @return Le paramètre après lissage.
     */
    private float fade(float t) {
        return t * t * t * (t * (t * 6 - 15) + 10); // Fonction fade pour lisser les transitions
    }

    /**
     * Interpolation linéaire entre deux valeurs.
     *
     * @param a Première valeur.
     * @param b Deuxième valeur.
     * @param t Facteur d'interpolation (entre 0 et 1).
     * @return Valeur interpolée entre a et b.
     */
    private float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    /**
     * Calcul du produit scalaire entre un vecteur de gradient et le vecteur de la position.
     *
     * @param hash Le hash représentant un vecteur de gradient.
     * @param x La coordonnée X.
     * @param y La coordonnée Y.
     * @return Le produit scalaire entre le gradient et la position.
     */
    private float grad(int hash, float x, float y) {
        // Le hash est utilisé pour déterminer le vecteur de gradient
        int h = hash & 7;  // On ne garde que les 3 derniers bits
        float u = h < 4 ? x : y;
        float v = h < 4 ? y : x;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }
}

