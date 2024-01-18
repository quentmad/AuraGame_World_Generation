package aura_game.app.S2;

public class Config {
    public static final String TILESHEET_PATH = "PunyWorld/punyworld-overworld-tileset-perlin.png";
    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;
    public static final int TILESIZE = 16;
    public static final int WORLD_X = (WINDOW_WIDTH + TILESIZE - 1) / TILESIZE;
    public static final int WORLD_Y = (WINDOW_HEIGHT + TILESIZE - 1) / TILESIZE;

    public static final int[] TERRAIN_WEIGHTS = {3, 4, 5, 2, 1, 1, 1};

    // Terrain types
    public static final int OCEAN3 = 0;
    public static final int OCEAN2 = 1;
    public static final int OCEAN1 = 2;
    public static final int BEACH = 3;
    public static final int GRASS = 4;
    public static final int MOUNTAIN = 5;
    public static final int SNOW = 6;

    // List of all terrain types, ordered from lower height to higher height
    public static final int[] ALL_TERRAIN_TYPES = {OCEAN3, OCEAN2, OCEAN1, BEACH, GRASS, MOUNTAIN, SNOW};

    // All tiles in the tilesheet, for each terrain type in ALL_TERRAIN_TYPES
    public static final int[][] TERRAIN_TILES = {
            // Ocean depth level 3 tiles
            {0, 0, 352, 160, 384, 160, 368, 160, 352, 192, 352, 176, 400, 144, 400, 160, 384, 192, 416, 144, 384, 176, 416, 208, 368, 192, 368, 176, 416, 224, 368, 176},
            // Ocean depth level 2 tiles
            {0, 0, 272, 160, 304, 160, 288, 160, 272, 192, 272, 176, 336, 144, 320, 160, 304, 192, 352, 144, 304, 176, 336, 160, 288, 192, 320, 176, 336, 176, 288, 176},
            // Ocean depth level 1 tiles
            {0, 0, 192, 160, 224, 160, 208, 160, 192, 192, 192, 176, 288, 144, 240, 160, 224, 192, 304, 144, 224, 176, 256, 160, 208, 192, 240, 176, 256, 176, 208, 176},
            // Beach level tiles
            {0, 0, 352, 0, 192, 0, 176, 0, 160, 32, 160, 16, 400, 32, 208, 0, 192, 32, 416, 32, 192, 16, 224, 0, 176, 32, 208, 16, 224, 16, 176, 16},
            // Grass level tiles
            {0, 0, 64, 80, 48, 80, 16, 96, 64, 64, 32, 80, 48, 96, 32, 96, 48, 64, 64, 96, 0, 80, 16, 64, 32, 64, 32, 32},
            // Mountain level tiles
            {0, 0, 368, 112, 352, 112, 320, 128, 368, 96, 336, 112, 352, 128, 336, 128, 352, 96, 368, 128, 304, 112, 304, 128, 320, 96, 336, 96, 304, 96, 320, 112},
            // Snow level tiles
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 400, 112}
    };
}