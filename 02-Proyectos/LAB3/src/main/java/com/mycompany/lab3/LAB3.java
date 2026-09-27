package com.mycompany.lab3;

import UI.Elements.UIConstants;
import UI.Panels.UNI_Portada;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class LAB3 {

    /** Tecla para salir de pantalla completa. */
    private static final int TECLA_SALIR = java.awt.event.KeyEvent.VK_ESCAPE;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LAB3::iniciar);
    }

    private static void iniciar() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception excepcion) {
            // Si el look and feel del sistema no esta disponible se usa el de Java.
        }
        instalarEscalaDpi();
        aplicarPantallaCompleta();
    }

    /**
     * Con el escalado de Windows activado, Swing dibuja la interfaz con la fuente
     * del sistema y las imagenes salen borrosas. Fijar la escala a 1 mantiene
     * todo en pixeles reales.
     */
    private static void instalarEscalaDpi() {
        System.setProperty("sun.java2d.uiScale", "1");
    }

    /**
     * El profesor pide abrir en pantalla completa, pero la ventana chica tambien
     * tiene que funcionar: queda el boton de la cabecera y la tecla Escape para
     * salir.
     */
    private static void aplicarPantallaCompleta() {
        GraphicsEnvironment entorno = GraphicsEnvironment.getLocalGraphicsEnvironment();
        if (!entorno.isHeadless() && entorno.getScreenDevices().length > 0) {
            GraphicsDevice dispositivo = entorno.getDefaultScreenDevice();
            if (dispositivo.isFullScreenSupported()) {
                dispositivo.setFullScreenWindow(crearVentana());
                return;
            }
        }
        crearVentana().setVisible(true);
    }

    private static JFrame crearVentana() {
        JFrame ventana = new JFrame("Laboratorio #3 · Algoritmos Recursivos");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setContentPane(new UNI_Portada());
        ventana.setMinimumSize(new Dimension(UIConstants.ANCHO_MINIMO, UIConstants.ALTO_MINIMO));
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        salirDePantallaCompletaConEscape(ventana);
        return ventana;
    }

    private static void salirDePantallaCompletaConEscape(JFrame ventana) {
        ventana.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evento) {
                if (evento.getKeyCode() == TECLA_SALIR) {
                    GraphicsEnvironment.getLocalGraphicsEnvironment()
                            .getDefaultScreenDevice().setFullScreenWindow(null);
                }
            }
        });
    }
}
