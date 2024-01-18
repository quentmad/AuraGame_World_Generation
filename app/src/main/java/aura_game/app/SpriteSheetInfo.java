package aura_game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Permet de stocker les élements
 */
public class SpriteSheetInfo{

    private int SPRITE_WIDTH; // Largeur d'un sprite dans le sprite sheet
    private int SPRITE_HEIGHT; // Hauteur d'un sprite dans le sprite sheet
    private Texture texture; // Chargement de la sprite sheet Texture
    private TextureRegion[][] spriteSheetRegions;// Division de l'image en sous-images correspondant à chaque sprite.de la forme Y X


    /**Constructeur où spriteWidth et spriteHeight sont égaux */
    public SpriteSheetInfo(int unitTileSize,String file){
        this.SPRITE_WIDTH = unitTileSize;
        this.SPRITE_HEIGHT = unitTileSize;
        this.texture = new Texture(Gdx.files.internal("src/main/resources/"+file+".png"));
        this.spriteSheetRegions  = TextureRegion.split(texture, SPRITE_WIDTH, SPRITE_HEIGHT);
    }


    /**Largeur d'un sprite dans le sprite sheet */
    public int SPRITE_WIDTH() {
        return SPRITE_WIDTH;
    }

    /**Hauteur d'un sprite dans le sprite sheet */
    public int SPRITE_HEIGHT() {
        return SPRITE_HEIGHT;
    }

    public Texture texture() {
        return texture;
    }

    public TextureRegion[][] spriteSheetRegions() {
        return spriteSheetRegions;
    }




}
