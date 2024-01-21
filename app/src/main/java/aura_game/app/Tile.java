package aura_game.app;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class Tile {

    // Indices des textures pour chaque type de bordures (bord ou non)
    private Map<BorderType, Pair<Integer, Integer>> tiles;
    private BorderType currentTileBorder;
    private TileType tileType;
    /**Tuile en dessous la bordure, si c'est une bordure, sinon null*/
    private Tile underTile;
    private int layer;

    /**Tile avec bordure, et tuile sous la tuile bordure */
    public Tile(TileType tileType, BorderType currentTileBorder, int layer, Tile underTile) {
        this.tileType = tileType;
        this.tiles = new HashMap<>();
        this.tiles.put(BorderType.NOBORDER, tileType.getTextureDefaultRandomIndex());
        this.tiles.put(BorderType.TOPBORDER, tileType.getTopBorderIndex());
        this.tiles.put(BorderType.RIGHTBORDER, tileType.getRightBorderIndex());
        this.tiles.put(BorderType.LEFTBORDER, tileType.getLeftBorderIndex());
        this.tiles.put(BorderType.BOTTOMBORDER, tileType.getBottomBorderIndex());
        this.currentTileBorder = currentTileBorder;
        this.layer = layer;
        this.underTile = underTile;
    }

    /**Tile sans bordure */
    public Tile(TileType tileType, int layer) {
        this.tileType = tileType;
        this.tiles = new HashMap<>();
        this.tiles.put(BorderType.NOBORDER, tileType.getTextureDefaultRandomIndex());
        this.tiles.put(BorderType.TOPBORDER, tileType.getTopBorderIndex());
        this.tiles.put(BorderType.RIGHTBORDER, tileType.getRightBorderIndex());
        this.tiles.put(BorderType.LEFTBORDER, tileType.getLeftBorderIndex());
        this.tiles.put(BorderType.BOTTOMBORDER, tileType.getBottomBorderIndex());
        this.currentTileBorder = BorderType.NOBORDER;
        this.layer = layer;
        this.underTile = null;
    }

    private Pair<Integer, Integer> getTextureIndex(BorderType direction) {
        return tiles.getOrDefault(direction, null);  // -1 si pas de bord dans cette direction
    }

    public Pair<Integer, Integer> getTextureIndexActual(){
        //return getTextureIndex(currentTileBorder);
        return tiles.getOrDefault(currentTileBorder, null);  // -1 si pas de bord dans cette direction

    }

    /*public void setTextureIndex(BorderType direction, int textureIndex) {
        tiles.put(direction, textureIndex);
    }*/

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public BorderType getCurrentTileBorder() {
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
