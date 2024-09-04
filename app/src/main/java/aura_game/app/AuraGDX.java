package aura_game.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class AuraGDX extends ApplicationAdapter {


    private MapManager mapManager;
    private Biome choosenBiome = choseRandomBiome();
    private final int DEFAULT_NB_TILES_WIDTH = 1962 / 16;
    private final int DEFAULT_NB_TILES_HEIGHT = 1008 / 16;

    private final int TILE_SIZE = 32;

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
        int randomNumber = (int) (Math.random() * 13);
        // Afficher le nombre généré
        System.out.println("Nombre aléatoire entre 0 et 13 : " + randomNumber);
        Biome choice = switch (randomNumber) {
            case 0 -> Biome.ISLAND;
            case 1 -> Biome.LAKES;
            case 2 -> Biome.SWAMP;
            case 3 -> Biome.MOUNTAINS;
            case 4 -> Biome.PLAINS;
            case 5 -> Biome.DESERT;
            case 6 -> Biome.FOREST;
            case 7 -> Biome.SNOWY;
            case 8 -> Biome.HIGHLANDS;
            case 9 -> Biome.TAIGA;
            case 10 -> Biome.TUNDRA;
            case 11 -> Biome.JUNGLE;
            case 12 -> Biome.BADLANDS;
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
