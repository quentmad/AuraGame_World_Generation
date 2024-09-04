

package aura_game.app.oldGenerationMapS1;

import com.badlogic.gdx.ApplicationAdapter;

/**
 * Classe principale de l'application libGDX. Gère le cycle de vie du jeu, la mise à jour et le rendu graphique.
 */
public class AuraGDXS1 extends ApplicationAdapter {
/*
    // Paramètres pour la génération de la carte de bruit
    int mapWidth = 100;  // Choisir la largeur de la carte
    int mapHeight = 100; // Choisir la hauteur de la carte
    int seed = 42;       // Choisir une graine pour la génération aléatoire
    float scale = 20.0f;  // Choisir l'échelle de la carte
    int octaves = 6;     // Choisir le nombre d'octaves pour créer des détails
    float persistance = 0.5f;  // Choisir le facteur de persistance
    float lacunarity = 2.0f;   // Choisir le facteur de lacunarité
    Vector2 offset = new Vector2(0, 0);  // Choisir le décalage de la carte

    //SpriteBatch batch; // déclaration d'un objet SpriteBatch pour dessiner les images

    @Override
    public void create() {
        // Générer une carte de bruit aléatoire en utilisant la méthode statique
        float[][] noiseMap = MapGenerator.GenerateNoiseMap(mapWidth, mapHeight, seed, scale, octaves, persistance, lacunarity, offset);

        // Afficher la carte de bruit générée (facultatif, selon votre système d'affichage)
        printNoiseMap(noiseMap);
        // Générer une image PNG à partir de la carte de bruit
        generatePNG(noiseMap, "noiseMap.png");

    }
*/
    /**
     * Appelé de façon continue par la boucle principale de libGDX
     *//*
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }


    // Méthode auxiliaire pour afficher la carte de bruit (facultatif)
    private void printNoiseMap(float[][] noiseMap) {
        for (int y = 0; y < noiseMap[0].length; y++) {
            for (int x = 0; x < noiseMap.length; x++) {
                System.out.print(noiseMap[x][y] + " ");
            }
            System.out.println();
        }
    }


    private void generatePNG(float[][] noiseMap, String fileName) {
        int width = noiseMap.length;
        int height = noiseMap[0].length;

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                float value = noiseMap[x][y];
                int color = getColor(value);
                pixmap.drawPixel(x, y, color);
            }
        }

        // Sauvegarder l'image au format PNG
        PixmapIO.writePNG(Gdx.files.local(fileName), pixmap);
        pixmap.dispose();
    }

    private int getColor(float value) {
        // Adapter cela en fonction de la façon dont vous souhaitez attribuer des couleurs à vos valeurs
        // Cette version utilise une simple échelle de gris

        int gray = (int) (value * 255);
        return (gray << 24) | (gray << 16) | (gray << 8) | 0xFF;
    }
*/

}

