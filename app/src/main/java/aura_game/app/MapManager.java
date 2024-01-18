package aura_game.app;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MapManager {

    private TilesManager tilesManager;
    private PerlinNoiseV2 perlinNoise;
    private SaveManager saveManager;
    private SpriteBatch batch;

    private Biome choosenBiome;

    private final int nbTilesWidth;
    private final int nbTilesHeight;
    private final int tileSize;
    private final double relief;

    private Tile[][] map;

    private int count = 0;

    public MapManager(int DEFAULT_NB_TILES_WIDTH, int DEFAULT_NB_TILES_HEIGHT, int TILE_SIZE, Biome biome) {
        this.choosenBiome = biome;
        this.batch = new SpriteBatch();
        this.tilesManager = new TilesManager(TILE_SIZE);
        this.perlinNoise = new PerlinNoiseV2(choosenBiome);
        this.saveManager = new SaveManager();
        this.map = new Tile[DEFAULT_NB_TILES_WIDTH][DEFAULT_NB_TILES_HEIGHT];
        this.nbTilesWidth = DEFAULT_NB_TILES_WIDTH;
        this.nbTilesHeight = DEFAULT_NB_TILES_HEIGHT;
        this.tileSize = TILE_SIZE;
        this.relief = choosenBiome.getRelief() ;


        initialiseMap(perlinNoise.getZ());
    }



    public void render() {

        batch.begin();
        drawMap();
        if(count == 2) {
            //saveManager.saveImage(choosenBiome);
        }
        batch.end();
    }


    /**
     * Méthode pour dessiner la carte texturée.
     */
    private void initialiseMap(double z) {
        Tile tile;
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                double x = (double) j / (double) nbTilesWidth;
                double y = (double) i / (double) nbTilesHeight;
                //déterminent l'échelle du bruit dans chaque dimension
                double n = relief * perlinNoise.noise(10 * x, 6 * y, z);//x et y permet d'allonger selon w ou h
                //System.out.println("n is "+ n);
                if (n < 0.38) {
                    tile = new Tile(TileType.WATER, BorderType.NOBORDER);
                } else if (n >= 0.38 && n < 0.7) {
                    tile = new Tile(TileType.GRASS, BorderType.NOBORDER);
                } else if (n >= 0.7 && n < 0.85) {
                    tile = new Tile(TileType.MOUNTAIN1, BorderType.NOBORDER);
                } else if((n >= 0.85 && n < 0.95)){
                    tile = new Tile(TileType.MOUNTAIN2, BorderType.NOBORDER);
                }else if((n >= 0.95 && n < 1.12)){
                    tile = new Tile(TileType.MOUNTAIN3, BorderType.NOBORDER);
                }else{
                    tile = new Tile(TileType.MOUNTAIN4, BorderType.NOBORDER);
                }

                map[i][j] = tile;
            }
        }
        addBorders(map);

    }


    private void addBorders(Tile[][] tileset) {
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {

                Tile tile = tileset[i][j];

                if(tile.getLayer()!= 1) {//Si c'est pas de l'herbe sans bord
                    // Vérifie la tuile à gauche
                    if (i > 0) {
                        if (tileset[i - 1][j].getLayer() != tile.getLayer() &&
                                tileset[i - 1][j].getBorderTypeTile().equals(BorderType.NOBORDER)) {
                            if(tile.getLayer() != 0)tileset[i][j] = new Tile(TileType.GRASS, BorderType.RIGHTBORDER);
                            else tileset[i][j] = new Tile(TileType.WATER, BorderType.LEFTBORDER);

                        }
                        /*
                        if (tileset[i - 1][j].getTextureIndexActual() != tile.getTextureIndexActual() &&
                                tileset[i - 1][j].getDirectionTile().equals(Direction.NOBORDER)) {
                            if(tile.getTextureIndexActual() != 0)tileset[i][j] = new Tile(TileType.GRASS,Direction.RIGHT);
                            else tileset[i][j] = new Tile(TileType.WATER,Direction.LEFT);

                        }*/
                    }

                    // Vérifie la tuile à droite
                    if (i < nbTilesWidth - 1) {
                        if (tileset[i + 1][j].getLayer() != tile.getLayer() &&
                                tileset[i + 1][j].getBorderTypeTile().equals(BorderType.NOBORDER)) {

                            if(tile.getLayer() != 0)tileset[i][j] = new Tile(TileType.GRASS, BorderType.LEFTBORDER);
                            else tileset[i][j] = new Tile(TileType.WATER, BorderType.RIGHTBORDER);
                        }
                    }

                    // Vérifie la tuile en haut
                    if (j > 0) {
                        if (tileset[i][j - 1].getLayer() != tile.getLayer() &&
                                tileset[i][j - 1].getBorderTypeTile().equals(BorderType.NOBORDER)) {
                            if(tile.getLayer() != 0)tileset[i][j] = new Tile(TileType.GRASS, BorderType.TOPBORDER);
                            else tileset[i][j] = new Tile(TileType.WATER, BorderType.TOPBORDER);
                        }
                    }

                    // Vérifie la tuile en bas
                    if (j < nbTilesHeight - 1) {
                        //System.out.println("voisin index: " + tileset[i][j + 1].getIndexTexture());
                        //System.out.println("currentIndex " + tile.getIndexTexture());
                        if (tileset[i][j + 1].getLayer() != tile.getLayer() &&
                                tileset[i][j + 1].getBorderTypeTile().equals(BorderType.NOBORDER)) {
                            if(tile.getLayer() != 0)tileset[i][j] = new Tile(TileType.GRASS, BorderType.BOTTOMBORDER);
                            else tileset[i][j] = new Tile(TileType.WATER, BorderType.BOTTOMBORDER);
                        }
                    }
                }
            }
        }
    }

    /**Dessine l'ensemble des Tuiles déjà charger dans le tableau2D au préalable*/
    private void drawMap(){
        count++;
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                Tile currentTile = map[i][j];
                tilesManager.drawTile(batch, currentTile, i * tileSize, j * tileSize);
            }
        }

    }

}
