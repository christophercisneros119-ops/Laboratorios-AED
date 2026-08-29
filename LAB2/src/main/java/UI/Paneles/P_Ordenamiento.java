package UI.Paneles;

import Algoritmos.IMetodoOrdenamiento;
import UI.Elementos.UIConstants;
import static UI.Elementos.UIConstants.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import java.awt.*;
import javax.swing.ImageIcon;

public class P_Ordenamiento extends javax.swing.JPanel {

    private final IMetodoOrdenamiento metodo;
    private final String tituloMetodo;
    private int tamano;
    private boolean tamanoDefinido;
    private java.awt.Image fondoImage;

    public P_Ordenamiento(IMetodoOrdenamiento metodo, String tituloMetodo) {
        this.metodo = metodo;
        this.tituloMetodo = tituloMetodo;
        setOpaque(false);
        fondoImage = new ImageIcon(getClass().getResource("/img/fondo2.jpg")).getImage();
        initComponents();
        jTextArea1.setEditable(false);
        jTextArea2.setEditable(false);
        jTextArea1.setFont(FONT_CODE);
        jTextArea2.setFont(FONT_CODE);
        jLabel1.setText(tituloMetodo);
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new UI.Elementos.GlassButton("Guardar", DARK_AQUA, WHITE, WHITE);
        jButton2 = new UI.Elementos.GlassButton("Guardar", DARK_AQUA, WHITE, WHITE);
        jButton3 = new UI.Elementos.GlassButton("Atrás", DARK_AQUA, WHITE, WHITE);
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        jLabel1.setFont(FONT_TITLE);
        jLabel1.setForeground(WHITE);
        jLabel1.setText("Método");

        jLabel2.setFont(FONT_SUBTITLE);
        jLabel2.setForeground(WHITE);
        jLabel2.setText("Tamaño del arreglo:");

        jLabel3.setFont(FONT_SUBTITLE);
        jLabel3.setForeground(WHITE);
        jLabel3.setText("Elementos (separados por coma):");

        jLabel4.setFont(FONT_LABEL);
        jLabel4.setForeground(WHITE);
        jLabel4.setText("Arreglo como fue leído");

        jLabel5.setFont(FONT_LABEL);
        jLabel5.setForeground(WHITE);
        jLabel5.setText("Arreglo ya ordenado");

        jTextField1.setFont(FONT_BODY);
        jTextField1.setForeground(DARK_GRAY);

        jTextField2.setFont(FONT_BODY);
        jTextField2.setForeground(DARK_GRAY);

        jButton1.setForeground(WHITE);
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setForeground(WHITE);
        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setForeground(WHITE);
        jButton3.setText("Atrás");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String texto = jTextField1.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el tamaño del arreglo.");
            return;
        }
        if (!esNumeroEntero(texto)) {
            JOptionPane.showMessageDialog(this, "El tamaño debe ser un valor numérico entero.");
            return;
        }
        int valor = Integer.parseInt(texto);
        if (valor <= 0) {
            JOptionPane.showMessageDialog(this, "El tamaño debe ser mayor que cero.");
            return;
        }
        tamano = valor;
        tamanoDefinido = true;
        JOptionPane.showMessageDialog(this, "Tamaño del arreglo definido: " + tamano);
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        if (!tamanoDefinido) {
            JOptionPane.showMessageDialog(this, "Primero define el tamaño del arreglo.");
            return;
        }
        String texto = jTextField2.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa los elementos del arreglo separados por coma.");
            return;
        }
        int[] arreglo;
        try {
            arreglo = IMetodoOrdenamiento.leerArreglo(texto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El arreglo solo debe contener valores numéricos.");
            return;
        }
        if (arreglo.length > tamano) {
            JOptionPane.showMessageDialog(this, "El arreglo no puede tener más de " + tamano + " elementos.");
            return;
        }
        int[] ordenado = arreglo.clone();
        metodo.ordenar(ordenado);
        jTextArea1.setText(IMetodoOrdenamiento.formatear(arreglo));
        jTextArea2.setText(IMetodoOrdenamiento.formatear(ordenado));
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JFrame ventana = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (ventana != null) {
            ventana.setContentPane(new P_MenuOrdenamiento());
            ventana.revalidate();
            ventana.repaint();
        }
    }

    private boolean esNumeroEntero(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        drawCoverImage(g2, w, h);
        drawVeil(g2, w, h);

        g2.dispose();
    }

    private void drawCoverImage(java.awt.Graphics2D g2, int w, int h) {
        int iw = fondoImage.getWidth(null);
        int ih = fondoImage.getHeight(null);
        double scale = Math.max(w / (double) iw, h / (double) ih);
        int dw = (int) (iw * scale);
        int dh = (int) (ih * scale);
        int dx = (w - dw) / 2;
        int dy = (h - dh) / 2;
        g2.drawImage(fondoImage, dx, dy, dw, dh, null);
    }

    private void drawVeil(java.awt.Graphics2D g2, int w, int h) {
        java.awt.LinearGradientPaint veil = new java.awt.LinearGradientPaint(
                0, 0, 0, h,
                new float[]{0f, 0.6f, 1f},
                new java.awt.Color[]{VEIL_TOP, VEIL_MID, VEIL_BOTTOM});
        g2.setPaint(veil);
        g2.fillRect(0, 0, w, h);
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
}
