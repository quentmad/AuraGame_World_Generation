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
     * Initialise la carte en utilisant l'algorithme de bruit de Perlin pour générer des tuiles en fonction
     * des paramètres de relief, de type de terrain et de la graine aléatoire actuelle.
     *
     * @param z La coordonnée z utilisée dans le bruit de Perlin, influençant la génération de la carte.
     */
    private void initialiseMap(double z) {
        Tile tile;
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                double x = (double) j / (double) nbTilesWidth;
                double y = (double) i / (double) nbTilesHeight;
                //déterminent l'échelle du bruit dans chaque dimension
                double n = relief * perlinNoise.noise(10 * x, 6 * y, z);//x et y permet d'allonger selon w ou h
                /*
                if (n < 0.38) {
                    tile = new Tile(TileType.WATER, BorderType.NOBORDER,0);
                } else if (n >= 0.38 && n < 0.7) {
                    tile = new Tile(TileType.GRASS, BorderType.NOBORDER,1);
                } else if (n >= 0.7 && n < 0.85) {
                    tile = new Tile(TileType.GRASS, BorderType.NOBORDER,2);
                } else if((n >= 0.85 && n < 0.95)){
                    tile = new Tile(TileType.GRASS, BorderType.NOBORDER,3);
                }else if((n >= 0.95 && n < 1.12)){
                    tile = new Tile(TileType.GRASS, BorderType.NOBORDER,4);
                }else{
                    tile = new Tile(TileType.GRASS, BorderType.NOBORDER,5);
                }

                */

                map[i][j] = choosenBiome.getTileFor(n);
            }
        }
        addBorders(map);

    }

    /**
     * Ajoute des bordures aux tuiles en fonction de leur voisinage dans le tableau 2D de tuiles.
     *
     * @param tileset Le tableau 2D de tuiles représentant la carte.
     */
    private void addBorders(Tile[][] tileset) {
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                Tile tile = tileset[i][j];

                    checkAndSetBorder(tileset, i - 1, j, i, j, tile, BorderType.LEFTBORDER);
                    checkAndSetBorder(tileset, i + 1, j, i, j, tile, BorderType.RIGHTBORDER);
                    checkAndSetBorder(tileset, i, j - 1, i, j, tile, BorderType.BOTTOMBORDER);
                    checkAndSetBorder(tileset, i, j + 1, i, j, tile, BorderType.TOPBORDER);
            }
        }
    }

    /**
     * Vérifie la tuile voisine à une position spécifiée et affecte une bordure à la tuile voisine (ainsi que la sous
     * tuile de celle courante) si nécessaire.
     *
     * @param tileset    Le tableau 2D de tuiles représentant la carte.
     * @param nx         La position x de la tuile voisine.
     * @param ny         La position y de la tuile voisine.
     * @param ox         La position x de la tuile actuelle.
     * @param oy         La position y de la tuile actuelle.
     * @param currentTile La tuile actuelle.
     * @param borderType Le type de bordure à ajouter.
     */
    private void checkAndSetBorder(Tile[][] tileset, int nx, int ny, int ox, int oy,Tile currentTile, BorderType borderType) {

        //Pas sur le bord de l'image et le voisin n'est pas déjà un bord
        if(nx >= 0 && nx < nbTilesWidth && ny >= 0 && ny < nbTilesHeight && tileset[nx][ny].getCurrentTileBorder().equals(BorderType.NOBORDER)
        && tileset[nx][ny].getLayer() > currentTile.getLayer() ) {//Layer du voisin supérieur

            //Le current n'est pas de l'eau (le bord choisi est celui du voisin)
            if(!currentTile.isWaterTile()) {
                TileType borderTile = tileset[nx][ny].getTileType();
                int borderLayer = tileset[nx][ny].getLayer();
                tileset[nx][ny] = new Tile(borderTile, borderType, borderLayer, currentTile);

            }else {//Le bord choisi est celui de l'eau
                TileType borderTile = currentTile.getTileType();
                int borderLayer = currentTile.getLayer();
                tileset[ox][oy] = new Tile(borderTile, borderType, borderLayer, tileset[nx][ny] );
            }
        }
    }

    /**Dessine l'ensemble des Tuiles déjà chargé dans le tableau2D au préalable*/
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
