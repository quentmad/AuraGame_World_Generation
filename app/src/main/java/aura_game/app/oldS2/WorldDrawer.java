package aura_game.app.oldS2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;

public class WorldDrawer {

    private final int WINDOW_WIDTH = Config.WINDOW_WIDTH;
    private final int WINDOW_HEIGHT = Config.WINDOW_HEIGHT;
    private final int TILESIZE = Config.TILESIZE;

    private final Array<Array<Sprite>> terrainTiles;
    private Batch batch;

    public WorldDrawer(Batch batch) {
        this.batch = batch;
        terrainTiles = new Array<>();

        Texture tilesheet = new Texture(Config.TILESHEET_PATH);

        for (int terrainType : Config.ALL_TERRAIN_TYPES) {
            Array<Sprite> tileTypes = new Array<>();
            /*for (int[] tilePos : Config.TERRAIN_TILES[terrainType]) {
                TextureRegion region = new TextureRegion(tilesheet, tilePos[0], tilePos[1], TILESIZE, TILESIZE);
                Sprite sprite = new Sprite(region);
                tileTypes.add(sprite);
            }*/
            for (int i = 0; i < Config.TERRAIN_TILES[terrainType].length; i += 2) {
                int tilePosX = Config.TERRAIN_TILES[terrainType][i];
                int tilePosY = Config.TERRAIN_TILES[terrainType][i + 1];
                TextureRegion region = new TextureRegion(tilesheet, tilePosX, tilePosY, TILESIZE, TILESIZE);
                Sprite sprite = new Sprite(region);
                tileTypes.add(sprite);
            }
            terrainTiles.add(tileTypes);
        }
    }

    public void draw(int[][] terrainTypeMap, boolean waitForKey) {
        drawTiles(terrainTypeMap);
        Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
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
            for (int x = 0; x < terrainTypeMap[0].length; x++) {

                if (x == Config.WORLD_X || y == Config.WORLD_Y) {
                    continue;
                }

                int[] tileCornerTypes = {
                        terrainTypeMap[y + 1][x + 1],
                        terrainTypeMap[y + 1][x],
                        terrainTypeMap[y][x + 1],
                        terrainTypeMap[y][x]
                };

                for (int terrainType : Config.ALL_TERRAIN_TYPES) {
                    if (Arrays.asList(tileCornerTypes).contains(terrainType)) {
                        int tileIndex = getTileIndexForType(tileCornerTypes, terrainType);
                        Sprite sprite = terrainTiles.get(terrainType).get(tileIndex);
                        sprite.setPosition(x * TILESIZE, y * TILESIZE);
                        sprite.draw(batch);
                        break;
                    }
                }
            }
        }
    }

    private int getTileIndexForType(int[] tileCorners, int terrainType) {
        int tileIndex = 0;
        for (int power = 0; power < tileCorners.length; power++) {
            if (tileCorners[power] == terrainType) {
                tileIndex += Math.pow(2, power);
            }
        }
        return tileIndex;
    }
}
