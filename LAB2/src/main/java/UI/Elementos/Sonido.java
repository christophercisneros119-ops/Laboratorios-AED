package UI.Elementos;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sonido {

    private static Clip hover;
    private static Clip click;
    private static Clip fondo;

    private static final float HOVER_DB = -12f;
    private static final float FONDO_DB = -20f;

    static {
        hover = cargar("/snd/hover.wav");
        click = cargar("/snd/click.wav");
        fondo = cargar("/snd/bg.wav");
        bajarVolumen(hover, HOVER_DB);
        bajarVolumen(fondo, FONDO_DB);
    }

    private static Clip cargar(String ruta) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(
                    Sonido.class.getResourceAsStream(ruta));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            return clip;
        } catch (Exception e) {
            return null;
        }
    }

    private static void bajarVolumen(Clip clip, float db) {
        if (clip == null) {
            return;
        }
        try {
            FloatControl vol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            vol.setValue(db);
        } catch (IllegalArgumentException ignored) {
        }
    }

    private static void reproducir(Clip clip) {
        if (clip == null) {
            return;
        }
        try {
            clip.setFramePosition(0);
            clip.start();
        } catch (Exception ignored) {
        }
    }

    public static void hover() {
        reproducir(hover);
    }

    public static void click() {
        reproducir(click);
    }

    public static void iniciarMusica() {
        if (fondo == null) {
            return;
        }
        try {
            fondo.setFramePosition(0);
            fondo.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ignored) {
        }
    }
}
