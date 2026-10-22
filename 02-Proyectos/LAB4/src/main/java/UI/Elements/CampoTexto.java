package UI.Elements;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 * Campo de texto con el look pixel art del laboratorio: caja oscura de esquinas
 * redondeadas, texto claro en Silkscreen y borde azul del mismo radio que los
 * botones. Ademas puede llevar una pista de ejemplo, que se ve tenue solo
 * mientras el campo esta vacio y no tiene el foco.
 */
public class CampoTexto extends JTextField {

    private String pista;

    public CampoTexto(int columnas) {
        super(columnas);
        setOpaque(false);
        setFont(UIConstants.FONT_TEXTO);
        setForeground(UIConstants.TEXTO);
        setCaretColor(UIConstants.TEXTO);
        setSelectionColor(UIConstants.BORDE);
        setSelectedTextColor(UIConstants.TEXTO);
        // El aire interior hace de borde: el trazo se dibuja aparte, redondeado.
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    /** Pista de ejemplo (Ej: Metro Centro) que desaparece al escribir. */
    public void setPista(String texto) {
        pista = texto;
        // Con y sin foco se repinta: la pista se oculta mientras se escribe.
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evento) {
                repaint();
            }

            @Override
            public void focusLost(FocusEvent evento) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int radio = UIConstants.RADIO_BOTON;
        // Sin antialiasing: las esquinas duras siguen el estilo pixel art.
        g2.setColor(UIConstants.ESCENARIO);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio);
        g2.setColor(UIConstants.BORDE);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio);
        g2.dispose();
        super.paintComponent(g);
        if (pista != null && getText().isEmpty() && !isFocusOwner()) {
            Graphics2D g3 = (Graphics2D) g.create();
            g3.setFont(UIConstants.FONT_CHICA);
            g3.setColor(UIConstants.TEXTO_SUAVE);
            int altoTexto = g3.getFontMetrics().getHeight();
            g3.drawString(pista, 12,
                    (getHeight() - altoTexto) / 2 + g3.getFontMetrics().getAscent());
            g3.dispose();
        }
    }
}
