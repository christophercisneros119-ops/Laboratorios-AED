package com.mycompany.lab4;

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

public class LAB4 {

    private static final int TECLA_SALIR = KeyEvent.VK_ESCAPE;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LAB4::iniciar);
    }

    private static void iniciar() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception excepcion) {
            // Si no esta el del sistema se usa el de Java.
        }
        instalarEscalaDpi();
        aplicarPantallaCompleta();
    }

    // Escala 1 para que las imagenes no salgan borrosas con el escalado de Windows.
    private static void instalarEscalaDpi() {
        System.setProperty("sun.java2d.uiScale", "1");
    }

    // Pantalla completa con Escape para salir; la ventana chica tambien sirve.
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
        JFrame ventana = new JFrame("Laboratorio #4 · Pilas, colas y notaciones polacas");
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
