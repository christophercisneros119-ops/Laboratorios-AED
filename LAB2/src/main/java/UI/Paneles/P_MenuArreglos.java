package UI.Paneles;

import UI.Elementos.UIConstants;
import static UI.Elementos.UIConstants.*;
import javax.swing.ImageIcon;

public class P_MenuArreglos extends javax.swing.JPanel {

    private java.awt.Image fondoImage;

    public P_MenuArreglos() {
        setOpaque(false);
        fondoImage = new ImageIcon(getClass().getResource("/img/fondo2.jpg")).getImage();
        initComponents();
    }

    private void initComponents() {

        jButton1 = new UI.Elementos.GlassButton("Métodos de Ordenación", DARK_AQUA, WHITE, WHITE);
        jButton2 = new UI.Elementos.GlassButton("Búsqueda", DARK_AQUA, WHITE, WHITE);
        jButton3 = new UI.Elementos.GlassButton("Atrás", DARK_AQUA, WHITE, WHITE);
        jLabel1 = new javax.swing.JLabel();

        jButton1.setFont(FONT_TITLE);
        jButton1.setForeground(WHITE);
        jButton1.setText("Métodos de Ordenación");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(FONT_TITLE);
        jButton2.setForeground(WHITE);
        jButton2.setText("Búsqueda");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(FONT_TITLE);
        jButton3.setForeground(WHITE);
        jButton3.setText("Atrás");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(FONT_TITLE_XL);
        jLabel1.setForeground(WHITE);
        jLabel1.setText("Laboratorio #2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        navegarA(new P_MenuOrdenamiento());
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        navegarA(new P_BusquedaBinaria());
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        navegarA(new UNI_Portada());
    }

    private void navegarA(javax.swing.JPanel destino) {
        javax.swing.JFrame ventana = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (ventana != null) {
            ventana.setContentPane(destino);
            ventana.revalidate();
            ventana.repaint();
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
}
