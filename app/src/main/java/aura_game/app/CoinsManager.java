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
                    under = new Tile(currentTile.getTileType(),currentTile.getLayer());

                }else {//if(currentTile.getCurrentTileBorder().isSimpleBorder()){   //TODO: pb de sous tile par moment
                    under = new Tile(currentTile.getUnderTile().getTileType(), currentTile.getLayer());//Si la tuile était une bordure simple alors la futur underTile sera la underTile de current
                }
                if(under != null) {
                    checkNeighborsAndSetCoins(tileset, i, j, currentTile, under);
                }

            }
        }
    }

    /** Verifie avec les 8 cases autour si la tuile x y doit devenir un coin out ou in
     * Ne modifie que la tuile a la position x y
     * @param tileset
     * @param x
     * @param y
     * @param currentTile
     */
    private void checkNeighborsAndSetCoins(Tile[][] tileset, int x, int y, Tile currentTile, Tile underTile) {
        // Détermine le type de coin en fonction de la disposition
        Border border = determineOutCornerType(tileset,x, y, currentTile);//OUT COINS CHECK
        if(border.equals(Border.NOBORDER) || currentTile.getCurrentTileBorder().equals(border)){//Si c'est une sans bordure ou que la bordure déterminé est celle qui était déjà là
            border = determineInCornerType(tileset,x, y, currentTile);
        }

        if(!border.equals(Border.NOBORDER) && !currentTile.getCurrentTileBorder().equals(border)) {//Si il y a une bordure et que c'est pas la meme que la précédente
            // Met à jour la tuile actuelle avec le coin approprié
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
                && areCompatibleTiles(Arrays.asList(tileset[x+1][y],tileset[x][y-1]), currentTile)){
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


    /**
     * Défini la couche inférieur des tuiles puis lance {@code checkNeighborsAndSetCoinsModify}.
     * @param tileset
     */
    public void addCoinsToBorderModify(Tile[][] tileset) {
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                Tile currentTile = tileset[i][j];

                // Vérifie la tuile i j dois devenir une tuile
                Tile under = null;

                if((currentTile.getCurrentTileBorder().equals(Border.NOBORDER))){//Si la tuile testé était une no bordure alors la futur underTile sera la current
                    under = new Tile(currentTile.getTileType(),currentTile.getLayer());

                }else {
                    under = new Tile(currentTile.getUnderTile().getTileType(),currentTile.getLayer()/*-1?*/);//Si la tuile était une bordure simple alors la futur underTile sera la underTile de current
                }

                if(under != null) {
                    checkNeighborsAndSetCoinsModify(tileset, i, j, currentTile, under);
                }

            }
        }
    }



    //TODO 3105 ne marche pas ! Pourrais je simplement faire lorsqu'une bordure out bordure n'est pas connecté des deux cotés, la transformer en bordure non coin du type du coté bien connecté

    /**
     * Appelé après {@code addCoinsToBorderModify} pour s'assurer que toutes les bordures sont connectées.
     * @param tileset
     */
    public void connectUnConnectedBordersNeighbors(Tile[][] tileset) {
        //System.out.println("before: " +tileset[29][9].toString());
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                Tile currentTile = tileset[i][j];

                // Vérifie la tuile i j dois devenir une tuile
                Tile under = null;

                if((currentTile.getCurrentTileBorder().equals(Border.NOBORDER))){//Si la tuile testé était une no bordure alors la futur underTile sera la current
                    under = new Tile(currentTile.getTileType(),currentTile.getLayer());

                }else {
                    under = new Tile(currentTile.getUnderTile().getTileType(),currentTile.getLayer()/*-1?*/);//Si la tuile était une bordure simple alors la futur underTile sera la underTile de current
                }

                if(under != null) {
                    checkNeightborsAndConnectThem(tileset, i, j, currentTile, under);
                }

            }
        }
        //System.out.println("after: " +tileset[29][9].toString());
    }
    public void printLayerOfAll(Tile[][] tileset){
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                System.out.println("x:"+i+" y:"+j+" layer:"+tileset[i][j].getLayer());
            }
        }
    }

    public void checkNeightborsAndConnectThem(Tile[][] tileset, int x, int y, Tile currentTile, Tile underTile){
    //Si un voisin devrait etre connecté avec le current tile on renvoie le type de bordure a mettre
        Border border = null;
        if(currentTile.getCurrentTileBorder().getType()=="in") {
            border = getConnectBordersIfPossible(tileset, x, y, currentTile);
        }

        if(border!=null) {
        // Met à jour la tuile
            tileset[x][y] = new Tile(currentTile.getTileType() , border, currentTile.getLayer(), underTile);
            //System.out.println("update tile at x:"+x+" y:"+y+" with border "+border);

        }
    }

    /**
     * Vérifie si les deux bordures sont connectées et les connecte si nécessaire.
     * @param tileset
     * @param x
     * @param y
     * @param currentTile (a border)
     * @return la liste des pairs border/coordonnées a modifier sur la map (etant les deux bordures mal connectées)
     */
    //TODO PROBLEM HERE
    private Border getConnectBordersIfPossible(Tile[][] tileset, int x, int y, Tile currentTile) {
    //On vérifie avec son voisin de gauche, droite haut et bas que si jamais les deux sont des bordures de meme type et niveau elles sont bien connectés
        int maxX = nbTilesWidth - 1;
        int maxY = nbTilesHeight - 1;

        // horizontal vers la droite depuis le current
        if(x==29 && y==9) {
            //System.out.println("connectable to right:" + currentTile.isConnectableWith(tileset[x + 1][y]) + "is actually ?" + whatCanBeConnectedTo(currentTile.getCurrentTileBorder(), "r").contains(tileset[x + 1][y].getCurrentTileBorder()));
        }
            if(x < maxX && currentTile.isConnectableWith(tileset[x+1][y]) && !(whatCanBeConnectedTo(currentTile.getCurrentTileBorder(), "r").contains(tileset[x+1][y].getCurrentTileBorder()))){
            //System.out.println("a connect right correction needed. x "+ x + " y:"+ y);
            return currentTile.getCurrentTileBorder().getFirstAsBorder();
        }

        // horizontal vers la gauche
        if(x > 0 && currentTile.isConnectableWith(tileset[x-1][y]) && !(whatCanBeConnectedTo(currentTile.getCurrentTileBorder(),"l").contains(tileset[x-1][y].getCurrentTileBorder()))){
            //System.out.println("a connect left correction needed");
            return currentTile.getCurrentTileBorder().getFirstAsBorder();
        }

        // vertical vers le haut
        if(y < maxY && currentTile.isConnectableWith(tileset[x][y+1]) && !(whatCanBeConnectedTo(currentTile.getCurrentTileBorder(),"t").contains(tileset[x][y+1].getCurrentTileBorder()))){
            //System.out.println("a connect top correction needed");
            return currentTile.getCurrentTileBorder().getFirstAsBorder();
        }

        // vertical vers le bas
        if(y > 0 && currentTile.isConnectableWith(tileset[x][y-1]) && !(whatCanBeConnectedTo(currentTile.getCurrentTileBorder(),"b").contains(tileset[x][y-1].getCurrentTileBorder()))){
            //System.out.println("a connect bottom correction needed");
            return currentTile.getCurrentTileBorder().getFirstAsBorder();

        }
        return null;

    }

    /** Ajoute des coins aux tuiles de bordure en fonction de leur voisinage dans le tableau 2D de tuiles. Met a jour les tuiles déjà calculés afin de corriger les coins incorrect / manquants.
     * Peut modifier la tuile xy ou celles autour / en ajouter si nécessaire
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
                    TileType neighborTileType = tileset[borders.get(1).getRight().getX()][borders.get(1).getRight().getY()].getTileType();//on prend le 1 (1er avec la texture cible) car le 0 correspond a l'actuel donc a la case vide soit la texture différente
                    //System.out.println("before update, tile at x:"+xxx+" y:"+yyy+" is "+tileset[xxx][yyy].toString());
                    tileset[xxx][yyy] = new Tile(neighborTileType , borders.get(i).getLeft(), currentTile.getLayer()+1, underTile);

                    //System.out.println("after update, tile at x:"+xxx+" y:"+yyy+" is now "+tileset[xxx][yyy].toString()+ "\n");
                }

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

        /*Cas 1: HautGauche - RIGHT*/
        if (x < maxX  && y > 0 && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.RIGHT,Border.IBOTTOM_RIGHT))
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.RIGHT, Border.IBOTTOM_RIGHT))
                && isFromLayerBelow(Arrays.asList(tileset[x+1][y],tileset[x][y-1]), currentTile))   {//Ya rien a ce niveau sur la case x y
            return Arrays.asList(Pair.of(Border.ORIGHT_BOTTOM, new Point(x,y)), Pair.of(Border.IBOTTOM_RIGHT, new Point(x+1, y)));
        }

        /*Cas 2: BasGauche - RIGHT*/
        if (x < maxX  && y < maxY && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.RIGHT))//Plus que ca ? coinsI
                && isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.RIGHT, Border.ITOP_RIGHT))
                && isFromLayerBelow(Arrays.asList(tileset[x+1][y],tileset[x][y+1]), currentTile))   {
            return Arrays.asList(Pair.of(Border.ORIGHT_TOP, new Point(x,y)), Pair.of(Border.ITOP_RIGHT, new Point(x+1, y)));
        }

        //LEFT BORDER

        /*Cas 3: BasDroite - LEFT*/
        if (x >0  && y < maxY && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.LEFT))//+?
                && isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.LEFT,Border.ILEFT_TOP))
                && isFromLayerBelow(Arrays.asList(tileset[x-1][y],tileset[x][y+1]), currentTile))   {
            return Arrays.asList(Pair.of(Border.OTOP_LEFT, new Point(x,y)), Pair.of(Border.ILEFT_TOP, new Point(x-1, y)));
        }

        /*Cas 4: HautDroite - LEFT*/
        if (x >0  && y > 0 && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.LEFT))//+?
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.LEFT,Border.ILEFT_BOTTOM))
                && isFromLayerBelow(Arrays.asList(tileset[x-1][y],tileset[x][y-1]), currentTile))   {
            return Arrays.asList(Pair.of(Border.OBOTTOM_LEFT, new Point(x,y)), Pair.of(Border.ILEFT_BOTTOM, new Point(x-1, y)));
        }

        //BOTTOM BORDER

        /*Cas 5: HautDroite - BOTTOM*/
        if (x >0  && y > 0 && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.BOTTOM,Border.ILEFT_BOTTOM))//+?
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.BOTTOM))
                && isFromLayerBelow(Arrays.asList(tileset[x-1][y],tileset[x][y-1]), currentTile))   {
            return Arrays.asList(Pair.of(Border.OBOTTOM_LEFT, new Point(x,y)), Pair.of(Border.ILEFT_BOTTOM, new Point(x, y-1)));
        }

        /*Cas 6: HautGauche - BOTTOM*/
        if (x < maxX  && y > 0 && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.BOTTOM,Border.IBOTTOM_RIGHT))//+?
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.BOTTOM))
                && isFromLayerBelow(Arrays.asList(tileset[x+1][y],tileset[x][y-1]), currentTile))   {
            return Arrays.asList(Pair.of(Border.ORIGHT_BOTTOM, new Point(x,y)), Pair.of(Border.IBOTTOM_RIGHT, new Point(x, y-1)));
        }

        //TOP BORDER

        /*Cas 7: BasGauche - TOP*/
        if (x < maxX  && y < maxY && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.TOP,Border.ITOP_RIGHT))//+?
                && isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.TOP))
                && isFromLayerBelow(Arrays.asList(tileset[x+1][y],tileset[x][y+1]), currentTile))   {//Garantit que la case x y est bien de la couche inférieure (vide)
            return Arrays.asList(Pair.of(Border.ORIGHT_TOP, new Point(x,y)), Pair.of(Border.ITOP_RIGHT, new Point(x, y+1)));
        }

        /*Cas 8: BasDroite - TOP*/
        if (x > 0  && y < maxY && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.TOP,Border.ILEFT_TOP))//+?
                && isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.TOP))
                && isFromLayerBelow(Arrays.asList(tileset[x-1][y],tileset[x][y+1]), currentTile))   {
            return Arrays.asList(Pair.of(Border.OTOP_LEFT, new Point(x,y)), Pair.of(Border.ILEFT_TOP, new Point(x, y+1)));
        }
        return null;
    }


    /**
     * Lorsqu'on veut mettre une tuile coin a un endroit où c'est la couche inférieurs
     * @param neighbors de la couche actuelle
     * @param current de la couche inferieure
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

    /**
     * Donne la liste des bordures connectables avec la bordure actuelle, dans la direction donnée. (seulement les IN)
     * @param b bordure actuelle
     * @param direction "t" "r" "b" "l"
     * @return
     */
    private List<Border> whatCanBeConnectedTo(Border b, String direction){
        String borderDirection = b.toString() + "_" + direction;
        //if(borderDirection.equals("ITOP_RIGHT_r"))System.out.println("borderDirection: " + borderDirection);
        switch (borderDirection){
            //ILEFTTOP (1)
            case "ILEFT_TOP_t"://En haut
                return Arrays.asList();
            case "ILEFT_TOP_r"://A droite
                return Arrays.asList(Border.OTOP_LEFT,Border.TOP,Border.ITOP_RIGHT);
            case "ILEFT_TOP_b"://En bas
                return Arrays.asList(Border.OTOP_LEFT,Border.LEFT,Border.ILEFT_BOTTOM);
            case "ILEFT_TOP_l"://A gauche
                return Arrays.asList();

            //ITOPRIGHT (3)
            case "ITOP_RIGHT_t"://En haut
                return Arrays.asList();
            case "ITOP_RIGHT_r"://A droite
                return Arrays.asList();
            case "ITOP_RIGHT_b"://En bas
                return Arrays.asList(Border.ORIGHT_TOP,Border.RIGHT,Border.IBOTTOM_RIGHT);
            case "ITOP_RIGHT_l"://A gauche
                return Arrays.asList(Border.ORIGHT_TOP,Border.TOP,Border.ILEFT_TOP);

            //ILEFTBOTTOM (7)
            case "ILEFT_BOTTOM_t"://En haut
                return Arrays.asList(Border.OBOTTOM_LEFT,Border.LEFT,Border.ILEFT_BOTTOM);
            case "ILEFT_BOTTOM_r"://A droite
                return Arrays.asList(Border.OBOTTOM_LEFT,Border.BOTTOM,Border.IBOTTOM_RIGHT);
            case "ILEFT_BOTTOM_b"://En bas
                return Arrays.asList();
            case "ILEFT_BOTTOM_l"://A gauche
                return Arrays.asList();

            //IBOTTOMRIGHT (9)
            case "IBOTTOM_RIGHT_t"://En haut
                return Arrays.asList(Border.ORIGHT_BOTTOM,Border.RIGHT,Border.ITOP_RIGHT);
            case "IBOTTOM_RIGHT_r"://A droite
                return Arrays.asList();
            case "IBOTTOM_RIGHT_b"://En bas
                return Arrays.asList();
            case "IBOTTOM_RIGHT_l"://A gauche
                return Arrays.asList(Border.ORIGHT_BOTTOM,Border.BOTTOM,Border.ILEFT_BOTTOM);

            case "TOP_t"://En haut d'une border top
                return Arrays.asList();//nothing
            case "TOP_r"://A droite d'une border top
                return Arrays.asList(Border.OTOP_LEFT,Border.TOP,Border.ITOP_RIGHT);
            case "TOP_b"://En bas d'une border top
                return Arrays.asList();//nothing
            case "TOP_l"://A gauche d'une border top
                return Arrays.asList(Border.ORIGHT_TOP,Border.TOP,Border.ILEFT_TOP);

            case "RIGHT_t"://En haut d'une border right
                return Arrays.asList(Border.ORIGHT_BOTTOM,Border.RIGHT,Border.ITOP_RIGHT);
            case "RIGHT_r"://A droite d'une border right
                return Arrays.asList();//nothing
            case "RIGHT_b"://En bas d'une border right
                return Arrays.asList(Border.ORIGHT_TOP,Border.RIGHT,Border.IBOTTOM_RIGHT);
            case "RIGHT_l"://A gauche d'une border right
                return Arrays.asList();//nothing

            case "BOTTOM_t"://En haut d'une border bottom
                return Arrays.asList();//nothing
            case "BOTTOM_r"://A droite d'une border bottom
                return Arrays.asList(Border.OBOTTOM_LEFT,Border.BOTTOM,Border.IBOTTOM_RIGHT);
            case "BOTTOM_b"://En bas d'une border bottom
                return Arrays.asList();//nothing
            case "BOTTOM_l"://A gauche d'une border bottom
                return Arrays.asList(Border.ORIGHT_BOTTOM,Border.BOTTOM,Border.ILEFT_BOTTOM);

            case "LEFT_t"://En haut d'une border left
                return Arrays.asList(Border.OBOTTOM_LEFT,Border.LEFT,Border.ILEFT_TOP);
            case "LEFT_r"://A droite d'une border left
                return Arrays.asList();//nothing
            case "LEFT_b"://En bas d'une border left
                return Arrays.asList(Border.OTOP_LEFT,Border.LEFT,Border.ILEFT_BOTTOM);
            case "LEFT_l"://A gauche d'une border left
                return Arrays.asList();//nothing
            case "ORIGHT_BOTTOM_t":
                return Arrays.asList();
            case "ORIGHT_BOTTOM_r":
                return Arrays.asList(Border.OBOTTOM_LEFT,Border.BOTTOM,Border.IBOTTOM_RIGHT);
            case "ORIGHT_BOTTOM_b":
                return Arrays.asList(Border.ORIGHT_TOP,Border.RIGHT,Border.IBOTTOM_RIGHT);
            case "ORIGHT_BOTTOM_l":
                return Arrays.asList();
            case "OBOTTOM_LEFT_t":
                return Arrays.asList();
            case "OBOTTOM_LEFT_r":
                return Arrays.asList();
            case "OBOTTOM_LEFT_b":
                return Arrays.asList(Border.OTOP_LEFT,Border.LEFT,Border.ILEFT_BOTTOM);
            case "OBOTTOM_LEFT_l":
                return Arrays.asList(Border.ORIGHT_BOTTOM,Border.BOTTOM,Border.ILEFT_BOTTOM);
            case "ORIGHT_TOP_t":
                return Arrays.asList(Border.ORIGHT_BOTTOM,Border.RIGHT,Border.ITOP_RIGHT);
            case "ORIGHT_TOP_r":
                return Arrays.asList(Border.OTOP_LEFT,Border.TOP,Border.ITOP_RIGHT);
            case "ORIGHT_TOP_b":
                return Arrays.asList();
            case "ORIGHT_TOP_l":
                return Arrays.asList();
            case "OTOP_LEFT_t":
                return Arrays.asList(Border.OBOTTOM_LEFT,Border.LEFT,Border.ILEFT_TOP);
            case "OTOP_LEFT_r":
                return Arrays.asList();
            case "OTOP_LEFT_b":
                return Arrays.asList();
            case "OTOP_LEFT_l":
                return Arrays.asList(Border.ORIGHT_TOP,Border.TOP,Border.ILEFT_TOP);

            default:
                System.out.println("Error in whatCanBeConnectedTo");
                return Arrays.asList();
        }
    }


    /**
     * Appelé en dernier, corrige les cases borders qui étaient "mal formé".
     * @param tileset
     */
    public void addCoinsToBorderThatWasUnlinked(Tile[][] tileset) {
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                Tile currentTile = tileset[i][j];

                // Vérifie la tuile i j dois devenir une tuile
                Tile under = null;

                if((currentTile.getCurrentTileBorder().equals(Border.NOBORDER))){//Si la tuile testé était une no bordure alors la futur underTile sera la current
                    under = new Tile(currentTile.getTileType(),currentTile.getLayer());

                }else {
                    under = new Tile(currentTile.getUnderTile().getTileType(),currentTile.getLayer()/*-1?*/);//Si la tuile était une bordure simple alors la futur underTile sera la underTile de current
                }

                if(under != null) {
                    correctUnlinkedBordersParralelOrPerpendicular(tileset, i, j, currentTile, under);
                }

            }
        }
    }


    public void correctUnlinkedBordersParralelOrPerpendicular(Tile[][] tileset, int x, int y, Tile currentTile, Tile underTile) {
        // Déterminez le type de coin en fonction de la disposition
        List<Pair<Border,Point>> borders = connectUnlinkedBorders(tileset,x, y, currentTile);//COINS IF SOME MODIF CHECK

        if(borders!=null) {
            // Met à jour les tuiles
            for (int i = 0; i < borders.size(); i++) {
                int xxx = borders.get(i).getRight().getX();
                int yyy = borders.get(i).getRight().getY();

                if(!tileset[xxx][yyy].getCurrentTileBorder().equals(borders.get(i).getLeft())){//Si la bordure qu'on veut mettre est pas déjà l'actuelle

                    //System.out.println("print x:"+xxx+" y:"+yyy+" border:"+borders.get(i).getLeft());
                    tileset[xxx][yyy] = new Tile(currentTile.getTileType() , borders.get(i).getLeft(), currentTile.getLayer(), underTile);
                }
                //System.out.println("correctUnlinkedBordersParralelOrPerpendicular:");
                //System.out.println("updated x:"+xxx+" y:"+yyy+" border:"+borders.get(i).getLeft());
            }

        }
    }

    /**LAST CHECK*/
    public List<Pair<Border,Point>> connectUnlinkedBorders(Tile[][] tileset, int x, int y, Tile currentTile){
        //VOISIN PERPENDICULAIRES
        //Vertical vers le haut
        //Cas A
        int maxX = nbTilesWidth - 1;
        int maxY = nbTilesHeight - 1;

        if (y < maxY && x < maxX && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.TOP)  )
                && isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.LEFT, Border.OBOTTOM_LEFT))
                && areCompatibleTiles(Arrays.asList(tileset[x][y+1]), currentTile)
                && tileset[x+1][y].getCurrentTileBorder().getType().equals("noborder")) {//Case a droite non lié ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.OTOP_LEFT, new Point(x,y)));
        }

        //Cas B:
        if (y < maxY && x > 0 && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.TOP)  )
                && isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.RIGHT, Border.ORIGHT_BOTTOM))
                && areCompatibleTiles(Arrays.asList(tileset[x][y+1]), currentTile)
                && tileset[x-1][y].getCurrentTileBorder().getType().equals("noborder")) {//Case a gauche non lié ! //TODO peut etre a coté d'une bordure mais non lié ?

                return Arrays.asList(Pair.of(Border.ORIGHT_TOP, new Point(x,y)));
        }

        //Vertical vers le haut
        //Cas C:
        if (y > 0 && x < maxX && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.BOTTOM)  )
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.LEFT, Border.OTOP_LEFT))
                && areCompatibleTiles(Arrays.asList(tileset[x][y-1]), currentTile)
                && tileset[x+1][y].getCurrentTileBorder().getType().equals("noborder")) {//Case a droite non lié ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.OBOTTOM_LEFT, new Point(x,y)));
        }

        //Cas D:
        if (y > 0 && x > 0 && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.BOTTOM)  )
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.RIGHT, Border.ORIGHT_TOP))
                && areCompatibleTiles(Arrays.asList(tileset[x][y-1]), currentTile)
                && tileset[x-1][y].getCurrentTileBorder().getType().equals("noborder")) {//Case a gauche non lié PAS  ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.ORIGHT_BOTTOM, new Point(x,y)));
        }

        //Horizontal vers la droite
        //Cas E:
        if (x < maxX && y > 0 && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.RIGHT)  )
                && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.TOP, Border.OTOP_LEFT))
                && areCompatibleTiles(Arrays.asList(tileset[x+1][y]), currentTile)
                && tileset[x][y-1].getCurrentTileBorder().getType().equals("noborder")) {//Case en haut non lié ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.ORIGHT_TOP, new Point(x,y)));
        }

        //Cas F:

        if (x < maxX && y < maxY && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.RIGHT)  )
                && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.BOTTOM, Border.OBOTTOM_LEFT))
                && areCompatibleTiles(Arrays.asList(tileset[x+1][y]), currentTile)
                && tileset[x][y+1].getCurrentTileBorder().getType().equals("noborder")) {//Case en bas non lié ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.ORIGHT_BOTTOM, new Point(x,y)));
        }

        //Horizontal vers la gauche

        //Cas G:
        if (x > 0 && y > 0 && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.LEFT)  )
                && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.TOP, Border.OTOP_LEFT))
                && areCompatibleTiles(Arrays.asList(tileset[x-1][y]), currentTile)
                && tileset[x][y-1].getCurrentTileBorder().getType().equals("noborder")) {//Case en haut non lié ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.OTOP_LEFT, new Point(x,y)));
        }

        //Cas H:

        if (x > 0 && y < maxY && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.LEFT)  )
                && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.BOTTOM, Border.ORIGHT_BOTTOM))
                && areCompatibleTiles(Arrays.asList(tileset[x-1][y]), currentTile)
                && tileset[x][y+1].getCurrentTileBorder().getType().equals("noborder")) {//Case en bas non lié ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.OBOTTOM_LEFT, new Point(x,y)));
        }

        //VOISIN PARALLELE
        //CAS VR1 (vertical right b)
        if (x < maxX && y < maxY && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.TOP)  )
                && isOneOfThesesBorder(tileset[x][y+1], Arrays.asList(Border.BOTTOM, Border.OBOTTOM_LEFT))
                && areCompatibleTiles(Arrays.asList(tileset[x][y+1]), currentTile)
                && tileset[x+1][y].getCurrentTileBorder().getType().equals("noborder")
                && tileset[x+1][y+1].getCurrentTileBorder().getType().equals("noborder")) {//Cases droites non lié ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.OTOP_LEFT, new Point(x,y)), Pair.of(Border.OBOTTOM_LEFT, new Point(x,y+1)));
        }

        //CAS VR2 (vertical right t)
        if (x < maxX && y > 0 && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.TOP)  )
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.BOTTOM, Border.ORIGHT_BOTTOM))
                && areCompatibleTiles(Arrays.asList(tileset[x][y-1]), currentTile)
                && tileset[x+1][y].getCurrentTileBorder().getType().equals("noborder")
                && tileset[x+1][y-1].getCurrentTileBorder().getType().equals("noborder")) {//Cases droites non lié ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.ORIGHT_TOP, new Point(x,y)), Pair.of(Border.ORIGHT_BOTTOM, new Point(x,y-1)));
        }

        //CAS VL2 (vertical left b)
        if (x > 0 && y > 0 && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.BOTTOM)  )
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.TOP, Border.OTOP_LEFT))
                && areCompatibleTiles(Arrays.asList(tileset[x][y-1]), currentTile)
                && tileset[x-1][y].getCurrentTileBorder().getType().equals("noborder")
                && tileset[x-1][y-1].getCurrentTileBorder().getType().equals("noborder")) {//Cases droites non lié ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.OBOTTOM_LEFT, new Point(x,y)), Pair.of(Border.OTOP_LEFT, new Point(x,y-1)));
        }

        //CAS VL2 (vertical left t)
        if (x > 0 && y > 0 && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.BOTTOM)  )
                && isOneOfThesesBorder(tileset[x][y-1], Arrays.asList(Border.TOP, Border.ORIGHT_TOP))
                && areCompatibleTiles(Arrays.asList(tileset[x][y-1]), currentTile)
                && tileset[x-1][y].getCurrentTileBorder().getType().equals("noborder")
                && tileset[x-1][y-1].getCurrentTileBorder().getType().equals("noborder")) {//Cases droites non lié ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.ORIGHT_BOTTOM, new Point(x,y)), Pair.of(Border.ORIGHT_TOP, new Point(x,y-1)));
        }

        //CAS HT1 (horizontal top r)
        if (x > 0 && y < maxY && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.LEFT)  )
                && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.RIGHT, Border.ORIGHT_BOTTOM))
                && areCompatibleTiles(Arrays.asList(tileset[x-1][y]), currentTile)
                && tileset[x][y+1].getCurrentTileBorder().getType().equals("noborder")
                && tileset[x-1][y+1].getCurrentTileBorder().getType().equals("noborder")) {//Cases droites non lié ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.OBOTTOM_LEFT, new Point(x,y)), Pair.of(Border.ORIGHT_BOTTOM, new Point(x-1,y)));
        }

        //CAS HT2 (horizontal top l)
        if (x < maxX && y < maxY && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.RIGHT)  )
                && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.LEFT, Border.OBOTTOM_LEFT))
                && areCompatibleTiles(Arrays.asList(tileset[x+1][y]), currentTile)
                && tileset[x][y+1].getCurrentTileBorder().getType().equals("noborder")
                && tileset[x+1][y+1].getCurrentTileBorder().getType().equals("noborder")) {//Cases droites non lié ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.ORIGHT_BOTTOM, new Point(x,y)), Pair.of(Border.OBOTTOM_LEFT, new Point(x+1,y)));
        }

        //CAS HB1 (horizontal bottom r)
        if (x > 0 && y > 0 && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.LEFT)  )
                && isOneOfThesesBorder(tileset[x-1][y], Arrays.asList(Border.RIGHT, Border.ORIGHT_TOP))
                && areCompatibleTiles(Arrays.asList(tileset[x-1][y]), currentTile)
                && tileset[x][y-1].getCurrentTileBorder().getType().equals("noborder")
                && tileset[x-1][y-1].getCurrentTileBorder().getType().equals("noborder")) {//Cases droites non lié ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.OTOP_LEFT, new Point(x,y)), Pair.of(Border.ORIGHT_TOP, new Point(x-1,y)));
        }

        //CAS HB2 (horizontal bottom l)
        if (x < maxX && y > 0 && isOneOfThesesBorder(tileset[x][y], Arrays.asList(Border.RIGHT)  )
                && isOneOfThesesBorder(tileset[x+1][y], Arrays.asList(Border.LEFT, Border.OTOP_LEFT))
                && areCompatibleTiles(Arrays.asList(tileset[x+1][y]), currentTile)
                && tileset[x][y-1].getCurrentTileBorder().getType().equals("noborder")
                && tileset[x+1][y-1].getCurrentTileBorder().getType().equals("noborder")) {//Cases droites non lié ! //TODO peut etre a coté d'une bordure mais non lié ?
            return Arrays.asList(Pair.of(Border.ORIGHT_TOP, new Point(x,y)), Pair.of(Border.OTOP_LEFT, new Point(x+1,y)));
        }
        return null;
    }

    /**
     * Vérifie si les tuiles sont bien connectés (avec whatCanBeConnectedTo) (si ils sont de la meme couche)
     * @param tileset
     * Envoie un message d'erreur si les tuiles ne sont pas bien connectées
     */
    public void checkIfAllBordersAreConnected(Tile[][] tileset) {
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                int maxX = nbTilesWidth - 1;
                int maxY = nbTilesHeight - 1;
                Tile currentTile = tileset[i][j];
                //Si c'est une bordure
                if (!currentTile.getCurrentTileBorder().getType().equals("noborder")) {
                    //System.out.println(currentTile.getCurrentTileBorder().getType() + " at x:" + i + " y:" + j);
                    // horizontal vers la droite depuis le current
                    boolean connected = false;
                    if(i<maxX && currentTile.isConnectableWith(tileset[i + 1][j])) {
                        connected = whatCanBeConnectedTo(currentTile.getCurrentTileBorder(), "r").contains(tileset[i + 1][j].getCurrentTileBorder());
                        if (!connected) {
                            System.out.println("Tile bad connected to right at x:" + i + " y:" + j);
                        }
                    }


                    if(i>0 && currentTile.isConnectableWith(tileset[i - 1][j])) {
                        connected = whatCanBeConnectedTo(currentTile.getCurrentTileBorder(), "l").contains(tileset[i - 1][j].getCurrentTileBorder());
                        // horizontal vers la gauche
                        if (!connected) {
                            System.out.println("Tile bad connected to left at x:" + i + " y:" + j);
                        }
                    }

                    if(j < maxY && currentTile.isConnectableWith(tileset[i][j + 1])) {
                        connected = whatCanBeConnectedTo(currentTile.getCurrentTileBorder(), "t").contains(tileset[i][j + 1].getCurrentTileBorder());
                        // vertical vers le haut
                        if (!connected) {
                            System.out.println("Tile bad connected to top at x:" + i + " y:" + j);
                        }
                    }

                    if(j > 0 && currentTile.isConnectableWith(tileset[i][j - 1])) {
                        connected = whatCanBeConnectedTo(currentTile.getCurrentTileBorder(), "b").contains(tileset[i][j - 1].getCurrentTileBorder());
                        // vertical vers le bas
                        if (!connected) {
                            System.out.println("Tile bad connected to bottom at x:" + i + " y:" + j);
                        }
                    }
                }


            }


        }


    }


    /**
     * Supprime toutes les bordures de la map qui ne sont connectés correctement à aucune autre bordure
     * @param tileset
     * @param connectionsToChase le nombre de connections que la bordure doit avoir être supprimée
     */
    public void removeUnlinkedBorders(Tile[][] tileset, int connectionsToChase) {
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                Tile currentTile = tileset[i][j];
                if (!currentTile.getCurrentTileBorder().getType().equals("noborder")) {
                    int connectedTo = 0;
                    if(i<nbTilesWidth-1 && currentTile.isConnectableWith(tileset[i + 1][j])) {
                        if (whatCanBeConnectedTo(currentTile.getCurrentTileBorder(), "r").contains(tileset[i + 1][j].getCurrentTileBorder())) {
                            connectedTo++;
                        }
                    }

                    if(i>0 && currentTile.isConnectableWith(tileset[i - 1][j])) {
                        if (whatCanBeConnectedTo(currentTile.getCurrentTileBorder(), "l").contains(tileset[i - 1][j].getCurrentTileBorder())) {
                            connectedTo++;
                        }
                    }

                    if(j<nbTilesHeight-1 && currentTile.isConnectableWith(tileset[i][j + 1])) {
                        if (whatCanBeConnectedTo(currentTile.getCurrentTileBorder(), "t").contains(tileset[i][j + 1].getCurrentTileBorder())) {
                            connectedTo++;
                        }
                    }

                    if(j>0 && currentTile.isConnectableWith(tileset[i][j - 1])) {
                        if (whatCanBeConnectedTo(currentTile.getCurrentTileBorder(), "b").contains(tileset[i][j - 1].getCurrentTileBorder())) {
                            connectedTo++;
                        }
                    }
                    if(connectedTo == connectionsToChase){
                        System.out.println("Unlinked border at x:" + i + " y:" + j);
                        tileset[i][j] = new Tile(currentTile.getTileType(),Border.NOBORDER,currentTile.getLayer()-1);
                    }
                }
            }
        }
    }


}
