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



    WATER(new Pair[]{Pair.of(1,25),Pair.of(1,26)/*, Pair.of(2,25),Pair.of(2,26)*/ },    Pair.of(3,25),Pair.of(1,24),Pair.of(1,27),Pair.of(0,25),
            Pair.of(0,24), Pair.of(0,27),Pair.of(3,24), Pair.of(3,27 ),//COINS Out
    Pair.of(4,24), Pair.of(4,25),Pair.of(5,24), Pair.of(5,25)),//COINS In




    CLEARWATER(new Pair[]{Pair.of(3,30),Pair.of(4,30)},Pair.of(0,29),Pair.of(1,30),Pair.of(1,28),Pair.of(2,29),
            Pair.of(3,29), Pair.of(3,28),Pair.of(4,29), Pair.of(4,28 ),//COINS Out
            Pair.of(0,28), Pair.of(0,30),Pair.of(2,28), Pair.of(2,30)),//COINS In


    DARKWATER(new Pair[]{Pair.of(1,35),Pair.of(3,36) },     Pair.of(0,35),Pair.of(1,36),Pair.of(1,34),Pair.of(3,35),
            Pair.of(0,34), Pair.of(0,36),Pair.of(2,34), Pair.of(2,36 ),//COINS Out
            Pair.of(3,34), Pair.of(4,35),Pair.of(3,34), Pair.of(4,35));//COINS In



    /**Texture de base, tableau pour avoir plusieurs variantes différentes...*/
    private final Pair<Integer,Integer>[] textureDefaultIndex;
    //Index (de indexborders) des textures
    private final Pair<Integer,Integer> bottomIndex;
    private final Pair<Integer,Integer> leftIndex;
    private final Pair<Integer,Integer> rightIndex;
    private final Pair<Integer,Integer> topIndex;

    //Outside coins
    private final Pair<Integer,Integer> outLeftTopIndex;
    private final Pair<Integer,Integer> outRightTopIndex;
    private final Pair<Integer,Integer> outleftBottomIndex;
    private final Pair<Integer,Integer> outRightBottomIndex;

    //Inside coins
    private final Pair<Integer,Integer> inRightBottomIndex;
    private final Pair<Integer,Integer> inLeftBottomIndex;
    private final Pair<Integer,Integer> inRightTopIndex;
    private final Pair<Integer,Integer> inLeftTopIndex;


    TileType(Pair<Integer,Integer> textureDefaultIndex[], Pair<Integer,Integer> bottomIndex, Pair<Integer,Integer> leftIndex, Pair<Integer,Integer> rightIndex, Pair<Integer,Integer> topIndex,
             Pair<Integer,Integer> outLeftTopIndex, Pair<Integer,Integer> outRightTopIndex, Pair<Integer,Integer> outleftBottomIndex, Pair<Integer,Integer> outRightBottomIndex,
             Pair<Integer,Integer> inRightBottomIndex, Pair<Integer,Integer> inLeftBottomIndex, Pair<Integer,Integer> inRightTopIndex, Pair<Integer,Integer> inLeftTopIndex) {

        this.textureDefaultIndex = textureDefaultIndex;//When no border
        this.bottomIndex = bottomIndex;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
        this.topIndex = topIndex;
        //Out coins
        this.outLeftTopIndex = outLeftTopIndex;
        this.outRightTopIndex = outRightTopIndex;
        this.outleftBottomIndex = outleftBottomIndex;
        this.outRightBottomIndex = outRightBottomIndex;
        //In coins
        this.inRightBottomIndex = inRightBottomIndex;
        this.inLeftBottomIndex = inLeftBottomIndex;
        this.inRightTopIndex = inRightTopIndex;
        this.inLeftTopIndex = inLeftTopIndex;
    }

    /**Utilisé pour put dans la hashmap lors de la creation de la tile si de type default*/
    public Pair<Integer, Integer> getTextureDefaultRandomIndex() {
        int r = (int) (Math.random() * textureDefaultIndex.length);
        //System.out.println("r for default is" + r);
        return textureDefaultIndex[r];
    }
    public Pair<Integer, Integer>[] getTextureDefaultIndex() {
        return textureDefaultIndex;
    }


    public Pair<Integer, Integer> bottomIndex() {
        return bottomIndex;
    }

    public Pair<Integer, Integer> leftIndex() {
        return leftIndex;
    }

    public Pair<Integer, Integer> rightIndex() {
        return rightIndex;
    }

    public Pair<Integer, Integer> topIndex() {
        return topIndex;
    }

    public Pair<Integer, Integer> outLeftTopIndex() {
        return outLeftTopIndex;
    }

    public Pair<Integer, Integer> outRightTopIndex() {
        return outRightTopIndex;
    }

    public Pair<Integer, Integer> outleftBottomIndex() {
        return outleftBottomIndex;
    }

    public Pair<Integer, Integer> outRightBottomIndex() {
        return outRightBottomIndex;
    }

    public Pair<Integer, Integer> inRightBottomIndex() {
        return inRightBottomIndex;
    }

    public Pair<Integer, Integer> inLeftBottomIndex() {
        return inLeftBottomIndex;
    }

    public Pair<Integer, Integer> inRightTopIndex() {
        return inRightTopIndex;
    }

    public Pair<Integer, Integer> inLeftTopIndex() {
        return inLeftTopIndex;
    }


}
