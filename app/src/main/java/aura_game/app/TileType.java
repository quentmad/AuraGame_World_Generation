package aura_game.app;

public enum TileType {

    WATER(0, 4,5,6,7, 1),
    GRASS(1,0,1,2,3, 2),
    MOUNTAIN1(3,0,1,2,3,4),
    MOUNTAIN2(4,0,1,2,3,5),
    MOUNTAIN3(5,0,1,2,3,6),
    MOUNTAIN4(6,0,1,2,3,7);


    private final int textureIndex;
    //Index (de indexborders) des textures
    private final int topBorderIndex;
    private final int rightBorderIndex;
    private final int leftBorderIndex;
    private final int bottomBorderIndex;
    private final int layer;

    TileType(int textureIndex, int topBorderIndex, int rightBorderIndex, int leftBorderIndex, int bottomBorderIndex, int layer) {
        this.textureIndex = textureIndex;
        this.topBorderIndex = topBorderIndex;
        this.rightBorderIndex = rightBorderIndex;
        this.leftBorderIndex = leftBorderIndex;
        this.bottomBorderIndex = bottomBorderIndex;
        this.layer = layer;
    }


    public int getTextureIndex() {
        return textureIndex;
    }
    public int getTopBorderIndex() {
        return topBorderIndex;
    }
    public int getRightBorderIndex() {return rightBorderIndex;}
    public int getLeftBorderIndex() {
        return leftBorderIndex;
    }
    public int getBottomBorderIndex() {
        return bottomBorderIndex;
    }
    public int getLayer() {return layer;}
}
