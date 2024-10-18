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
        this.tiles.put(Border.BOTTOM, tileType.bottomIndex());
        this.tiles.put(Border.LEFT, tileType.leftIndex());
        this.tiles.put(Border.RIGHT, tileType.rightIndex());
        this.tiles.put(Border.TOP, tileType.topIndex());
        //Coins out
        this.tiles.put(Border.OUTLEFTTOP, tileType.outLeftTopIndex());
        this.tiles.put(Border.OUTRIGHTTOP, tileType.outRightTopIndex());
        this.tiles.put(Border.OUTLEFTBOTTOM, tileType.outleftBottomIndex());
        this.tiles.put(Border.OUTRIGHTBOTTOM, tileType.outRightBottomIndex());

        //Coins out
        this.tiles.put(Border.INRIGHTBOTTOM, tileType.inRightBottomIndex());
        this.tiles.put(Border.INLEFTBOTTOM, tileType.inLeftBottomIndex());
        this.tiles.put(Border.INRIGHTTOP, tileType.inRightTopIndex());
        this.tiles.put(Border.INLEFTTOP, tileType.inLeftTopIndex());

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

    public Border getCurrentTileBorder() {
        return currentTileBorder;
    }

    public TileType getTileType() { return tileType;}

    public Tile getUnderTile() {
        return underTile;
    }

    /**@return true si la tile est une tile d'eau (les bords etant ceux de l'eau et non de celui du layer au-dessus */
    public boolean isWaterTile(){
        return (tileType.equals(TileType.DARKWATER) || tileType.equals(TileType.WATER) ||tileType.equals(TileType.CLEARWATER));
    }

    /**
     * @return true si la tuile est compatible avec la tuile passée en paramètre (même layer et même type de tuile)
     */
    public boolean isCompatibleWith(Tile other){
        if(other.getTileType() != this.getTileType() ||other.getLayer() != this.getLayer()){
            return false;
        }
        return true;
    }

    public String toString(){
        return "Tile : " + tileType + " Layer : " + layer + " Border : " + currentTileBorder;
    }

}
