package aura_game.app;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.apache.commons.lang3.tuple.Pair;

public class TilesManager {
    private SpriteSheetInfo tilesTexture;

    public TilesManager(int TILE_SIZE){
        this.tilesTexture = new SpriteSheetInfo(TILE_SIZE, "OFFICIAL_TILESET_test1-32_32_Lis");// - NUM");
    }

    public SpriteSheetInfo getTilesTexture() {
        return tilesTexture;
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
        if(!tile.getCurrentTileBorder().equals(Border.NOBORDER)) {//Si c'est une bordure on affiche la tile dessous puis la bordure
            Pair<Integer,Integer> texture = tile.getUnderTile().getTextureIndexActual();//Texture de la tuile sous la bordure
            batch.draw(tilesTexture.spriteSheetRegions()[texture.getLeft()][texture.getRight()], x, y);
            batch.draw(tilesTexture.spriteSheetRegions()[tile.getTextureIndexActual().getLeft()][tile.getTextureIndexActual().getRight()], x, y);

        }else{//Sinon on affiche la tile
            batch.draw(tilesTexture.spriteSheetRegions()[tile.getTextureIndexActual().getLeft()][tile.getTextureIndexActual().getRight()], x, y);
        }




    }

    //Donne la tuile en fonction de la hauteur et du biome
    public Tile getTile(Biome biome, int height){
        return new Tile(biome.getTileType1(), height);
    }


}
