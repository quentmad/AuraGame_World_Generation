package aura_game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

import java.time.LocalTime;

public class SaveManager {




    public void saveImage(Biome biome) {
        // Capturer l'écran dans un Pixmap
        Pixmap pixmap = captureScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Enregistrer le Pixmap en tant qu'image PNG
        PixmapIO.writePNG(Gdx.files.local("out/map_v"+getCurrentTime()+" - "+biome.getName()+".png"), pixmap);

        // Libérer la mémoire du Pixmap
        pixmap.dispose();
    }

    private String getCurrentTime(){
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
        // Créer un Pixmap de la taille de l'écran
        Pixmap pixmap = new Pixmap(exportWidth, exportHeight, Pixmap.Format.RGBA8888);

        // Capturer l'écran et copier les pixels dans le Pixmap
        Gdx.gl.glReadPixels(0, 0, exportWidth, exportHeight, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, pixmap.getPixels());

        // Inverser l'image verticalement, car les coordonnées de la fenêtre LibGDX sont inversées par rapport aux coordonnées de la pixmap
        Pixmap flippedPixmap = new Pixmap(exportWidth, exportHeight, Pixmap.Format.RGBA8888);
        for (int y = 0; y < exportHeight; y++) {
            flippedPixmap.drawPixmap(pixmap, 0, y, 0, exportHeight - y - 1, exportWidth, 1);
        }

        // Disposez du Pixmap original
        pixmap.dispose();

        return flippedPixmap;
    }
}
