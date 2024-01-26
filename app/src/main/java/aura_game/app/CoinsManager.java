package aura_game.app;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class CoinsManager {
    private final int nbTilesWidth;
    private final int nbTilesHeight;

    public CoinsManager(int nbTilesWidth, int nbTilesHeight){
        this.nbTilesWidth = nbTilesWidth;
        this.nbTilesHeight = nbTilesHeight;

    }

    public void addCoinsToBorder(Tile[][] tileset) {
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                Tile currentTile = tileset[i][j];

                // Vérifie la tuile i j dois devenir une tuile
                Tile under = null;

                if((currentTile.getCurrentTileBorder().equals(Border.NOBORDER))){//Si la tuile testé était une no bordure alors la futur underTile sera la current
                    under = new Tile(currentTile.getTileType(),currentTile.getLayer()-1);

                }else {//if(currentTile.getCurrentTileBorder().isSimpleBorder()){   //TODO: pb de sous tile par moment
                    under = new Tile(currentTile.getUnderTile().getTileType(), currentTile.getLayer()-1);//Si la tuile était une bordure simple alors la futur underTile sera la underTile de current
                }
                if(under != null) {
                    checkNeighborsAndSetCoins(tileset, i, j, currentTile, under);
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
        if(border.equals(Border.NOBORDER) || currentTile.getCurrentTileBorder().equals(border)){//Si c'est une sans bordure ou que la bordure déterminé est celle qui était déjà là
            border = determineInCornerType(tileset,x, y, currentTile);
        }

        if(!border.equals(Border.NOBORDER) && !currentTile.getCurrentTileBorder().equals(border)) {//Si il y a une bordure et que c'est pas la meme que la précédente
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
         * - Le voisin à droite de la tuile actuelle a une bordure bottom ou OBottomLeft ou IBottomRight.
         * - Le voisin au-dessous de la tuile actuelle a une bordure right ou ORightTop, IBottomRight.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if (x < maxX  && y > 0 && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.BOTTOM, Border.OBOTTOM_LEFT, Border.IBOTTOM_RIGHT)  )
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.RIGHT, Border.ORIGHT_TOP, Border.IBOTTOM_RIGHT)    )
                && areCompatibleTiles(Arrays.asList(tileset[x+1][y], tileset[x][y-1]), currentTile)  ) {//rightBottom
            //System.out.println("a o right bottom");
            return Border.ORIGHT_BOTTOM;
        }

        /* Vérifie si la tuile peut devenir un coin bottomLeft, pour cela il vérifie si :
         * - Le voisin à gauche de la tuile actuelle a une bordure bottom ou ORightBottom ou ILeftBottom.
         * - Le voisin au-dessous de la tuile actuelle a une bordure left ou OTopLeft ou ILeftBottom.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if (x > 0 && y > 0 && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.BOTTOM, Border.ORIGHT_BOTTOM, Border.ILEFT_BOTTOM)  )
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.LEFT, Border.OTOP_LEFT, Border.ILEFT_BOTTOM)   )
                && areCompatibleTiles(Arrays.asList(tileset[x-1][y], tileset[x][y-1]), currentTile) ) {//BottomLeft
            //System.out.println("a o bottom left");
            return Border.OBOTTOM_LEFT;
        }

        /* Vérifie si la tuile peut devenir un coin rightTop, pour cela il vérifie si :
         * - Le voisin à droite de la tuile actuelle a une bordure top ou ORightBottom ou ITopRight.
         * - Le voisin en dessus de la tuile actuelle a une bordure right ou OTopLeft ou ITopRight.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if (x < maxX && y < maxY && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.TOP, Border.ORIGHT_BOTTOM, Border.ITOP_RIGHT))
                && isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.RIGHT, Border.OTOP_LEFT, Border.ITOP_RIGHT))
                && areCompatibleTiles(Arrays.asList(tileset[x+1][y], tileset[x][y+1]), currentTile) ){//RightTop
            //System.out.println("a o right top");
            return Border.ORIGHT_TOP;
        }

        /* Vérifie si la tuile peut devenir un coin topLeft, pour cela il vérifie si :
         * - Le voisin à gauche de la tuile actuelle a une bordure top ou ORightTop ou ILeftTop.
         * - Le voisin en dessus de la tuile actuelle a une bordure left ou OBottomLeft ou ILeftTop.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if(x >0 && y < maxY && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.TOP,Border.ORIGHT_TOP, Border.ILEFT_TOP))
                && isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.LEFT, Border.OBOTTOM_LEFT, Border.ILEFT_TOP))
                && areCompatibleTiles(Arrays.asList(tileset[x-1][y],tileset[x][y+1]), currentTile ) ) {//Topleft
            //System.out.println("a o top left");
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
         * - La bordure de la tuile voisine à droite est top ou OTopLeft ou iTopRight.
         * - La bordure de la tuile voisine au-dessous est left ou OTopLeft ou iLeftBottom.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if (x < maxX  && y > 0 && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.TOP,Border.OTOP_LEFT, Border.ITOP_RIGHT))
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.LEFT,Border.OTOP_LEFT, Border.ILEFT_BOTTOM))
                && areCompatibleTiles(Arrays.asList(tileset[x+1][y],tileset[x][y-1]), currentTile) )   {
            //System.out.println("a i left top");
            return Border.ILEFT_TOP;
        }
        /* Vérifie si la tuile peut devenir un coin iTopRight, pour cela il vérifie si :
         * - La bordure de la tuile voisine à gauche est top ou ORightTop ou iLeftTop.
         * - La bordure de la tuile voisine au-dessous est right ou ORightTop ou iBottomRight.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if (x > 0 && y > 0 && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.TOP, Border.ORIGHT_TOP, Border.ILEFT_TOP))
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.RIGHT, Border.ORIGHT_TOP, Border.IBOTTOM_RIGHT))
                && areCompatibleTiles(Arrays.asList(tileset[x-1][y],tileset[x][y-1]), currentTile)  )  {
            //System.out.println("a i top right");
            return Border.ITOP_RIGHT;
        }

        /* Vérifie si la tuile peut devenir un coin iLeftBottom, pour cela il vérifie si :
         * - La bordure de la tuile voisine à droite est bottom ou OBottomLeft ou iBottomRight.
         * - La bordure de la tuile voisine en dessus est left ou OBottomLeft ou iLeftTop.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if (x < maxX && y < maxY && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.BOTTOM,Border.OBOTTOM_LEFT, Border.IBOTTOM_RIGHT))
                && isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.LEFT, Border.OBOTTOM_LEFT, Border.ILEFT_TOP))
                && areCompatibleTiles(Arrays.asList(tileset[x+1][y],tileset[x][y+1]), currentTile)  ){
            //System.out.println("a i left bottom");
            return Border.ILEFT_BOTTOM;
        }

        /* Vérifie si la tuile peut devenir un coin iBottomRight, pour cela il vérifie si :
         * - La position x de la tuile actuelle est supérieure à 0.
         * - La position y de la tuile actuelle est inférieure à maxY.
         * - La bordure de la tuile voisine à gauche est bottom ou ORightBottom ou iLeftBottom.
         * - La bordure de la tuile voisine en dessous est right ou ORightBottom ou iTopRight.
         * - Les tuiles actuelle et ses voisins sont compatibles.   */
        if(x >0 && y < maxY && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.BOTTOM,Border.ORIGHT_BOTTOM, Border.ILEFT_BOTTOM))
                &&isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.RIGHT, Border.ORIGHT_BOTTOM, Border.ITOP_RIGHT))
                && areCompatibleTiles(Arrays.asList(tileset[x-1][y],tileset[x][y+1]), currentTile)  ) {
            //System.out.println("a i bottom right");
            return Border.IBOTTOM_RIGHT;
        }

        return Border.NOBORDER;
    }

    /**@return true si toutes les tuiles sont de meme niveau et de meme type que la tuile current */
    private boolean areCompatibleTiles(List<Tile> neighbors, Tile current){
        for(Tile neighbor : neighbors){
            if(!(current.getTileType()==neighbor.getTileType() && current.getLayer()==neighbor.getLayer())){
                return false;
            }
        }
        return true;
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



    public void addCoinsToBorderModify(Tile[][] tileset) {
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                Tile currentTile = tileset[i][j];

                // Vérifie la tuile i j dois devenir une tuile
                Tile under = null;

                if((currentTile.getCurrentTileBorder().equals(Border.NOBORDER))){//Si la tuile testé était une no bordure alors la futur underTile sera la current
                    under = new Tile(currentTile.getTileType(),currentTile.getLayer()-1);

                }else {//if(currentTile.getCurrentTileBorder().isSimpleBorder()){
                    under = new Tile(currentTile.getUnderTile().getTileType(),currentTile.getLayer()-1/*-1?*/);//Si la tuile était une bordure simple alors la futur underTile sera la underTile de current
                }

                if(under != null) {
                    checkNeighborsAndSetCoinsModify(tileset, i, j, currentTile, under);
                }


            }
        }
    }
    /** Verifie avec les 8 cases autour si la tuile x y doit devenir un coin out ou in en devant modifier le type de bordure(s)
     *
     * @param tileset
     * @param x du currentTile
     * @param y currentTile
     * @param currentTile
     */
    private void checkNeighborsAndSetCoinsModify(Tile[][] tileset, int x, int y, Tile currentTile, Tile underTile) {
        // Déterminez le type de coin en fonction de la disposition
        List<Pair<Border,Point>> borders = determineCornerTypeModify(tileset,x, y, currentTile);//COINS IF SOME MODIF CHECK

        if(borders!=null) {
            //System.out.println("print tile1");
            // Mettez à jour les tuiles
            for (int i = 0; i < borders.size(); i++) {
                int xxx = borders.get(i).getRight().getX();
                int yyy = borders.get(i).getRight().getY();

            if(!tileset[xxx][yyy].getCurrentTileBorder().equals(borders.get(i).getLeft())){//Si la bordure qu'on veut mettre est pas déjà l'actuelle
                    //System.out.println("print tilep2");
                    TileType neighborTileType = tileset[borders.get(i).getRight().getX()][borders.get(i).getRight().getY()].getTileType();//TEST
                    tileset[xxx][yyy] = new Tile(neighborTileType , borders.get(i).getLeft(), currentTile.getLayer(), underTile);

                }//else{System.out.println("same" + tileset[xxx][yyy].getCurrentTileBorder() + " \n \n");}

            }

        }
    }

    /**
     *
     * @param tileset la map
     * @param x du currentTile
     * @param y du currentTile
     * @param currentTile
     * @return la liste des pairs border/coordonnées a rajouter sur la map
     */
    private List<Pair<Border,Point>> determineCornerTypeModify(Tile[][] tileset, int x, int y, Tile currentTile) {
        int maxX = nbTilesWidth - 1;
        int maxY = nbTilesHeight - 1;

        //2 tuiles en diagonale mais manque angle:
        //RIGHT BORDER

        /**Cas 1: HautGauche - RIGHT*/
        if (x < maxX  && y > 0 && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.RIGHT,Border.IBOTTOM_RIGHT))
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.RIGHT))
                && isFromLayerBelow(Arrays.asList(tileset[x+1][y],tileset[x][y-1]), currentTile))   {
            return Arrays.asList(Pair.of(Border.ORIGHT_BOTTOM, new Point(x,y)), Pair.of(Border.IBOTTOM_RIGHT, new Point(x+1, y)));
        }

        /**Cas 2: BasGauche - RIGHT*/
        if (x < maxX  && y < maxY && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.RIGHT))//Plus que ca ? coinsI
                && isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.RIGHT))
                && isFromLayerBelow(Arrays.asList(tileset[x+1][y],tileset[x][y+1]), currentTile))   {
            return Arrays.asList(Pair.of(Border.ORIGHT_TOP, new Point(x,y)), Pair.of(Border.ITOP_RIGHT, new Point(x+1, y)));
        }

        //LEFT BORDER

        /**Cas 3: BasDroite - LEFT*/
        if (x >0  && y < maxY && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.LEFT))//+?
                && isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.LEFT))
                && isFromLayerBelow(Arrays.asList(tileset[x-1][y],tileset[x][y+1]), currentTile))   {
            return Arrays.asList(Pair.of(Border.OTOP_LEFT, new Point(x,y)), Pair.of(Border.ILEFT_TOP, new Point(x-1, y)));
        }

        /**Cas 4: HautDroite - LEFT*/
        if (x >0  && y > 0 && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.LEFT))//+?
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.LEFT))
                && isFromLayerBelow(Arrays.asList(tileset[x-1][y],tileset[x][y-1]), currentTile))   {
            return Arrays.asList(Pair.of(Border.OBOTTOM_LEFT, new Point(x,y)), Pair.of(Border.ILEFT_BOTTOM, new Point(x-1, y)));
        }

        //BOTTOM BORDER

        /**Cas 5: HautDroite - BOTTOM*/
        if (x >0  && y > 0 && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.BOTTOM))//+?
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.BOTTOM))
                && isFromLayerBelow(Arrays.asList(tileset[x-1][y],tileset[x][y-1]), currentTile))   {
            return Arrays.asList(Pair.of(Border.OBOTTOM_LEFT, new Point(x,y)), Pair.of(Border.ILEFT_BOTTOM, new Point(x, y-1)));
        }

        /**Cas 6: HautGauche - BOTTOM*/
        if (x < maxX  && y > 0 && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.BOTTOM))//+?
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.BOTTOM))
                && isFromLayerBelow(Arrays.asList(tileset[x+1][y],tileset[x][y-1]), currentTile))   {
            return Arrays.asList(Pair.of(Border.ORIGHT_BOTTOM, new Point(x,y)), Pair.of(Border.IBOTTOM_RIGHT, new Point(x, y-1)));
        }

        //TOP BORDER

        /**Cas 7: BasGauche - TOP*/
        if (x < maxX  && y < maxY && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.TOP))//+?
                && isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.TOP))
                && isFromLayerBelow(Arrays.asList(tileset[x+1][y],tileset[x][y+1]), currentTile))   {
            return Arrays.asList(Pair.of(Border.ORIGHT_TOP, new Point(x,y)), Pair.of(Border.ITOP_RIGHT, new Point(x, y+1)));
        }

        /**Cas 8: BasDroite - TOP*/
        if (x > 0  && y < maxY && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.TOP))//+?
                && isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.TOP))
                && isFromLayerBelow(Arrays.asList(tileset[x-1][y],tileset[x][y+1]), currentTile))   {
            return Arrays.asList(Pair.of(Border.OTOP_LEFT, new Point(x,y)), Pair.of(Border.ILEFT_TOP, new Point(x, y+1)));
        }
        return null;
    }


    /**
     * Lorsqu'on veut mettre une tuile coin a un endroit où c'est la couche inférieurs
     * @param neighbors de la couche inférieure
     * @param current de la couche actuelle
     * @return
     */
    private boolean isFromLayerBelow(List<Tile> neighbors, Tile current){
        TileType tileTypeWanted = neighbors.get(0).getTileType();
        for(Tile neighbor : neighbors){
            if(!(neighbor.getTileType() == tileTypeWanted && current.getLayer()+1==neighbor.getLayer())){
                return false;
            }
        }
        return true;
    }
}
