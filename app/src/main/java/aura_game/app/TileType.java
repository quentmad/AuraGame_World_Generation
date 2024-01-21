package aura_game.app;

import org.apache.commons.lang3.tuple.Pair;

public enum TileType {

    DARKGRASS(new Pair[]{Pair.of(1,1),Pair.of(3,2)},    Pair.of(2,1),Pair.of(1,0),Pair.of(1,2),Pair.of(0,1)),
    GRASS(new Pair[]{Pair.of(1,4),Pair.of(3,5)},    Pair.of(2,4),Pair.of(1,3),Pair.of(1,5),Pair.of(0,4)),
    CLEARGRASS(new Pair[]{Pair.of(1,7),Pair.of(3,8)},   Pair.of(2,7),Pair.of(1,6),Pair.of(1,8),Pair.of(0,7)),
    SNOW(new Pair[]{Pair.of(1,10),Pair.of(3,11)},   Pair.of(2,10),Pair.of(1,9),Pair.of(1,11),Pair.of(0,10)),
    DIRT(new Pair[]{Pair.of(1,13),Pair.of(3,14)},   Pair.of(2,13),Pair.of(1,12),Pair.of(1,14),Pair.of(0,13)),
    ROCK(new Pair[]{Pair.of(1,16),Pair.of(3,17)},   Pair.of(2,16),Pair.of(1,15),Pair.of(1,17),Pair.of(0,16)),
    DARKSAND(new Pair[]{Pair.of(1,19),Pair.of(3,20)},   Pair.of(2,19),Pair.of(1,18),Pair.of(1,20),Pair.of(0,19)),
    SAND(new Pair[]{Pair.of(1,22),Pair.of(3,23)},   Pair.of(2,22),Pair.of(1,21),Pair.of(1,23),Pair.of(0,22)),
    WATER(new Pair[]{Pair.of(1,25),Pair.of(1,26), Pair.of(2,25),Pair.of(2,26) },    Pair.of(0,26),Pair.of(1,27),Pair.of(1,24),Pair.of(3,25)),
    CLEARWATER(new Pair[]{Pair.of(3,30),Pair.of(4,30)},    Pair.of(3,29),Pair.of(1,28),Pair.of(1,30),Pair.of(0,29)),
    DARKWATER(new Pair[]{Pair.of(1,35),Pair.of(3,36) },     Pair.of(0,35),Pair.of(1,36),Pair.of(1,34),Pair.of(3,35)),
    ;





    /**Texture de base, tableau pour avoir plusieurs variantes différentes...*/
    private final Pair<Integer,Integer>[] textureDefaultIndex;
    //Index (de indexborders) des textures
    private final Pair<Integer,Integer> topBorderIndex;
    private final Pair<Integer,Integer> rightBorderIndex;
    private final Pair<Integer,Integer> leftBorderIndex;
    private final Pair<Integer,Integer> bottomBorderIndex;
    //private final int layer;

    TileType(Pair<Integer,Integer> textureDefaultIndex[], Pair<Integer,Integer> topBorderIndex, Pair<Integer,Integer> rightBorderIndex, Pair<Integer,Integer> leftBorderIndex, Pair<Integer,Integer> bottomBorderIndex) {
        this.textureDefaultIndex = textureDefaultIndex;
        this.topBorderIndex = topBorderIndex;
        this.rightBorderIndex = rightBorderIndex;
        this.leftBorderIndex = leftBorderIndex;
        this.bottomBorderIndex = bottomBorderIndex;
    }

    /** Retourne la pair d'index de texture lié à la direction.
     * Pour la texture no border, retourne le premier élément du tableau*/
    public Pair<Integer, Integer> getTextureFor(BorderType direction){
        return switch (direction) {
            case TOPBORDER -> topBorderIndex;
            case RIGHTBORDER -> rightBorderIndex;
            case LEFTBORDER -> leftBorderIndex;
            default -> textureDefaultIndex[0];
        };

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

}
