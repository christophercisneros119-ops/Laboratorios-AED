package UI.Paneles;

import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

public class P_MenuOrdenamiento extends javax.swing.JPanel {

    private java.awt.Image fondoImage;

    public P_MenuOrdenamiento() {
        setOpaque(false);
        fondoImage = loadFondo();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new UI.Elementos.GlassButton("Burbuja", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton2 = new UI.Elementos.GlassButton("Burbuja con Señal", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton3 = new UI.Elementos.GlassButton("Shell", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton4 = new UI.Elementos.GlassButton("Selección Directa", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton5 = new UI.Elementos.GlassButton("Sacudida", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton6 = new UI.Elementos.GlassButton("Baraja", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton7 = new UI.Elementos.GlassButton("Atr\u00e1s", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);

        setBackground(new java.awt.Color(0, 51, 51));

        jLabel1.setFont(new java.awt.Font("Swis721 Blk BT", 0, 24));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Métodos de Ordenación");

        jButton1.setBackground(new java.awt.Color(0, 51, 51));
        jButton1.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Burbuja");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 51, 51));
        jButton2.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Burbuja con Señal");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 51, 51));
        jButton3.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Shell");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 51, 51));
        jButton4.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Selección Directa");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 51, 51));
        jButton5.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Sacudida");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 51, 51));
        jButton6.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Baraja");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(0, 51, 51));
        jButton7.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Atr\u00e1s");
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

    private java.awt.Image loadFondo() {
        String[] resources = {"/img/fondo3.jpg"};
        for (String path : resources) {
            try {
                java.net.URL url = getClass().getResource(path);
                if (url != null) {
                    return new javax.swing.ImageIcon(url).getImage();
                }
            } catch (Exception ignored) {
            }
        }
        String[] files = {"fondo3.jpg", "fondo3.png"};
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

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
}