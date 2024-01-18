package aura_game.app.GenerationMapS2;

public class Tuile {
    public int x;
    public int y;
    public int terrainType;

    public Tuile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getTerrainType() {
        return terrainType;
    }
}