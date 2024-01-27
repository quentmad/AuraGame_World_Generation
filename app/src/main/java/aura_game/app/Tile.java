package aura_game.app;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class Tile {

    // Indices des textures pour chaque type de bordures (bord ou non)
    private Map<Border, Pair<Integer, Integer>> tiles;
    private Border currentTileBorder;
    private TileType tileType;
    /**Tuile en dessous la bordure, si c'est une bordure, sinon null*/
    private Tile underTile;
    private int layer;

    /**Tile avec bordure, et tuile sous la tuile bordure */
    public Tile(TileType tileType, Border currentTileBorder, int layer, Tile underTile) {
        this.tileType = tileType;
        initializeHashmap();
        this.currentTileBorder = currentTileBorder;
        this.layer = layer;
        this.underTile = underTile;
    }

    /**Tile sans UnderTile*/
    public Tile(TileType tileType, Border currentTileBorder, int layer) {
        this.tileType = tileType;
        initializeHashmap();
        this.currentTileBorder = currentTileBorder;
        this.layer = layer;
        this.underTile = null;
    }

    /**Tile sans UnderTile et avec NoBorder*/
    public Tile(TileType tileType, int layer) {
        this.tileType = tileType;
        initializeHashmap();
        this.currentTileBorder = Border.NOBORDER;
        this.layer = layer;
        this.underTile = null;
    }

    private void initializeHashmap(){
        this.tiles = new HashMap<>();
        this.tiles.put(Border.NOBORDER, tileType.getTextureDefaultRandomIndex());
        this.tiles.put(Border.TOP, tileType.getTopBorderIndex());
        this.tiles.put(Border.RIGHT, tileType.getRightBorderIndex());
        this.tiles.put(Border.LEFT, tileType.getLeftBorderIndex());
        this.tiles.put(Border.BOTTOM, tileType.getBottomBorderIndex());
        //Coins out
        this.tiles.put(Border.ORIGHT_BOTTOM, tileType.getORightBottomIndex());
        this.tiles.put(Border.OBOTTOM_LEFT, tileType.getOBottomLeftIndex());
        this.tiles.put(Border.ORIGHT_TOP, tileType.getORightTopIndex());
        this.tiles.put(Border.OTOP_LEFT, tileType.getOTopLeftIndex());

        //Coins out
        this.tiles.put(Border.ILEFT_TOP, tileType.getiLeftTopIndex());
        this.tiles.put(Border.ITOP_RIGHT, tileType.getiTopRightIndex());
        this.tiles.put(Border.ILEFT_BOTTOM, tileType.getiLeftBottomIndex());
        this.tiles.put(Border.IBOTTOM_RIGHT, tileType.getiBottomRightIndex());

    }
    public Pair<Integer, Integer> getTextureIndex(Border direction) {
        return tiles.getOrDefault(direction, null);  // -1 si pas de bord dans cette direction
    }

    public Pair<Integer, Integer> getTextureIndexActual(){
        return tiles.getOrDefault(currentTileBorder, null);  // -1 si pas de bord dans cette direction

    }

    public int getLayer() {
        return layer;
    }

    /*public void setLayer(int layer) {
        this.layer = layer;
    }*/

    public Border getCurrentTileBorder() {
        return currentTileBorder;
    }

    /*public void setCurrentBorderTile(BorderType border) {
        currentTileBorder = border;
    }*/

    public TileType getTileType() { return tileType;}

    public Tile getUnderTile() {
        return underTile;
    }

    /**@return true si la tile est une tile d'eau (les bords etant ceux de l'eau et non de celui du layer au-dessus */
    public boolean isWaterTile(){
        return (tileType.equals(TileType.DARKWATER) || tileType.equals(TileType.WATER) ||tileType.equals(TileType.CLEARWATER));
    }
}
