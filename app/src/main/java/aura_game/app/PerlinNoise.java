package aura_game.app;

import java.util.Random;

/**
 * Classe représentant une génération de bruit de Perlin pour créer une carte texturée.
 */
public class PerlinNoise {
    private long seed;
    private Random rand;
    private double z;

    /**
     * Constructeur de la classe PerlinNoise.
     * Initialise la taille par défaut, la graine aléatoire, les textures et le lot de sprites.
     */
    public PerlinNoise() {
        seed = System.currentTimeMillis(); // ((long)(Math.random() * 400) + 1);
        rand = new Random(seed);
        z = rand.nextDouble();
        System.out.println("random :" + z);
    }


    public double getZ(){return z;}

    /**
     * Méthode pour générer une valeur de bruit de Perlin en trois dimensions.
     *
     * @param x Coordonnée x.
     * @param y Coordonnée y.
     * @param z Coordonnée z.
     * @return Valeur de bruit générée entre 0 et 1.
     */
    public double noise(double x, double y, double z)
    {
        // Find the unit cube that contains the point
        int X = (int)Math.floor(x) & 255;
        int Y = (int)Math.floor(y) & 255;
        int Z = (int)Math.floor(z) & 255;

        // Find relative x, y,z of point in cube
        x -= Math.floor(x);
        y -= Math.floor(y);
        z -= Math.floor(z);

        // Compute fade curves for each of x, y, z
        double u = fade(x);
        double v = fade(y);
        double w = fade(z);

        // Hash coordinates of the 8 cube corners
        int A = p[X] + Y;
        int AA = p[A] + Z;
        int AB = p[A + 1] + Z;
        int B = p[X + 1] + Y;
        int BA = p[B] + Z;
        int BB = p[B + 1] + Z;

        // Add blended results from 8 corners of cube
        double res = lerp(
                w,
                lerp(v, lerp(u, grad(p[AA], x, y, z), grad(p[BA], x - 1, y, z)),
                        lerp(u, grad(p[AB], x, y - 1, z), grad(p[BB], x - 1, y - 1, z))),
                lerp(v, lerp(u, grad(p[AA + 1], x, y, z - 1), grad(p[BA + 1], x - 1, y, z - 1)),
                        lerp(u, grad(p[AB + 1], x, y - 1, z - 1), grad(p[BB + 1], x - 1, y - 1, z - 1))));
        return (res + 1.0) / 2.0;
    }

    /**
     * Méthode pour appliquer une fonction de fondu à une valeur.
     *
     * @param t Valeur à appliquer à la fonction de fondu.
     * @return Valeur après application de la fonction de fondu.
     */
    private double fade(double t)
    {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    /**
     * Méthode pour effectuer une interpolation linéaire entre deux valeurs.
     *
     * @param t Coefficient d'interpolation.
     * @param a Première valeur.
     * @param b Deuxième valeur.
     * @return Résultat de l'interpolation linéaire.
     */
    private double lerp(double t, double a, double b)
    {
        return a + t * (b - a);
    }

    /**
     * Méthode pour calculer le gradient du bruit de Perlin en trois dimensions.
     *
     * @param hash Valeur de hachage.
     * @param x Coordonnée x.
     * @param y Coordonnée y.
     * @param z Coordonnée z.
     * @return Valeur du gradient calculé.
     */
    private double grad(int hash, double x, double y, double z)
    {
        int h = hash & 15; // CONVERT LO 4 BITS OF HASH CODE
        double u = h < 8 ? x : y, // INTO 12 GRADIENT DIRECTIONS.
                v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    private static final int p[] = new int[512];

    private static final int permutation[] = { 151, 160, 137, 91, 90, 15,
            131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23,
            190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33,
            88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166,
            77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244,
            102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196,
            135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123,
            5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42,
            223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9,
            129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228,
            251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107,
            49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254,
            138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180
    };

    static
    {
        for (int i = 0; i < 256; i++)
            p[256 + i] = p[i] = permutation[i];
    }
}

