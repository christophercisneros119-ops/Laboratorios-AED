package UI.Paneles;

import UI.Elementos.UIConstants;
import static UI.Elementos.UIConstants.*;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

public class P_MenuOrdenamiento extends javax.swing.JPanel {

    private java.awt.Image fondoImage;

    public P_MenuOrdenamiento() {
        setOpaque(false);
        fondoImage = new ImageIcon(getClass().getResource("/img/fondo3.jpg")).getImage();
        initComponents();
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new UI.Elementos.GlassButton("Burbuja", DARK_AQUA, WHITE, WHITE);
        jButton2 = new UI.Elementos.GlassButton("Burbuja con Señal", DARK_AQUA, WHITE, WHITE);
        jButton3 = new UI.Elementos.GlassButton("Shell", DARK_AQUA, WHITE, WHITE);
        jButton4 = new UI.Elementos.GlassButton("Selección Directa", DARK_AQUA, WHITE, WHITE);
        jButton5 = new UI.Elementos.GlassButton("Sacudida", DARK_AQUA, WHITE, WHITE);
        jButton6 = new UI.Elementos.GlassButton("Baraja", DARK_AQUA, WHITE, WHITE);
        jButton7 = new UI.Elementos.GlassButton("Atrás", DARK_AQUA, WHITE, WHITE);

        jLabel1.setFont(FONT_TITLE);
        jLabel1.setForeground(WHITE);
        jLabel1.setText("Métodos de Ordenación");

        jButton1.setFont(FONT_SUBTITLE);
        jButton1.setForeground(WHITE);
        jButton1.setText("Burbuja");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(FONT_SUBTITLE);
        jButton2.setForeground(WHITE);
        jButton2.setText("Burbuja con Señal");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(FONT_SUBTITLE);
        jButton3.setForeground(WHITE);
        jButton3.setText("Shell");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(FONT_SUBTITLE);
        jButton4.setForeground(WHITE);
        jButton4.setText("Selección Directa");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(FONT_SUBTITLE);
        jButton5.setForeground(WHITE);
        jButton5.setText("Sacudida");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(FONT_SUBTITLE);
        jButton6.setForeground(WHITE);
        jButton6.setText("Baraja");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(FONT_SUBTITLE);
        jButton7.setForeground(WHITE);
        jButton7.setText("Atrás");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton7, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jButton7, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        navegarA(new P_Ordenamiento(new Algoritmos.Burbuja(), "Método Burbuja"));
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        navegarA(new P_Ordenamiento(new Algoritmos.BurbujaSenal(), "Método Burbuja con Señal"));
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        navegarA(new P_Ordenamiento(new Algoritmos.Shell(), "Método Shell"));
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        navegarA(new P_Ordenamiento(new Algoritmos.Seleccion(), "Método Selección Directa"));
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        navegarA(new P_Ordenamiento(new Algoritmos.Sacudida(), "Método Sacudida"));
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
        navegarA(new P_Ordenamiento(new Algoritmos.Baraja(), "Método Baraja"));
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        navegarA(new P_MenuArreglos());
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
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
}
