package aura_game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.time.LocalTime;

public class SaveManager {

    TilesManager tilesManager;
    private FrameBuffer frameBuffer;

    public SaveManager(TilesManager tm) {
        this.tilesManager = tm;
        // Créez un FrameBuffer avec la taille de la fenêtre d'affichage (à utiliser lors de la capture de l'écran)
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    public void saveImage(int width, int height, Tile[][] tileset, int tileSize) {
        // Capturer l'écran dans un Pixmap à partir du FrameBuffer
        //Pixmap pixmap = captureScreen(width, height);
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        // Parcourez le tableau de tuiles et dessinez chaque tuile sur le Pixmap
        for (int x = 0; x < tileset.length; x++) {
            for (int y = 0; y < tileset[0].length; y++) {
                Tile currentTile = tileset[x][y];
                // Dessinez la tuile sur le Pixmap en fonction de sa position dans la map
                drawTileOnPixmap(currentTile, pixmap, x * tileSize, y * tileSize);
            }
        }

        // Enregistrer le Pixmap en tant qu'image PNG
        PixmapIO.writePNG(Gdx.files.local("out/map_v" + getCurrentTime() + " - + -testCorrectionBorder.png"), pixmap);

        // Libérer la mémoire du Pixmap
        pixmap.dispose();
    }

    private void drawTileOnPixmap(Tile tile, Pixmap targetPixmap, int targetX, int targetY) {
        // Récupérer la texture de la tuile à partir du gestionnaire de tuiles
        Texture textureCurrentTile = tilesManager.getTilesTexture().spriteSheetRegions()[tile.getTextureIndexActual().getLeft()][tile.getTextureIndexActual().getRight()].getTexture();

        // Récupérer le Pixmap de la texture de la tuile
        Pixmap pixmapTile = ScreenUtils.getFrameBufferPixmap(0, 0, textureCurrentTile.getWidth(), textureCurrentTile.getHeight());

        // Dessiner le Pixmap de la tuile sur le Pixmap cible à la position spécifiée
        targetPixmap.drawPixmap(pixmapTile, targetX, targetY);

        // Libérer la mémoire du Pixmap de la tuile
        pixmapTile.dispose();
    }

    private String getCurrentTime() {
        // Obtenir l'heure actuelle
        LocalTime now = LocalTime.now();

        // Extraire l'heure, les minutes et les secondes
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        // Formater les composants en une chaîne de temps
        return String.format("%02dh%02d.%02d", hour, minute, second);
    }

    private Pixmap captureScreen(int exportWidth, int exportHeight) {
        // Redimensionner le FrameBuffer si nécessaire
        if (frameBuffer.getWidth() != exportWidth || frameBuffer.getHeight() != exportHeight) {
            frameBuffer.dispose();
            frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, exportWidth, exportHeight, false);
        }

        // Activer le FrameBuffer pour le rendu
        frameBuffer.begin();

        // Effacer le FrameBuffer
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Capturez le rendu ici en fonction de votre implémentation

        // Désactiver le FrameBuffer
        frameBuffer.end();

        // Obtenez le Pixmap du FrameBuffer
        return ScreenUtils.getFrameBufferPixmap(0, 0, exportWidth, exportHeight);
    }
}
