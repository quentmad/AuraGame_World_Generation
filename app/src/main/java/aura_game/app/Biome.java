package aura_game.app;

import com.badlogic.gdx.graphics.Color;

public enum Biome {

    OCEAN(TileType.WATER, TileType.DARKWATER, Color.BLUE, true),
    HOTOCEAN(TileType.CLEARWATER, TileType.WATER, Color.SKY, true),
    DESERT(TileType.SAND, TileType.DARKSAND, Color.YELLOW, false),
    PLAINS(TileType.GRASS, TileType.CLEARGRASS, Color.GREEN, false),
    MOUNTAIN(TileType.ROCK, TileType.DIRT, Color.BROWN, false),
    SNOWY(TileType.SNOW, TileType.GRASS, Color.WHITE,false),
    TOUNDRA(TileType.SNOW, TileType.DARKGRASS, Color.LIGHT_GRAY,false),
    JUNGLE(TileType.DARKGRASS, TileType.GRASS, Color.FOREST,false),
    GRASSMOUNTAIN(TileType.GRASS,TileType.DIRT , Color.OLIVE ,false );

    private final TileType tileType1;
    private final TileType tileType2;
    private Color colorMiniMap;
    private final boolean isWater;


    Biome(TileType tileType1, TileType tileType2, Color colorMiniMap, boolean isWater) {
        this.tileType1 = tileType1;
        this.tileType2 = tileType2;
        this.colorMiniMap = colorMiniMap;
        this.isWater = isWater;

    }

    public Color colorMiniMap() {
        return colorMiniMap;
    }

    public TileType getTileType1() {
        return tileType1;
    }

    public TileType getTileType2() {
        return tileType2;
    }

    public boolean isWater() {
        return isWater;
    }

}
