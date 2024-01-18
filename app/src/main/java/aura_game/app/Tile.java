package aura_game.app;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class Tile {

    private Map<Direction, Integer> tiles;  // Indices des textures pour chaque cas (bord ou non)
    //private Map<Biome, Integer> biomeTextures;
    private Direction directionTile;
    private int layer;

    public Tile(TileType tileType, Direction directionTile/*int layer*/) {
        this.tiles = new HashMap<>();
        if(tileType.getTopBorderIndex() == 1){//TODO: c'est un TEST POUR AVOIR 2 HERBES
            if(Math.random() < 0.5){    this.tiles.put(Direction.NOBORDER, 2);
            }else{  this.tiles.put(Direction.NOBORDER, 1);}
        }else {
            this.tiles.put(Direction.NOBORDER, tileType.getTextureIndex());
        }
        this.tiles.put(Direction.TOP, tileType.getTopBorderIndex());
        this.tiles.put(Direction.RIGHT, tileType.getRightBorderIndex());
        this.tiles.put(Direction.LEFT, tileType.getLeftBorderIndex());
        this.tiles.put(Direction.BOTTOM, tileType.getBottomBorderIndex());
        this.directionTile = directionTile;
        this.layer = tileType.getLayer();
    }

    public int getTextureIndex(Direction direction) {
        return tiles.getOrDefault(direction, -1);  // -1 si pas de bord dans cette direction
    }

    public int getTextureIndexActual(){
        return getTextureIndex(directionTile);
    }

    public void setTextureIndex(Direction direction, int textureIndex) {
        tiles.put(direction, textureIndex);
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public Direction getDirectionTile() {
        return directionTile;
    }

    public void setDirectionTile(Direction direction) {
        directionTile = direction;
    }




}
