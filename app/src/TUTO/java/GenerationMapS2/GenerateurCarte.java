package aura_game.app.GenerationMapS2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Cette classe génère une carte en utilisant l'algorithme Perlin Noise avec des tuiles.
 */
public class GenerateurCarte {

    static PerlinNoise perlinNoise;
    static Texture tilesheet;
    static TextureRegion[][] terrainTiles;

    // Chemin vers la feuille de tuiles
    static final String TILESHEET_PATH = "PunyWorld/punyworld-overworld-tileset-perlin.png";

    static final int WINDOW_WIDTH = 1920;
    static final int WINDOW_HEIGHT = 1080;
    static final int TILESIZE = 16;
    static final int WORLD_X = (WINDOW_WIDTH + TILESIZE - 1) / TILESIZE;
    static final int WORLD_Y = (WINDOW_HEIGHT + TILESIZE - 1) / TILESIZE;

    // Types de terrain
    static final int OCEAN3 = 0;
    static final int OCEAN2 = 1;
    static final int OCEAN1 = 2;
    static final int BEACH = 3;
    static final int GRASS = 4;
    static final int MOUNTAIN = 5;
    static final int SNOW = 6;

    // Liste de tous les types de terrain, ordonnée de la hauteur la plus basse à la hauteur la plus élevée
    public static final int[] ALL_TERRAIN_TYPES = {OCEAN3, OCEAN2, OCEAN1, BEACH, GRASS, MOUNTAIN, SNOW};

    /**Toutes les tuiles dans la feuille de tuiles, pour chaque type de terrain dans ALL_TERRAIN_TYPES*/
    private final Tuile[][] TERRAIN_TILES = {
            // Ocean depth level 3 tiles
            {
                new Tuile(0, 0),
                new Tuile(352, 160),  // bottom-right
                new Tuile(384, 160),  // bottom-left
                new Tuile(368, 160),  // bottom side
                new Tuile(352, 192),  // top-right
                new Tuile(352, 176),  // right side
                new Tuile(400,144),  // TR, BL
                new Tuile(400, 160),  // TR, BL, BR
                new Tuile(384, 192),  // top-left
                new Tuile(416, 144),  // TL, BR
                new Tuile(384, 176),  // left side
                new Tuile(416, 208),  // TL + BL + BR
                new Tuile(368, 192),  // top side
                new Tuile(368, 176),  // TL + TR + BR
                new Tuile(416, 224),  // TL + TR + BL
                new Tuile(368, 176),  // all sides
            },
            // Ocean depth level 2 tiles
            {
                new Tuile(0, 0),
                new Tuile(272, 160),  // bottom-right
                new Tuile(304, 160),  // bottom-left
                new Tuile(288, 160),  // bottom side
                new Tuile(272, 192),  // top-right
                new Tuile(272, 176),  // right side
                new Tuile(336, 144),  // TR, BL
                new Tuile(320, 160),  // TR, BL, BR
                new Tuile(304, 192),  // top-left
                new Tuile(352, 144),  // TL, BR
                new Tuile(304, 176),  // left side
                new Tuile(336, 160),  // TL + BL + BR
                new Tuile(288, 192),  // top side
                new Tuile(320, 176),  // TL + TR + BR
                new Tuile(336, 176),  // TL + TR + BL
                new Tuile(288, 176),  // all sides
            },
            // Ocean depth level 1 tiles
            {
                new Tuile(0, 0),
                new Tuile(192, 160),  // bottom-right
                new Tuile(224, 160),  // bottom-left
                new Tuile(208, 160),  // bottom side
                new Tuile(192, 192),  // top-right
                new Tuile(192, 176),  // right side
                new Tuile(288, 144),  // TR, BL
                new Tuile(240, 160),  // TR, BL, BR
                new Tuile(224, 192),  // top-left
                new Tuile(304, 144),  // TL, BR
                new Tuile(224, 176),  // left side
                new Tuile(256, 160),  // TL + BL + BR
                new Tuile(208, 192),  // top side
                new Tuile(240, 176),  // TL + TR + BR
                new Tuile(256, 176),  // TL + TR + BL
                new Tuile(208, 176),  // all sides

            },
            // Beach level tiles
            {
                new Tuile(0, 0),
                new Tuile(352, 0),  // bottom-right
                new Tuile(192, 0),  // bottom-left
                new Tuile(176, 0),  // bottom side
                new Tuile(160, 32),  // top-right
                new Tuile(160, 16),  // right side
                new Tuile(400, 32),  // TR, BL
                new Tuile(208, 0),  // TR, BL, BR
                new Tuile(192, 32),  // top-left
                new Tuile(416, 32),  // TR, BL
                new Tuile(192, 16),  // left side
                new Tuile(224, 0),  // TL + BL + BR
                new Tuile(176, 32),  // top side
                new Tuile(208, 16),  // TL + TR + BR
                new Tuile(224, 16),  // TL + TR + BL
                new Tuile(176, 16),  // all sides

            },
            // Grass level tiles
            {
                new Tuile(0, 0),
                new Tuile(64, 80),  // bottom-right
                new Tuile(48, 80),  // bottom-left
                new Tuile(16, 96),  // bottom side
                new Tuile(64, 64),  // top-right
                new Tuile(32, 80),  // right side
                new Tuile(48, 96),  // TR, BL
                new Tuile(32, 96),  // TR, BL, BR
                new Tuile(48, 64),  // top-left
                new Tuile(64, 96),  // TL, BR
                new Tuile(0, 80),  // left side
                new Tuile(0, 96),  // TL + BL + BR
                new Tuile(16, 64),  // top side
                new Tuile(32, 64),  // TL + TR + BR
                new Tuile(0, 64),  // TL + TR + BL
                new Tuile(32, 32),  // all sides

            },
            // Mountain level tiles
            {
                new Tuile(0, 0),
                new Tuile(368, 112),  // bottom-right
                new Tuile(352, 112),  // bottom-left
                new Tuile(320, 128),  // bottom side
                new Tuile(368, 96),  // top-right
                new Tuile(336, 112),  // right side
                new Tuile(352, 128),  // TR, BL
                new Tuile(336, 128),  // TR, BL, BR
                new Tuile(352, 96),  // top-left
                new Tuile(368, 128),  // TL, BR
                new Tuile(304, 112),  // left side
                new Tuile(304, 128),  // TL + BL + BR
                new Tuile(320, 96),  // top side
                new Tuile(336, 96),  // TL + TR + BR
                new Tuile(304, 96),  // TL + TR + BL
                new Tuile(320, 112),  // all sides

            },
            // Snow level tiles
            {
                new Tuile(0, 0),
                new Tuile(0, 0),
                new Tuile(0, 0),
                new Tuile(0, 0),
                new Tuile(0, 0),
                new Tuile(0, 0),
                new Tuile(0, 0),
                new Tuile(0, 0),
                new Tuile(0, 0),
                new Tuile(0, 0),
                new Tuile(0, 0),
                new Tuile(0, 0),
                new Tuile(0, 0),
                new Tuile(0, 0),
                new Tuile(0, 0),
                new Tuile(400, 112),  // all sides

            }
    };

    /**
     * Initialise le générateur de carte avec les données nécessaires.
     */
    public GenerateurCarte() {
        perlinNoise = new PerlinNoise(3, System.currentTimeMillis());
        tilesheet = new Texture(TILESHEET_PATH);
        terrainTiles = new TextureRegion[TERRAIN_TILES.length][];

        // Découper la feuille de tuiles en régions
        for (int i = 0; i < TERRAIN_TILES.length; i++) {
            terrainTiles[i] = new TextureRegion[TERRAIN_TILES[i].length];
            for (int j = 0; j < TERRAIN_TILES[i].length; j++) {
                int x = TERRAIN_TILES[i][j].x % (tilesheet.getWidth() / TILESIZE) * TILESIZE;
                int y = TERRAIN_TILES[i][j].y / (tilesheet.getWidth() / TILESIZE) * TILESIZE;
                terrainTiles[i][j] = new TextureRegion(tilesheet, x, y, TILESIZE, TILESIZE);
            }
        }
    }

    /**
     * Génère et affiche la carte en utilisant l'algorithme Perlin Noise.
     */
    public void afficherCarte() {
        // Obtenir la carte des types de terrain générée par Perlin Noise
        int[][] terrainTypeMap = getTerrainTypeMap();

        // Créer une instance de WorldDrawer pour gérer l'affichage
        WorldDrawer worldDrawer = new WorldDrawer(this);

        // Afficher la carte des types de terrain
        worldDrawer.draw(terrainTypeMap, true);
    }


    public Tuile[][] getTERRAIN_TILES() {
        return TERRAIN_TILES;
    }

    /**
     * Génère et renvoie la carte des types de terrain en utilisant l'algorithme Perlin Noise.
     * La carte résultante est basée sur les valeurs Perlin Noise pour chaque position dans le monde.
     *
     * @return La carte des types de terrain où chaque case représente le type de terrain à cette position.
     */
    public int[][] getTerrainTypeMap() {
        // Création d'une nouvelle carte des types de terrain
        int[][] terrainTypeMap = new int[WORLD_X][WORLD_Y];

        // Parcours de toutes les positions dans le monde
        for (int x = 0; x < WORLD_X; x++) {
            for (int y = 0; y < WORLD_Y; y++) {
                // Utiliser Perlin Noise pour obtenir une valeur entre 0 et 1
                float value = (float) perlinNoise.noise(new double[]{(double) x / 20f, (double) y / 20f});

                // Déterminer le type de terrain en fonction de la valeur Perlin Noise
                int terrainType = (int) (value * (ALL_TERRAIN_TYPES.length - 1));

                // Affecter le type de terrain à la position correspondante dans la carte
                terrainTypeMap[x][y] = terrainType;
            }
        }

        // Renvoyer la carte des types de terrain générée
        return terrainTypeMap;
    }
}