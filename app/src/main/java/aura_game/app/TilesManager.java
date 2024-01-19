package aura_game.app;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TilesManager {
    private SpriteSheetInfo tilesTexture;

    public TilesManager(int TILE_SIZE){
        this.tilesTexture = new SpriteSheetInfo(TILE_SIZE, "tilesTexture");
    }

    /**
     * Dessine une tuile à l'écran.
     *
     * @param batch Le lot de sprites sur lequel dessiner la tuile.
     * @param tile La tuile a dessiner.
     * @param x La coordonnée x de la position où dessiner la tuile.
     * @param y La coordonnée y de la position où dessiner la tuile.
     */
    public void drawTile(SpriteBatch batch, Tile tile, int x, int y){
        if(!tile.getBorderTypeTile().equals(BorderType.NOBORDER)) {//Si c'est une bordure on affiche la tile dessous puis la bordure
            batch.draw(tilesTexture.spriteSheetRegions()[0][tile.getTextureIndex(BorderType.NOBORDER)], x, y);
            batch.draw(tilesTexture.spriteSheetRegions()[1][tile.getTextureIndex(tile.getBorderTypeTile())], x, y);

        }else{//Sinon on affiche la tile
            batch.draw(tilesTexture.spriteSheetRegions()[0][tile.getTextureIndex(tile.getBorderTypeTile())], x, y);
        }

    }


}
