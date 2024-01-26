package aura_game.app;

import org.apache.commons.lang3.tuple.Pair;

public enum TileType {

    DARKGRASS(new Pair[]{Pair.of(1,1),Pair.of(3,2)},    Pair.of(2,1),Pair.of(1,0),Pair.of(1,2),Pair.of(0,1),
            Pair.of(0,0), Pair.of(0,2),Pair.of(2,0), Pair.of(2,2),//COINS Out
            Pair.of(3,0), Pair.of(3,1),Pair.of(4,0), Pair.of(4,1)),//COINS In

    GRASS(new Pair[]{Pair.of(1,4),Pair.of(3,5)},    Pair.of(2,4),Pair.of(1,3),Pair.of(1,5),Pair.of(0,4),
            Pair.of(0,3), Pair.of(0,5),Pair.of(2,3), Pair.of(2,5),//COINS Out
            Pair.of(3,3), Pair.of(3,4),Pair.of(4,3), Pair.of(4,4)),//COINS In

    CLEARGRASS(new Pair[]{Pair.of(1,7),Pair.of(3,8)},   Pair.of(2,7),Pair.of(1,6),Pair.of(1,8),Pair.of(0,7),
            Pair.of(0,6), Pair.of(0,8),Pair.of(2,6), Pair.of(2,8 ),//COINS Out
            Pair.of(3,6), Pair.of(3,7),Pair.of(4,6), Pair.of(4,7)),//COINS In

    SNOW(new Pair[]{Pair.of(1,10),Pair.of(3,11)},   Pair.of(2,10),Pair.of(1,9),Pair.of(1,11),Pair.of(0,10),
            Pair.of(0,9), Pair.of(0,11),Pair.of(2,9), Pair.of(2,11 ),//COINS Out
            Pair.of(3,9), Pair.of(3,10),Pair.of(4,9), Pair.of(4,10)),//COINS In

    DIRT(new Pair[]{Pair.of(1,13),Pair.of(3,14)},   Pair.of(2,13),Pair.of(1,12),Pair.of(1,14),Pair.of(0,13),
            Pair.of(0,12), Pair.of(0,14),Pair.of(2,12), Pair.of(2,14 ),//COINS Out
            Pair.of(3,12), Pair.of(3,13),Pair.of(4,12), Pair.of(4,13)),//COINS In

    ROCK(new Pair[]{Pair.of(1,16),Pair.of(3,17)},   Pair.of(2,16),Pair.of(1,15),Pair.of(1,17),Pair.of(0,16),
            Pair.of(0,15), Pair.of(0,17),Pair.of(2,15), Pair.of(2,17 ),//COINS Out
            Pair.of(3,15), Pair.of(3,16),Pair.of(4,15), Pair.of(4,16)),//COINS In

    DARKSAND(new Pair[]{Pair.of(1,19),Pair.of(3,20)},   Pair.of(2,19),Pair.of(1,18),Pair.of(1,20),Pair.of(0,19),
            Pair.of(0,18), Pair.of(0,20),Pair.of(2,18), Pair.of(2,20 ),//COINS Out
            Pair.of(3,18), Pair.of(3,19),Pair.of(4,18), Pair.of(4,19)),//COINS In

    SAND(new Pair[]{Pair.of(1,22),Pair.of(3,23)},   Pair.of(2,22),Pair.of(1,21),Pair.of(1,23),Pair.of(0,22),
            Pair.of(0,21), Pair.of(0,23),Pair.of(2,21), Pair.of(2,23 ),//COINS Out
            Pair.of(3,21), Pair.of(3,22),Pair.of(4,21), Pair.of(4,22)),//COINS In

    WATER(new Pair[]{Pair.of(1,25),Pair.of(1,26)/*, Pair.of(2,25),Pair.of(2,26)*/ },    Pair.of(0,26),Pair.of(1,27),Pair.of(1,24),Pair.of(3,25),
            Pair.of(4,24), Pair.of(4,25),Pair.of(5,24), Pair.of(5,25),//COINS In
            Pair.of(0,24), Pair.of(0,27),Pair.of(3,24), Pair.of(3,27 )),//COINS Out


    CLEARWATER(new Pair[]{Pair.of(3,30),Pair.of(4,30)},Pair.of(2,29),Pair.of(1,28),Pair.of(1,30),Pair.of(0,29),
            Pair.of(3,28), Pair.of(4,29),Pair.of(3,28), Pair.of(4,29),//COINS In
            Pair.of(0,28), Pair.of(0,30),Pair.of(2,28), Pair.of(2,30 )),//COINS Out


    DARKWATER(new Pair[]{Pair.of(1,35),Pair.of(3,36) },     Pair.of(0,35),Pair.of(1,36),Pair.of(1,34),Pair.of(3,35),
            Pair.of(3,34), Pair.of(4,35),Pair.of(3,34), Pair.of(4,35),//COINS In
            Pair.of(0,34), Pair.of(0,36),Pair.of(2,34), Pair.of(2,36 )),//COINS Out

    ;





    /**Texture de base, tableau pour avoir plusieurs variantes différentes...*/
    private final Pair<Integer,Integer>[] textureDefaultIndex;
    //Index (de indexborders) des textures
    private final Pair<Integer,Integer> topBorderIndex;
    private final Pair<Integer,Integer> rightBorderIndex;
    private final Pair<Integer,Integer> leftBorderIndex;
    private final Pair<Integer,Integer> bottomBorderIndex;

    //Outside coins
    private final Pair<Integer,Integer> orightBottomIndex;
    private final Pair<Integer,Integer> obottomLeftIndex;
    private final Pair<Integer,Integer> orightTopOIndex;
    private final Pair<Integer,Integer> otopLeftOIndex;

    //Inside coins
    private final Pair<Integer,Integer> iLeftTopIndex;
    private final Pair<Integer,Integer> iTopRightIndex;
    private final Pair<Integer,Integer> iLeftBottomIndex;
    private final Pair<Integer,Integer> iBottomRightIndex;


    TileType(Pair<Integer,Integer> textureDefaultIndex[], Pair<Integer,Integer> topBorderIndex, Pair<Integer,Integer> rightBorderIndex, Pair<Integer,Integer> leftBorderIndex, Pair<Integer,Integer> bottomBorderIndex,
             Pair<Integer,Integer> orightBottomIndex, Pair<Integer,Integer> obottomLeftIndex, Pair<Integer,Integer> orightTopOIndex, Pair<Integer,Integer> otopLeftOIndex,
             Pair<Integer,Integer> iLeftTopIndex, Pair<Integer,Integer> iTopRightIndex, Pair<Integer,Integer> iLeftBottomIndex, Pair<Integer,Integer> iBottomRightIndex) {
        this.textureDefaultIndex = textureDefaultIndex;//When no border
        this.topBorderIndex = topBorderIndex;
        this.rightBorderIndex = rightBorderIndex;
        this.leftBorderIndex = leftBorderIndex;
        this.bottomBorderIndex = bottomBorderIndex;
        //Out coins
        this.orightBottomIndex = orightBottomIndex;
        this.obottomLeftIndex = obottomLeftIndex;
        this.orightTopOIndex = orightTopOIndex;
        this.otopLeftOIndex = otopLeftOIndex;
        //In coins
        this.iLeftTopIndex = iLeftTopIndex;
        this.iTopRightIndex = iTopRightIndex;
        this.iLeftBottomIndex = iLeftBottomIndex;
        this.iBottomRightIndex = iBottomRightIndex;
    }

    /** Retourne la pair d'index de texture lié à la direction.
     * Pour la texture no border, retourne le premier élément du tableau*/
    /*public Pair<Integer, Integer> getTextureFor(Border direction){
        return switch (direction) {
            case TOP -> topBorderIndex;
            case RIGHT -> rightBorderIndex;
            case LEFT -> leftBorderIndex;
            default -> textureDefaultIndex[0];
        };

    }*/

    /**Utilisé pour put dans la hashmap lors de la creation de la tile si de type default*/
    public Pair<Integer, Integer> getTextureDefaultRandomIndex() {
        int r = (int) (Math.random() * textureDefaultIndex.length);
        //System.out.println("r for default is" + r);
        return textureDefaultIndex[r];
    }
    public Pair<Integer, Integer>[] getTextureDefaultIndex() {
        return textureDefaultIndex;
    }

    public Pair<Integer, Integer> getTopBorderIndex() {
        return topBorderIndex;
    }

    public Pair<Integer, Integer> getRightBorderIndex() {
        return rightBorderIndex;
    }

    public Pair<Integer, Integer> getLeftBorderIndex() {
        return leftBorderIndex;
    }

    public Pair<Integer, Integer> getBottomBorderIndex() {
        return bottomBorderIndex;
    }


    public Pair<Integer, Integer> getORightBottomIndex() {
        return orightBottomIndex;
    }

    public Pair<Integer, Integer> getOBottomLeftIndex() {
        return obottomLeftIndex;
    }

    public Pair<Integer, Integer> getORightTopIndex() {
        return orightTopOIndex;
    }

    public Pair<Integer, Integer> getOTopLeftIndex() {
        return otopLeftOIndex;
    }

    public Pair<Integer, Integer> getiLeftTopIndex() {
        return iLeftTopIndex;
    }

    public Pair<Integer, Integer> getiTopRightIndex() {
        return iTopRightIndex;
    }

    public Pair<Integer, Integer> getiLeftBottomIndex() {
        return iLeftBottomIndex;
    }

    public Pair<Integer, Integer> getiBottomRightIndex() {
        return iBottomRightIndex;
    }
}
