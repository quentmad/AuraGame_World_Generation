package aura_game.app.GenerationMapS2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static aura_game.app.GenerationMapS2.GenerateurCarte.*;

public class WorldDrawer {

    private SpriteBatch batch;
    private Texture tilesheet;
    private TextureRegion[][] terrainTiles;
    private GenerateurCarte generateurCarte;

    public WorldDrawer(GenerateurCarte generateurCarte) {
        this.generateurCarte = generateurCarte;
        // Initialisation de libGDX
        batch = new SpriteBatch();

        // Chargement de la feuille de tuiles
        tilesheet = new Texture("PunyWorld/punyworld-overworld-tileset-perlin.png");

        // Extraction des régions pour chaque tuile de chaque type de terrain
        terrainTiles = new TextureRegion[ALL_TERRAIN_TYPES.length][];
        for (int terrainType : ALL_TERRAIN_TYPES) {
            int numTiles = generateurCarte.getTERRAIN_TILES()[terrainType].length;
            terrainTiles[terrainType] = new TextureRegion[numTiles];
            for (int i = 0; i < numTiles; i++) {
                Tuile tile = generateurCarte.getTERRAIN_TILES()[terrainType][i];
                terrainTiles[terrainType][i] = new TextureRegion(tilesheet, tile.x, tile.y, TILESIZE, TILESIZE);
            }
        }
    }

    public void draw(int[][] terrainTypeMap, boolean waitForKey) {
        batch.begin();
        drawTiles(terrainTypeMap);
        batch.end();

        if (waitForKey) {
            waitKey();
        }
    }

    private void waitKey() {
        while (true) {
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                Gdx.app.exit();
                break;
            }
        }
    }

    private void drawTiles(int[][] terrainTypeMap) {
        for (int y = 0; y < terrainTypeMap.length; y++) {
            for (int x = 0; x < terrainTypeMap[y].length; x++) {
                if (x == WORLD_X || y == WORLD_Y) {
                    continue;
                }

                Tuile[] tileCornerTypes = new Tuile[4];
                tileCornerTypes[0] = generateurCarte.getTERRAIN_TILES()[terrainTypeMap[y + 1][x + 1]][0];
                tileCornerTypes[1] = generateurCarte.getTERRAIN_TILES()[terrainTypeMap[y + 1][x]][0];
                tileCornerTypes[2] = generateurCarte.getTERRAIN_TILES()[terrainTypeMap[y][x + 1]][0];
                tileCornerTypes[3] = generateurCarte.getTERRAIN_TILES()[terrainTypeMap[y][x]][0];

                int terrainType = getTerrainTypeForCorners(tileCornerTypes);
                int tileIndex = getTileIndexForType(tileCornerTypes, terrainType);

                batch.draw(terrainTiles[terrainType][tileIndex], x * TILESIZE, y * TILESIZE);
            }
        }
    }

    private int getTileIndexForType(Tuile[] tileCorners, int terrainType) {
        int tileIndex = 0;
        for (int power = 0; power < tileCorners.length; power++) {
            if (isTileEqual(tileCorners[power], terrainType)) {
                tileIndex += Math.pow(2, power);
            }
        }
        return tileIndex;
    }

    private int getTerrainTypeForCorners(Tuile[] tileCorners) {
        for (int terrainType : ALL_TERRAIN_TYPES) {
            for (int tileIndex = 0; tileIndex < terrainTiles[terrainType].length; tileIndex++) {
                Tuile tileCornerType = generateurCarte.getTERRAIN_TILES()[terrainType][tileIndex];
                if (isTilesEqual(tileCornerType, tileCorners)) {
                    return terrainType;
                }
            }
        }
        return -1;
    }

    private boolean isTilesEqual(Tuile tile, Tuile[] tileCorners) {
        for (Tuile t : tileCorners) {
            if (tile.equals(t)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTileEqual(Tuile tile, int terrainType) {
        // Supposons que la comparaison est basée sur le type de terrain de la tuile
        return tile.getTerrainType() == terrainType;
    }
}
