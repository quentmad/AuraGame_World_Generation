package aura_game.app;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;


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


                if (currentTile.getCurrentTileBorder().equals(Border.NOBORDER) || currentTile.getCurrentTileBorder().isSimpleBorder()) {//Si ce n'est pas un border
                    // Vérifie la tuile i j dois devenir une tuile (vérifie les tuiles autour)
                    Tile under = null;

                    if((currentTile.getCurrentTileBorder().equals(Border.NOBORDER))){//Si la tuile testé était une no bordure alors la futur underTile sera la current
                        under = new Tile(currentTile.getTileType(),currentTile.getLayer());
                    }else if(currentTile.getCurrentTileBorder().isSimpleBorder()){
                        under = new Tile(currentTile.getUnderTile().getTileType(),currentTile.getLayer());//Si la tuile était une bordure simple alors la futur underTile sera la underTile de current
                    }
                    if(under != null) {
                        checkNeighborsAndSetCoins(tileset, i, j, currentTile, under);
                    }
                }
            }
        }
    }

    /** Verifie avec les 8 cases autour si la tuile x y doit devenir un coin out ou in
     *
     * @param tileset
     * @param x
     * @param y
     * @param currentTile
     */
    private void checkNeighborsAndSetCoins(Tile[][] tileset, int x, int y, Tile currentTile, Tile underTile) {
            // Déterminez le type de coin en fonction de la disposition
            Border border = determineOutCornerType(tileset,x, y, currentTile);//OUT COINS CHECK
            if(border.equals(Border.NOBORDER)){
                border = determineInCornerType(tileset,x, y, currentTile);
            }

            if(!border.equals(Border.NOBORDER)) {
                // Mettez à jour la tuile actuelle avec le coin approprié
                tileset[x][y] = new Tile(currentTile.getTileType(), border, currentTile.getLayer(), underTile);
            }
    }


    //TODO [ligne][colonne]

    /**
     * Détermine le type de coin out en fonction des voisins.
     *
     * @param tileset      Le tableau 2D de tuiles représentant la carte.
     * @param x            La position x de la tuile actuelle.
     * @param y            La position y de la tuile actuelle.
     * @param currentTile  La tuile actuelle.
     * @return Le type de coin out, ou {@link Border#NOBORDER} si aucun coin n'est détecté.
     */
    private Border determineOutCornerType(Tile[][] tileset, int x, int y, Tile currentTile) {
        int maxX = nbTilesWidth - 1;
        int maxY = nbTilesHeight - 1;

        /* Vérifie si la tuile peut devenir un coin rightBottom, pour cela il vérifie si :
         * - Le voisin à droite de la tuile actuelle a une bordure bottom.
         * - Le voisin au-dessous de la tuile actuelle a une bordure right.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if (x < maxX  && y > 0 && tileset[x+1][y].getCurrentTileBorder().equals(Border.BOTTOM) && tileset[x][y-1].getCurrentTileBorder().equals(Border.RIGHT)
                && areCompatibleTiles(tileset[x+1][y], currentTile) && areCompatibleTiles(tileset[x][y-1], currentTile)) {//rightBottom
            System.out.println("a o right bottom");
            return Border.ORIGHT_BOTTOM;
        }

         /* Vérifie si la tuile peut devenir un coin bottomLeft, pour cela il vérifie si :
         * - Le voisin à gauche de la tuile actuelle a une bordure bottom.
         * - Le voisin au-dessous de la tuile actuelle a une bordure left.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if (x > 0 && y > 0 && tileset[x-1][y].getCurrentTileBorder().equals(Border.BOTTOM) && tileset[x][y-1].getCurrentTileBorder().equals(Border.LEFT)
                && areCompatibleTiles(tileset[x-1][y], currentTile) && areCompatibleTiles(tileset[x][y-1], currentTile)) {//BottomLeft
            System.out.println("a o bottom left");
            return Border.OBOTTOM_LEFT;
        }

        /* Vérifie si la tuile peut devenir un coin rightTop, pour cela il vérifie si :
         * - Le voisin à droite de la tuile actuelle a une bordure top.
         * - Le voisin en dessus de la tuile actuelle a une bordure right.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if (x < maxX && y < maxY && tileset[x+1][y].getCurrentTileBorder().equals(Border.TOP) && tileset[x][y+1].getCurrentTileBorder().equals(Border.RIGHT)
                && areCompatibleTiles(tileset[x+1][y], currentTile) && areCompatibleTiles(tileset[x][y+1], currentTile)){//RightTop
            System.out.println("a o right top");
            return Border.ORIGHT_TOP;
        }

        /* Vérifie si la tuile peut devenir un coin topLeft, pour cela il vérifie si :
         * - Le voisin à gauche de la tuile actuelle a une bordure top.
         * - Le voisin en dessus de la tuile actuelle a une bordure left.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if(x >0 && y < maxY && tileset[x-1][y].getCurrentTileBorder().equals(Border.TOP) && tileset[x][y+1].getCurrentTileBorder().equals(Border.LEFT)
                && areCompatibleTiles(tileset[x-1][y], currentTile) && areCompatibleTiles(tileset[x][y+1], currentTile )) {//Topleft
            System.out.println("a o top left");
            return Border.OTOP_LEFT;
        }

        return Border.NOBORDER;
    }

    /**Logique pour déterminer le type de coin en fonction des voisins
     * check pour in coins*/
    private Border determineInCornerType(Tile[][] tileset, int x, int y, Tile currentTile) {
        int maxX = nbTilesWidth - 1;
        int maxY = nbTilesHeight - 1;

        /* Vérifie si la tuile peut devenir un coin iLeftTop, pour cela il vérifie si :
         * - La bordure de la tuile voisine à droite est top ou iTopRight.
         * - La bordure de la tuile voisine au-dessous est left ou iLeftBottom.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if (x < maxX  && y > 0 && (tileset[x+1][y].getCurrentTileBorder().equals(Border.TOP) || tileset[x+1][y].getCurrentTileBorder().equals(Border.ITOP_RIGHT))//Non hors zone, top ou itopright
                && (tileset[x][y-1].getCurrentTileBorder().equals(Border.LEFT) || tileset[x][y-1].getCurrentTileBorder().equals(Border.ILEFT_BOTTOM))//left ou coin ileftbottom
                && areCompatibleTiles(tileset[x+1][y], currentTile) && areCompatibleTiles(tileset[x][y-1], currentTile)) {
            System.out.println("a i left top");
            return Border.ILEFT_TOP;
        }
        /* Vérifie si la tuile peut devenir un coin iTopRight, pour cela il vérifie si :
         * - La bordure de la tuile voisine à gauche est top ou iLeftTop.
         * - La bordure de la tuile voisine au-dessous est right ou iBottomRight.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if (x > 0 && y > 0 && (tileset[x-1][y].getCurrentTileBorder().equals(Border.TOP) || tileset[x-1][y].getCurrentTileBorder().equals(Border.ILEFT_TOP)) &&
                (tileset[x][y-1].getCurrentTileBorder().equals(Border.RIGHT) || tileset[x][y-1].getCurrentTileBorder().equals(Border.IBOTTOM_RIGHT))
                && areCompatibleTiles(tileset[x-1][y], currentTile) && areCompatibleTiles(tileset[x][y-1], currentTile)) {
            System.out.println("a i top right");
            return Border.ITOP_RIGHT;
        }

        /* Vérifie si la tuile peut devenir un coin iLeftBottom, pour cela il vérifie si :
         * - La bordure de la tuile voisine à droite est bottom ou iBottomRight.
         * - La bordure de la tuile voisine en dessus est left ou iLeftTop.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if (x < maxX && y < maxY && (tileset[x+1][y].getCurrentTileBorder().equals(Border.BOTTOM) || tileset[x+1][y].getCurrentTileBorder().equals(Border.IBOTTOM_RIGHT))
                && (tileset[x][y+1].getCurrentTileBorder().equals(Border.LEFT) || tileset[x][y+1].getCurrentTileBorder().equals(Border.ILEFT_TOP))
                && areCompatibleTiles(tileset[x+1][y], currentTile) && areCompatibleTiles(tileset[x][y+1], currentTile)){
            System.out.println("a i left bottom");
            return Border.ILEFT_BOTTOM;
        }

        /* Vérifie si la tuile peut devenir un coin iBottomRight, pour cela il vérifie si :
         * - La position x de la tuile actuelle est supérieure à 0.
         * - La position y de la tuile actuelle est inférieure à maxY.
         * - La bordure de la tuile voisine à gauche est bottom ou iLeftBottom.
         * - La bordure de la tuile voisine en dessous est right ou iTopRight.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if(x >0 && y < maxY && (tileset[x-1][y].getCurrentTileBorder().equals(Border.BOTTOM) ||tileset[x-1][y].getCurrentTileBorder().equals(Border.ILEFT_BOTTOM))
                && (tileset[x][y+1].getCurrentTileBorder().equals(Border.RIGHT) || tileset[x][y+1].getCurrentTileBorder().equals(Border.ITOP_RIGHT))
                && areCompatibleTiles(tileset[x-1][y], currentTile) && areCompatibleTiles(tileset[x][y+1], currentTile )) {
            System.out.println("a i bottom right");
            return Border.IBOTTOM_RIGHT;
        }

        return Border.NOBORDER;
    }

    /**@return true si les 2 tuiles sont de meme niveau et de meme type */
    private boolean areCompatibleTiles(Tile neighbor, Tile current){
        return ( current.getTileType()==neighbor.getTileType() && current.getLayer()==neighbor.getLayer() );
    }

    /**
     * Evite la redondance comme (tileset[x][y+1].getCurrentTileBorder().equals(Border.RIGHT) || tileset[x][y+1].getCurrentTileBorder().equals(Border.ITOP_RIGHT))
     * @param tile la tuile qu'on veut tester, par exemple tileset[x][y+1]
     * @param possibleBorders la liste des borders possible
     * @return true si la tuile est une des bordures de la liste
     */
    private boolean isOneOfThesesBorder(Tile tile, List<Border> possibleBorders ){
        return possibleBorders.contains(tile.getCurrentTileBorder());
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
