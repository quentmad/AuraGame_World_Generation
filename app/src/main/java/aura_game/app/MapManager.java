package aura_game.app;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.apache.commons.lang3.tuple.Pair;

import javax.tools.Diagnostic;


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

                map[i][j] = choosenBiome.getTileFor(n);
            }
        }
        addBorders(map);
        addCoinsToBorder(map);

    }

    /**
     * Ajoute des bordures aux tuiles en fonction de leur voisinage dans le tableau 2D de tuiles.
     * Appelé après l'initialisation du tableau avec les Tiles
     * @param tileset Le tableau 2D de tuiles représentant la carte.
     */
    private void addBorders(Tile[][] tileset) {
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                Tile tile = tileset[i][j];

                    checkAndSetBorder(tileset, i - 1, j, i, j, tile, Border.LEFT);
                    checkAndSetBorder(tileset, i + 1, j, i, j, tile, Border.RIGHT);
                    checkAndSetBorder(tileset, i, j - 1, i, j, tile, Border.BOTTOM);
                    checkAndSetBorder(tileset, i, j + 1, i, j, tile, Border.TOP);
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
     * @param border Le type de bordure à ajouter.
     */
    private void checkAndSetBorder(Tile[][] tileset, int nx, int ny, int ox, int oy,Tile currentTile, Border border) {

        //Pas sur le bord de l'image et le voisin n'est pas déjà un bord
        if(nx >= 0 && nx < nbTilesWidth && ny >= 0 && ny < nbTilesHeight && tileset[nx][ny].getCurrentTileBorder().equals(Border.NOBORDER)
        && tileset[nx][ny].getLayer() > currentTile.getLayer() ) {//Layer du voisin supérieur

            //Le current n'est pas de l'eau (le bord choisi est celui du voisin)
            if(!currentTile.isWaterTile()) {
                TileType borderTile = tileset[nx][ny].getTileType();
                int borderLayer = tileset[nx][ny].getLayer();
                tileset[nx][ny] = new Tile(borderTile, border, borderLayer, currentTile);

            }else {//Le bord choisi est celui de l'eau
                TileType borderTile = currentTile.getTileType();
                int borderLayer = currentTile.getLayer();
                tileset[ox][oy] = new Tile(borderTile, border, borderLayer, tileset[nx][ny] );
            }
        }
    }

    private void addCoinsToBorder(Tile[][] tileset) {
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                Tile currentTile = tileset[i][j];

                //
                if (currentTile.getCurrentTileBorder().equals(Border.NOBORDER)) {//Si ce n'est pas un border
                    // Vérifie la tuile i j dois devenir une tuile (vérifie les tuiles autour)
                    checkNeighborsAndSetCoins(tileset, i, j, currentTile);
                }
            }
        }
    }

    /** Verifie avec les 8 cases autour si la tuile x y doit devenir un coin
     *
     * @param tileset
     * @param x
     * @param y
     * @param currentTile
     */
    private void checkNeighborsAndSetCoins(Tile[][] tileset, int x, int y, Tile currentTile) {
            // Déterminez le type de coin en fonction de la disposition
            Border border = determineCornerType(tileset,x, y, currentTile);
            if(!border.equals(Border.NOBORDER)) {
                // Mettez à jour la tuile actuelle avec le coin approprié
                tileset[x][y] = new Tile(currentTile.getTileType(), border, currentTile.getLayer(), tileset[x][y]);
            }
    }


    //TODO [ligne][colonne]

    /**Logique pour déterminer le type de coin en fonction des voisins*/
    private Border determineCornerType(Tile[][] tileset, int x, int y, Tile currentTile) {
        //verifie si x est pas hors map, et si les tuiles voisines sont placé de sorte a ce que currentTile doit etre un rightbottom (si elles sont compatible)
        if (x < nbTilesWidth-1  && y < nbTilesHeight-1 && tileset[x][y + 1].getCurrentTileBorder().equals(Border.BOTTOM) && tileset[x + 1][y].getCurrentTileBorder().equals(Border.RIGHT)
                && areCompatibleTiles(tileset[x + 1][y], currentTile) && areCompatibleTiles(tileset[x][y + 1], currentTile)) {//rightBottom
            return Border.RIGHT_BOTTOM;
        }
        if (x < nbTilesWidth-1 && y > 0 && tileset[x][y - 1].getCurrentTileBorder().equals(Border.BOTTOM) && tileset[x + 1][y].getCurrentTileBorder().equals(Border.LEFT)
                && areCompatibleTiles(tileset[x + 1][y], currentTile) && areCompatibleTiles(tileset[x][y - 1], currentTile)) {//BottomLeft
            return Border.BOTTOM_LEFT;
        }
        if (x > 0 && y < nbTilesHeight-1 && tileset[x][y + 1].getCurrentTileBorder().equals(Border.TOP) && tileset[x - 1][y].getCurrentTileBorder().equals(Border.RIGHT)
                && areCompatibleTiles(tileset[x - 1][y], currentTile) && areCompatibleTiles(tileset[x][y + 1], currentTile)){//RightTop
            return Border.RIGHT_TOP;
        }
        if(x > 0 && y > 0 && tileset[x][y-1].getCurrentTileBorder().equals(Border.TOP) && tileset[x-1][y].getCurrentTileBorder().equals(Border.RIGHT)
                && areCompatibleTiles(tileset[x-1][y], currentTile) && areCompatibleTiles(tileset[x][y-1], currentTile )) {//Topleft
            return Border.TOP_LEFT;
        }

        return Border.NOBORDER;
    }


    /**@return true si les 2 tuiles sont de meme niveau et de meme type */
    private boolean areCompatibleTiles(Tile neighbor, Tile current){
        return ( current.getTileType()==neighbor.getTileType() && current.getLayer()==neighbor.getLayer() );
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
