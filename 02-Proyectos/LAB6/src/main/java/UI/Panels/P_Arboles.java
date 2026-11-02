package UI.Panels;

import Arboles.ArbolBinario;
import Arboles.Nodo;
import Recorrido.Inorden;
import Recorrido.Postorden;
import Recorrido.Preorden;
import UI.Elements.BotonEstilizado;
import UI.Elements.BotonEstilizado.Glifo;
import UI.Elements.Fuentes;
import UI.Elements.Imagenes;
import UI.Elements.Navegacion;
import UI.Elements.UIConstants;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class P_Arboles extends JPanel {

    private enum Rama { IZQUIERDA, DERECHA }

    private record TareaPregunta(Nodo padre, Rama rama, int nivelHijo) {}

    private static final int MARGEN_Y = 36;

    private final ArbolBinario arbol = new ArbolBinario();
    private final Map<Nodo, Color> colores = new HashMap<>();
    private final Deque<TareaPregunta> tareas = new ArrayDeque<>();

    private int totalNodosCreados = 0;
    private Nodo nodoEnFoco = null;
    private TareaPregunta tareaActual = null;
    private boolean finalizado = false;

    private final JPanel lienzo;
    private final JPanel panelAcciones;
    private final JLabel lblBadgeEstado = new JLabel(" ");
    private final JLabel lblPregunta = new JLabel(" ");
    private final JLabel lblAviso = new JLabel(" ");
    private final JPanel panelRecorridos = new JPanel();
    private final JLabel lblPreorden = new JLabel(" ");
    private final JLabel lblInorden = new JLabel(" ");
    private final JLabel lblPostorden = new JLabel(" ");

    private final BotonEstilizado btnSi;
    private final BotonEstilizado btnNo;
    private final BotonEstilizado btnFinalizar;
    private final BotonEstilizado btnNuevo;
    private final BotonEstilizado btnRegresar;
    private final BotonEstilizado btnReiniciar;

    public P_Arboles() {
        setLayout(new BorderLayout(0, 12));
        setBackground(UIConstants.FONDO);
        setBorder(BorderFactory.createEmptyBorder(UIConstants.MARGEN / 2, UIConstants.MARGEN,
                UIConstants.MARGEN / 2, UIConstants.MARGEN));

        add(crearCabecera(), BorderLayout.NORTH);

        lienzo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                pintarArbol((Graphics2D) g, getWidth(), getHeight());
            }
        };
        lienzo.setOpaque(true);
        lienzo.setBackground(UIConstants.ESCENARIO);
        lienzo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDE, 2, true),
                BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));
        add(lienzo, BorderLayout.CENTER);

        btnSi = BotonEstilizado.textoAjustado("1 - Sí", UIConstants.CELESTE, UIConstants.AZULITO, UIConstants.AZUL_PRESION);
        btnNo = BotonEstilizado.textoAjustado("0 - No", UIConstants.GRIS_SUAVE, UIConstants.GRIS_OSCURO, UIConstants.GRIS_PRESION);
        btnFinalizar = BotonEstilizado.textoAjustado("Finalizar Árbol", UIConstants.AMARILLO_PASTEL, UIConstants.LILA_SUAVE, UIConstants.AZUL_PRESION);
        btnNuevo = BotonEstilizado.textoAjustado("Nuevo Árbol", UIConstants.BLANCO_HUESO, UIConstants.LILA_SUAVE, UIConstants.AMARILLO_PASTEL);

        btnSi.addActionListener(e -> responderSi());
        btnNo.addActionListener(e -> responderNo());
        btnFinalizar.addActionListener(e -> intentarFinalizar());
        btnNuevo.addActionListener(e -> reiniciar());

        btnRegresar = BotonEstilizado.icono(Glifo.FLECHA, 70,
                UIConstants.CELESTE, UIConstants.AZULITO, UIConstants.AZUL_PRESION);
        btnRegresar.addActionListener(e -> Navegacion.irA(this, new UNI_Portada()));

        btnReiniciar = BotonEstilizado.icono(Glifo.RESET, 70,
                UIConstants.GRIS_SUAVE, UIConstants.GRIS_OSCURO, UIConstants.GRIS_PRESION);
        btnReiniciar.addActionListener(e -> reiniciar());

        panelAcciones = crearPanelInferior();
        add(panelAcciones, BorderLayout.SOUTH);

        iniciarConstruccion();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage fondo = Imagenes.obtener("fondo_blured");
        if (fondo != null && fondo.getWidth() > 0 && fondo.getHeight() > 0) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            double escala = Math.max(getWidth() / (double) fondo.getWidth(), getHeight() / (double) fondo.getHeight());
            int dw = (int) Math.round(fondo.getWidth() * escala);
            int dh = (int) Math.round(fondo.getHeight() * escala);
            int dx = (getWidth() - dw) / 2;
            int dy = (getHeight() - dh) / 2;
            g2.drawImage(fondo, dx, dy, dw, dh, null);
            g2.dispose();
        }
    }

    private JPanel crearCabecera() {
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setOpaque(false);

        JLabel titulo = new JLabel("Laboratorio #6 · Árboles Binarios (Máx 5 Niveles)") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                FontMetrics fm = g2.getFontMetrics(getFont());
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

                g2.setFont(getFont());
                g2.setColor(new Color(0x0A, 0x14, 0x1D, 240));
                g2.drawString(getText(), x + 2, y + 2);

                g2.setColor(getForeground());
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        titulo.setFont(UIConstants.FONT_TITULO);
        titulo.setForeground(UIConstants.ACENTO);

        JPanel contenedorTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        contenedorTitulo.setOpaque(true);
        contenedorTitulo.setBackground(new Color(0x0E, 0x22, 0x30, 225));
        contenedorTitulo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDE, 2, true),
                BorderFactory.createEmptyBorder(6, 24, 6, 24)
        ));
        contenedorTitulo.add(titulo);

        JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centro.setOpaque(false);
        centro.add(contenedorTitulo);

        cabecera.add(centro, BorderLayout.CENTER);
        return cabecera;
    }

    private JPanel crearPanelInferior() {
        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setOpaque(false);

        JPanel rectangulo = new JPanel();
        rectangulo.setLayout(new BoxLayout(rectangulo, BoxLayout.Y_AXIS));
        rectangulo.setOpaque(true);
        rectangulo.setBackground(UIConstants.ESCENARIO);
        rectangulo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDE, 2, true),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        lblBadgeEstado.setFont(UIConstants.FONT_CHICA);
        lblBadgeEstado.setForeground(UIConstants.ACENTO);
        lblBadgeEstado.setAlignmentX(CENTER_ALIGNMENT);

        lblPregunta.setFont(UIConstants.FONT_SUBTITULO);
        lblPregunta.setForeground(UIConstants.TEXTO);
        lblPregunta.setAlignmentX(CENTER_ALIGNMENT);

        lblAviso.setFont(UIConstants.FONT_CHICA);
        lblAviso.setForeground(UIConstants.ERROR);
        lblAviso.setAlignmentX(CENTER_ALIGNMENT);

        JPanel filaBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 0));
        filaBotones.setOpaque(false);
        filaBotones.setAlignmentX(CENTER_ALIGNMENT);
        filaBotones.add(btnSi);
        filaBotones.add(btnNo);
        filaBotones.add(btnFinalizar);
        filaBotones.add(btnNuevo);

        panelRecorridos.setLayout(new BoxLayout(panelRecorridos, BoxLayout.Y_AXIS));
        panelRecorridos.setOpaque(false);
        panelRecorridos.setAlignmentX(CENTER_ALIGNMENT);

        panelRecorridos.add(crearFilaRecorrido("PREORDEN", lblPreorden, UIConstants.ACENTO));
        panelRecorridos.add(Box.createVerticalStrut(4));
        panelRecorridos.add(crearFilaRecorrido("INORDEN", lblInorden, UIConstants.AMARILLO_PASTEL));
        panelRecorridos.add(Box.createVerticalStrut(4));
        panelRecorridos.add(crearFilaRecorrido("POSTORDEN", lblPostorden, UIConstants.LILA_SUAVE));

        rectangulo.add(lblBadgeEstado);
        rectangulo.add(Box.createVerticalStrut(2));
        rectangulo.add(lblPregunta);
        rectangulo.add(lblAviso);
        rectangulo.add(Box.createVerticalStrut(6));
        rectangulo.add(filaBotones);
        rectangulo.add(Box.createVerticalStrut(6));
        rectangulo.add(panelRecorridos);
        rectangulo.setMaximumSize(new Dimension(520, Integer.MAX_VALUE));

        raiz.add(rectangulo, BorderLayout.CENTER);
        raiz.add(btnRegresar, BorderLayout.WEST);
        raiz.add(btnReiniciar, BorderLayout.EAST);

        return raiz;
    }

    private JPanel crearFilaRecorrido(String etiqueta, JLabel lblValor, Color colorAcento) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        fila.setOpaque(false);
        fila.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblTag = new JLabel(" " + etiqueta + " ");
        lblTag.setFont(UIConstants.FONT_CHICA);
        lblTag.setForeground(UIConstants.FONDO);
        lblTag.setOpaque(true);
        lblTag.setBackground(colorAcento);
        lblTag.setPreferredSize(new Dimension(88, 22));
        lblTag.setHorizontalAlignment(JLabel.CENTER);

        lblValor.setFont(UIConstants.FONT_TEXTO);
        lblValor.setForeground(UIConstants.TEXTO);
        lblValor.setHorizontalAlignment(JLabel.CENTER);

        fila.add(lblTag);
        fila.add(lblValor);
        return fila;
    }

    private void iniciarConstruccion() {
        arbol.limpiar();
        colores.clear();
        tareas.clear();
        totalNodosCreados = 0;
        finalizado = false;

        Nodo raiz = new Nodo(generarEtiqueta(totalNodosCreados++));
        arbol.setRaiz(raiz);
        asignarColor(raiz);

        nodoEnFoco = raiz;
        tareas.push(new TareaPregunta(raiz, Rama.DERECHA, 2));
        tareas.push(new TareaPregunta(raiz, Rama.IZQUIERDA, 2));

        siguientePregunta();
    }

    private void responderSi() {
        if (tareaActual == null || finalizado) return;

        lblAviso.setText(" ");
        Nodo nuevo = new Nodo(generarEtiqueta(totalNodosCreados++));
        asignarColor(nuevo);

        if (tareaActual.rama == Rama.IZQUIERDA) {
            tareaActual.padre.setIzq(nuevo);
        } else {
            tareaActual.padre.setDer(nuevo);
        }

        nodoEnFoco = nuevo;

        if (tareaActual.nivelHijo < ArbolBinario.MAX_NIVEL) {
            tareas.push(new TareaPregunta(nuevo, Rama.DERECHA, tareaActual.nivelHijo + 1));
            tareas.push(new TareaPregunta(nuevo, Rama.IZQUIERDA, tareaActual.nivelHijo + 1));
        }

        siguientePregunta();
    }

    private void responderNo() {
        if (tareaActual == null || finalizado) return;

        lblAviso.setText(" ");
        if (tareaActual.rama == Rama.IZQUIERDA) {
            tareaActual.padre.setIzq(null);
        } else {
            tareaActual.padre.setDer(null);
        }

        siguientePregunta();
    }

    private void intentarFinalizar() {
        if (arbol.altura() < 2) {
            lblAviso.setText("Debes ingresar al menos 2 niveles antes de finalizar.");
            return;
        }
        completarArbol();
    }

    private void siguientePregunta() {
        if (tareas.isEmpty()) {
            completarArbol();
            return;
        }

        tareaActual = tareas.pop();
        nodoEnFoco = tareaActual.padre;

        String lado = tareaActual.rama == Rama.IZQUIERDA ? "IZQUIERDA" : "DERECHA";
        lblBadgeEstado.setText(String.format("CONFIGURACIÓN · PASO INTERACTIVO (NIVEL %d / %d)",
                tareaActual.nivelHijo, ArbolBinario.MAX_NIVEL));
        lblPregunta.setText(String.format("¿Existe nodo por %s de [%s]?: 1(Sí) - 0(No)",
                lado, tareaActual.padre.getDato()));

        btnSi.setVisible(true);
        btnNo.setVisible(true);
        btnFinalizar.setVisible(true);
        btnNuevo.setVisible(false);

        panelRecorridos.setVisible(false);
        lienzo.repaint();
    }

    private void completarArbol() {
        finalizado = true;
        tareaActual = null;
        nodoEnFoco = null;

        lblBadgeEstado.setText("✔ ÁRBOL BINARIO COMPLETADO");
        lblBadgeEstado.setForeground(UIConstants.PALETA_NODOS[2]);
        lblPregunta.setText(String.format("Estructura final: %d nodos en %d niveles.",
                arbol.contarNodos(), arbol.altura()));
        lblAviso.setText(" ");

        btnSi.setVisible(false);
        btnNo.setVisible(false);
        btnFinalizar.setVisible(false);
        btnNuevo.setVisible(true);

        List<Object> pre = Preorden.ejecutar(arbol.getRaiz());
        List<Object> in = Inorden.ejecutar(arbol.getRaiz());
        List<Object> pos = Postorden.ejecutar(arbol.getRaiz());

        lblPreorden.setText(formatearRecorrido(pre));
        lblInorden.setText(formatearRecorrido(in));
        lblPostorden.setText(formatearRecorrido(pos));

        panelRecorridos.setVisible(true);
        lienzo.repaint();
    }

    private String formatearRecorrido(List<Object> elementos) {
        String sep = elementos.size() > 20 ? " " : "  ";
        return elementos.stream().map(Object::toString).collect(Collectors.joining(sep));
    }

    private void reiniciar() {
        lblAviso.setText(" ");
        lblBadgeEstado.setForeground(UIConstants.ACENTO);
        iniciarConstruccion();
    }

    private String generarEtiqueta(int indice) {
        if (indice < 26) {
            return String.valueOf((char) ('A' + indice));
        }
        int prefijo = (indice / 26) - 1;
        int resto = indice % 26;
        return "" + (char) ('A' + prefijo) + (char) ('A' + resto);
    }

    private void asignarColor(Nodo nodo) {
        int indice = colores.size() % UIConstants.PALETA_NODOS.length;
        colores.put(nodo, UIConstants.PALETA_NODOS[indice]);
    }

    private void pintarArbol(Graphics2D g2, int ancho, int alto) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (arbol.estaVacio()) return;

        int anchoArbol = Math.min(ancho - 80, 1650);
        int offsetX = (ancho - anchoArbol) / 2;
        int diametro = Math.clamp(anchoArbol / 28, 42, 54);

        int altoDisponible = Math.max(alto - MARGEN_Y * 2 - 20, 100);
        int pasoY = Math.clamp(altoDisponible / (ArbolBinario.MAX_NIVEL - 1), 75, 160);
        int alturaTotalArbol = pasoY * (ArbolBinario.MAX_NIVEL - 1);
        int baseY = MARGEN_Y + Math.max(5, (altoDisponible - alturaTotalArbol) / 2);

        pintarRamas(g2, arbol.getRaiz(), 1, 0, anchoArbol, offsetX, baseY, pasoY);
        pintarNodos(g2, arbol.getRaiz(), 1, 0, anchoArbol, offsetX, baseY, pasoY, diametro);
        pintarHud(g2, ancho);
    }

    private void pintarHud(Graphics2D g2, int ancho) {
        String info = String.format("NIVELES: %d/%d  |  NODOS: %d",
                arbol.altura(), ArbolBinario.MAX_NIVEL, arbol.contarNodos());
        g2.setFont(UIConstants.FONT_CHICA);
        FontMetrics fm = g2.getFontMetrics();
        int textoAncho = fm.stringWidth(info);

        int hudW = textoAncho + 16;
        int hudH = 22;
        int hudX = ancho - hudW - 14;
        int hudY = 12;

        g2.setColor(new Color(0x0E, 0x22, 0x30, 190));
        g2.fillRoundRect(hudX, hudY, hudW, hudH, 8, 8);
        g2.setColor(UIConstants.BORDE);
        g2.drawRoundRect(hudX, hudY, hudW, hudH, 8, 8);

        g2.setColor(UIConstants.TEXTO_SUAVE);
        g2.drawString(info, hudX + 8, hudY + (hudH + fm.getAscent() - fm.getDescent()) / 2);
    }

    private void pintarRamas(Graphics2D g2, Nodo nodo, int nivel, int col, int anchoArbol, int offsetX, int baseY, int pasoY) {
        if (nodo == null) return;

        int x = calcularX(nivel, col, anchoArbol, offsetX);
        int y = baseY + (nivel - 1) * pasoY;

        if (nodo.getIzq() != null) {
            int xIzq = calcularX(nivel + 1, col * 2, anchoArbol, offsetX);
            int yIzq = baseY + nivel * pasoY;
            dibujarRama(g2, x, y, xIzq, yIzq);
            pintarRamas(g2, nodo.getIzq(), nivel + 1, col * 2, anchoArbol, offsetX, baseY, pasoY);
        }

        if (nodo.getDer() != null) {
            int xDer = calcularX(nivel + 1, col * 2 + 1, anchoArbol, offsetX);
            int yDer = baseY + nivel * pasoY;
            dibujarRama(g2, x, y, xDer, yDer);
            pintarRamas(g2, nodo.getDer(), nivel + 1, col * 2 + 1, anchoArbol, offsetX, baseY, pasoY);
        }
    }

    private void dibujarRama(Graphics2D g2, int x1, int y1, int x2, int y2) {
        g2.setColor(new Color(0x56738B));
        g2.setStroke(new BasicStroke(3.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(x1, y1, x2, y2);
    }

    private void pintarNodos(Graphics2D g2, Nodo nodo, int nivel, int col, int anchoArbol, int offsetX, int baseY, int pasoY, int diametro) {
        if (nodo == null) return;

        int x = calcularX(nivel, col, anchoArbol, offsetX);
        int y = baseY + (nivel - 1) * pasoY;

        pintarNodo(g2, nodo, x, y, diametro);

        if (nodo.getIzq() != null) {
            pintarNodos(g2, nodo.getIzq(), nivel + 1, col * 2, anchoArbol, offsetX, baseY, pasoY, diametro);
        }
        if (nodo.getDer() != null) {
            pintarNodos(g2, nodo.getDer(), nivel + 1, col * 2 + 1, anchoArbol, offsetX, baseY, pasoY, diametro);
        }
    }

    private void pintarNodo(Graphics2D g2, Nodo nodo, int x, int y, int diametro) {
        Color color = colores.getOrDefault(nodo, UIConstants.ACENTO);
        int radio = diametro / 2;

        if (nodo == nodoEnFoco && !finalizado) {
            g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 80));
            g2.fillOval(x - radio - 7, y - radio - 7, diametro + 14, diametro + 14);
        }

        g2.setColor(UIConstants.ESCENARIO);
        g2.fillOval(x - radio, y - radio, diametro, diametro);

        BufferedImage circuloTintado = Imagenes.obtenerCirculoTintado(color, diametro);
        if (circuloTintado != null) {
            g2.drawImage(circuloTintado, x - radio, y - radio, null);
        } else {
            g2.setColor(color);
            g2.setStroke(new BasicStroke(3));
            g2.drawOval(x - radio, y - radio, diametro, diametro);
        }

        String texto = String.valueOf(nodo.getDato());
        Font fuenteNodo = Fuentes.obtener(true, diametro >= 42 ? 16 : 8);
        g2.setFont(fuenteNodo);
        FontMetrics fm = g2.getFontMetrics();
        int tx = x - fm.stringWidth(texto) / 2;
        int ty = y + (fm.getAscent() - fm.getDescent()) / 2;

        g2.setColor(UIConstants.TEXTO);
        g2.drawString(texto, tx, ty);
    }

    private int calcularX(int nivel, int col, int anchoArbol, int offsetX) {
        int columnasNivel = 1 << (nivel - 1);
        return offsetX + (int) Math.round((2.0 * col + 1.0) * anchoArbol / (2.0 * columnasNivel));
    }
}
