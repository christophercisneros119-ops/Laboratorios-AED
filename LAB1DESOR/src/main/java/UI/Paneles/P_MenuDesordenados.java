package UI.Paneles;

import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

public class P_MenuDesordenados extends javax.swing.JPanel {

    private java.awt.Image fondoImage;

    public P_MenuDesordenados() {
        setOpaque(false);
        fondoImage = loadFondo();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new UI.Elementos.GlassButton("Ejercicio #1", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton2 = new UI.Elementos.GlassButton("Ejercicio #2", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton3 = new UI.Elementos.GlassButton("Ejercicio #3", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton4 = new UI.Elementos.GlassButton("Atr\u00e1s", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);

        setBackground(new java.awt.Color(0, 51, 51));

        jLabel1.setFont(new java.awt.Font("Swis721 Blk BT", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Arreglos Desordenados");

        jButton1.setBackground(new java.awt.Color(0, 51, 51));
        jButton1.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Ejercicio #1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 51, 51));
        jButton2.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Ejercicio #2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 51, 51));
        jButton3.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Ejercicio #3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 51, 51));
        jButton4.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Atr\u00e1s");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        navegarA(new P_Ejercicio1());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        navegarA(new P_Ejercicio2());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        navegarA(new P_Ejercicio3());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        navegarA(new P_Arreglos());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void navegarA(javax.swing.JPanel destino) {
        javax.swing.JFrame ventana = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        if (ventana != null) {
            ventana.setContentPane(destino);
            ventana.revalidate();
            ventana.repaint();
        }
    }

    private java.awt.Image loadFondo() {
        String[] resources = {"/img/fondo4.jpg"};
        for (String path : resources) {
            try {
                java.net.URL url = getClass().getResource(path);
                if (url != null) {
                    return new javax.swing.ImageIcon(url).getImage();
                }
            } catch (Exception ignored) {
            }
        }
        String[] files = {"fondo4.jpg", "fondo4.png"};
        for (String name : files) {
            try {
                java.io.File file = new java.io.File(name);
                if (file.exists()) {
                    return new javax.swing.ImageIcon(file.getAbsolutePath()).getImage();
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        if (fondoImage != null) {
            drawCoverImage(g2, w, h);
        } else {
            g2.setColor(new java.awt.Color(12, 34, 31));
            g2.fillRect(0, 0, w, h);
        }

        drawVeil(g2, w, h);

        g2.dispose();
    }

    private void drawCoverImage(java.awt.Graphics2D g2, int w, int h) {
        int iw = fondoImage.getWidth(null);
        int ih = fondoImage.getHeight(null);
        if (iw <= 0 || ih <= 0) {
            return;
        }
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
                new java.awt.Color[]{
                        new java.awt.Color(4, 12, 16, 30),
                        new java.awt.Color(4, 12, 16, 80),
                        new java.awt.Color(4, 12, 16, 150)});
        g2.setPaint(veil);
        g2.fillRect(0, 0, w, h);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
