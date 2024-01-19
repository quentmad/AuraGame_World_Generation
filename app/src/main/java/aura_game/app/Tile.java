package aura_game.app;

import java.util.HashMap;
import java.util.Map;

public class Tile {

    private Map<BorderType, Integer> tiles;  // Indices des textures pour chaque cas (bord ou non)
    //private Map<Biome, Integer> biomeTextures;
    private BorderType borderType;
    private TileType tileType;
    private int layer;

    /** Il est possible de mettre de l'herbe au layer 1 comme 2 comme... ainsi on demande lequel il veut*/
    public Tile(TileType tileType, BorderType borderType, int layer) {
        this.tileType = tileType;
        this.tiles = new HashMap<>();
        if(tileType.getTopBorderIndex() == 1){//TODO: c'est un TEST POUR AVOIR 2 HERBES
            if(Math.random() < 0.5){    this.tiles.put(BorderType.NOBORDER, 2);
            }else{  this.tiles.put(BorderType.NOBORDER, 1);}
        }else {
            this.tiles.put(BorderType.NOBORDER, tileType.getTextureIndex());
        }
        this.tiles.put(BorderType.TOPBORDER, tileType.getTopBorderIndex());
        this.tiles.put(BorderType.RIGHTBORDER, tileType.getRightBorderIndex());
        this.tiles.put(BorderType.LEFTBORDER, tileType.getLeftBorderIndex());
        this.tiles.put(BorderType.BOTTOMBORDER, tileType.getBottomBorderIndex());
        this.borderType = borderType;
        this.layer = layer;
    }

    public int getTextureIndex(BorderType direction) {
        return tiles.getOrDefault(direction, -1);  // -1 si pas de bord dans cette direction
    }

    public int getTextureIndexActual(){
        return getTextureIndex(borderType);
    }

    public void setTextureIndex(BorderType direction, int textureIndex) {
        tiles.put(direction, textureIndex);
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public BorderType getBorderTypeTile() {
        return borderType;
    }

    public void setBorderTypeTile(BorderType border) {
        borderType = border;
    }

    public TileType getTileType() { return tileType;}
}
