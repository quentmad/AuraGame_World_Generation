package aura_game.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class AuraGDX extends ApplicationAdapter {


    private MapManager mapManager;
    private final int DEFAULT_NB_TILES_WIDTH = 500;//1962 / 16;
    private final int DEFAULT_NB_TILES_HEIGHT = 200;//1008 / 16;

    private final int TILE_SIZE = 32;

    @Override
    public void create() {
        mapManager = new MapManager(DEFAULT_NB_TILES_WIDTH, DEFAULT_NB_TILES_HEIGHT, TILE_SIZE);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //mapManager.renderBiomeMap();
        mapManager.drawMap();
    }

    @Override
    public void dispose() {
        //perlinNoise.dispose();
    }
}
