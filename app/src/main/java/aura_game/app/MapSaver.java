package aura_game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MapSaver {

    TilesManager tilesManager;
    private final int nbTilesWidth;
    private final int nbTilesHeight;
    private final int tileSize;


    public MapSaver(TilesManager tilesManager, int nbTilesWidth, int nbTilesHeight, int tileSize) {
        this.tilesManager = tilesManager;
        this.nbTilesWidth = nbTilesWidth;
        this.nbTilesHeight = nbTilesHeight;
        this.tileSize = tileSize;
    }

    public void saveMapAsPNG(Tile[][] tileset, String fileName) {
        // Déterminez la largeur et la hauteur de la map en pixels
        int mapWidth = tileset.length * tileSize;  // Supposons que la largeur d'une tuile est TILE_WIDTH
        int mapHeight = tileset[0].length * tileSize;  // Supposons que la hauteur d'une tuile est TILE_HEIGHT

        // Créez un Pixmap avec la largeur et la hauteur de la map
        Pixmap pixmap = new Pixmap(mapWidth, mapHeight, Pixmap.Format.RGBA8888);

        // Parcourez le tableau de tuiles et dessinez chaque tuile sur le Pixmap
        for (int x = 0; x < tileset.length; x++) {
            for (int y = 0; y < tileset[0].length; y++) {
                Tile currentTile = tileset[x][y];
                // Dessinez la tuile sur le Pixmap en fonction de sa position dans la map
                Texture textureCurrentTile = tilesManager.getTilesTexture().spriteSheetRegions()[currentTile.getTextureIndexActual().getLeft()][currentTile.getTextureIndexActual().getRight()].getTexture();
                Pixmap pixmapTile = ScreenUtils.getFrameBufferPixmap(0, 0, textureCurrentTile.getWidth(), textureCurrentTile.getHeight());
                pixmap.drawPixmap(pixmapTile, x * tileSize, y * tileSize);
            }
        }

        // Enregistrez le Pixmap en tant que fichier PNG
        PixmapIO.writePNG(Gdx.files.local(fileName), pixmap);


        // Libérez les ressources du Pixmap
        pixmap.dispose();
    }
}
