package aura_game.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class AuraGDX extends ApplicationAdapter {


    private MapManager mapManager;
    private Biome choosenBiome = choseRandomBiome();
    private final int DEFAULT_NB_TILES_WIDTH = 1962 / 16;
    private final int DEFAULT_NB_TILES_HEIGHT = 1008 / 16;

    private final int TILE_SIZE = 16;

    @Override
    public void create() {
        mapManager = new MapManager(DEFAULT_NB_TILES_WIDTH, DEFAULT_NB_TILES_HEIGHT, TILE_SIZE, choosenBiome);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapManager.render();


    }

    public Biome choseRandomBiome(){
        // Générer un nombre aléatoire entre 0 et 12 (inclus)
        int randomNumber = (int) (Math.random() * 12);
        // Afficher le nombre généré
        System.out.println("Nombre aléatoire entre 0 et 12 : " + randomNumber);
        Biome choice = switch (randomNumber) {
            case 0 -> Biome.ISLAND;
            case 1 -> Biome.LAKES;
            case 2 -> Biome.MOUNTAINS;
            case 3 -> Biome.PLAINS;
            case 4 -> Biome.DESERT;
            case 5 -> Biome.FOREST;
            case 6 -> Biome.SNOWY;
            case 7 -> Biome.HIGHLANDS;
            case 8 -> Biome.TAIGA;
            case 9 -> Biome.TUNDRA;
            case 10 -> Biome.JUNGLE;
            case 11 -> Biome.BADLANDS;
            default -> Biome.PLAINS;
        };
        System.out.println(" ----- Biome : "+ choice.getName() + "----- \n \n");
        return choice;
    }
    @Override
    public void dispose() {
        //perlinNoise.dispose();
    }
}
