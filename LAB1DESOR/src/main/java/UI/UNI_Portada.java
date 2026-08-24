package UI;

public class UNI_Portada extends javax.swing.JPanel {

    private static final java.awt.Color BG_TOP = new java.awt.Color(12, 34, 31);
    private static final java.awt.Color BG_MID = new java.awt.Color(9, 38, 44);
    private static final java.awt.Color BG_BOTTOM = new java.awt.Color(5, 11, 15);

    private static final int SIDEBAR_W = UI.SidebarPanel.SIDEBAR_WIDTH;

    private java.awt.Image fondoImage;

    private final UI.TaskbarMenuButton menuButton = new UI.TaskbarMenuButton();
    private final UI.SidebarPanel sidebar = new UI.SidebarPanel();
    private final UI.DesktopDateWidget dateWidget = new UI.DesktopDateWidget();

    private boolean sidebarOpen;
    private int sidebarX;
    private int sidebarTarget;
    private javax.swing.Timer sidebarTimer;
    private Runnable onSlideDone;

    /**
     * Creates new form UNI_Portada
     */
    public UNI_Portada() {
        initComponents();
        setPreferredSize(new java.awt.Dimension(800, 600));
        setOpaque(false);

        fondoImage = loadFondo();

        memberTile1.setFullName("Janelly Romero");
        memberTile1.setCarnetNumber("2025-1905U");
        memberTile2.setFullName("Moises Alemán");
        memberTile2.setCarnetNumber("2025-2560U");
        memberTile3.setFullName("Christopher Cisneros");
        memberTile3.setCarnetNumber("2025-0032U");

        buildOverlay();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                reposition();
            }
        });

        javax.swing.SwingUtilities.invokeLater(this::reposition);
    }

    private void buildOverlay() {
        overlay.add(menuButton);
        overlay.add(dateWidget);
        overlay.add(sidebar);
        setComponentZOrder(overlay, 0);

        menuButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                openSidebar();
            }
        });
        sidebar.getCloseButton().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                closeSidebar();
            }
        });

        sidebarX = getWidth();
    }

    private void openSidebar() {
        if (sidebarOpen) {
            return;
        }
        sidebarOpen = true;
        menuButton.setVisible(false);
        startSlide(getWidth() - SIDEBAR_W, null);
    }

    private void closeSidebar() {
        if (!sidebarOpen) {
            return;
        }
        sidebarOpen = false;
        startSlide(getWidth(), () -> menuButton.setVisible(true));
    }

    private void startSlide(int target, Runnable done) {
        sidebarTarget = target;
        onSlideDone = done;
        if (sidebarTimer == null) {
            sidebarTimer = new javax.swing.Timer(12, e -> stepSlide());
        }
        sidebarTimer.start();
    }

    private void stepSlide() {
        int dx = sidebarTarget - sidebarX;
        if (Math.abs(dx) <= 8) {
            sidebarX = sidebarTarget;
            sidebarTimer.stop();
            placeSidebar();
            if (onSlideDone != null) {
                onSlideDone.run();
            }
        } else {
            sidebarX += (int) Math.signum(dx) * 12;
            placeSidebar();
        }
    }

    private void placeSidebar() {
        int h = getHeight();
        sidebar.setBounds(sidebarX, 0, SIDEBAR_W, h);
        sidebar.repaint();
    }

    private void reposition() {
        int w = getWidth();
        int h = getHeight();
        menuButton.setBounds(w - 52, h - 31, 26, 26);
        dateWidget.setBounds((w - 148) / 2, 196, 148, 104);
        sidebarX = sidebarOpen ? w - SIDEBAR_W : w;
        placeSidebar();
    }

    private java.awt.Image loadFondo() {
        String[] resources = {"/img/fondo.png", "/img/fondo.jpg"};
        for (String path : resources) {
            try {
                java.net.URL url = getClass().getResource(path);
                if (url != null) {
                    return new javax.swing.ImageIcon(url).getImage();
                }
            } catch (Exception ignored) {
            }
        }
        String[] files = {"fondo.png", "fondo.jpg"};
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
            drawFallbackBackground(g2, w, h);
        }

        drawVeil(g2, w, h);

        drawHeaderBand(g2);

        drawTaskbar(g2, w, h);

        g2.dispose();
    }

    private void drawCoverImage(java.awt.Graphics2D g2, int w, int h) {
        int iw = fondoImage.getWidth(null);
        int ih = fondoImage.getHeight(null);
        if (iw <= 0 || ih <= 0) {
            drawFallbackBackground(g2, w, h);
            return;
        }
        double scale = Math.max(w / (double) iw, h / (double) ih);
        int dw = (int) (iw * scale);
        int dh = (int) (ih * scale);
        int dx = (w - dw) / 2;
        int dy = (h - dh) / 2;
        g2.drawImage(fondoImage, dx, dy, dw, dh, null);
    }

    private void drawFallbackBackground(java.awt.Graphics2D g2, int w, int h) {
        java.awt.LinearGradientPaint bg = new java.awt.LinearGradientPaint(
                0, 0, 0, h,
                new float[]{0f, 0.5f, 1f},
                new java.awt.Color[]{BG_TOP, BG_MID, BG_BOTTOM});
        g2.setPaint(bg);
        g2.fillRect(0, 0, w, h);

        java.awt.RadialGradientPaint mist = new java.awt.RadialGradientPaint(
                new java.awt.geom.Point2D.Double(w * 0.5, h * 0.16),
                h * 0.45f,
                new float[]{0f, 1f},
                new java.awt.Color[]{
                        new java.awt.Color(210, 235, 225, 55),
                        new java.awt.Color(210, 235, 225, 0)});
        g2.setPaint(mist);
        g2.fillRect(0, 0, w, h);

        drawTrees(g2, w, h);
        drawWaterfallVibes(g2, w, h);
    }

    private void drawTrees(java.awt.Graphics2D g2, int w, int h) {
        int baseY = (int) (h * 0.62);
        g2.setColor(new java.awt.Color(8, 24, 24));
        for (int x = -24; x < w + 24; x += 24) {
            int th = 46 + (int) (Math.sin(x * 0.09) * 22);
            g2.fillOval(x - 15, baseY - th, 30, th + 16);
            g2.fillOval(x - 8, baseY - th + 12, 16, th + 6);
        }
        g2.setColor(new java.awt.Color(12, 30, 30));
        g2.fillRect(0, baseY + 4, w, h - baseY - 4);
    }

    private void drawWaterfallVibes(java.awt.Graphics2D g2, int w, int h) {
        int wx = (int) (w * 0.30);
        int topY = (int) (h * 0.30);
        int botY = (int) (h * 0.60);

        java.awt.GradientPaint water = new java.awt.GradientPaint(
                0, topY, new java.awt.Color(220, 245, 240, 40),
                0, botY, new java.awt.Color(220, 245, 240, 10));
        g2.setPaint(water);
        g2.fillRect(wx - 20, topY, 40, botY - topY);

        g2.setColor(new java.awt.Color(235, 250, 245, 45));
        for (int i = 0; i < 6; i++) {
            int sx = wx - 16 + i * 6;
            int sy = topY + 18 + i * 18;
            g2.fillRoundRect(sx, sy, 3, 24 + i * 6, 2, 2);
        }
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

    private void drawHeaderBand(java.awt.Graphics2D g2) {
        int bw = 520;
        int bx = 28;
        g2.setColor(new java.awt.Color(10, 28, 34, 110));
        g2.fillRoundRect(bx, 26, bw, 152, 30, 30);
    }

    private void drawTaskbar(java.awt.Graphics2D g2, int w, int h) {
        int th = 36;
        int y = h - th;

        g2.setColor(new java.awt.Color(48, 52, 56, 205));
        g2.fillRoundRect(0, y, w, th, 10, 10);

        java.awt.LinearGradientPaint topEdge = new java.awt.LinearGradientPaint(
                0, y, 0, y + 6,
                new float[]{0f, 1f},
                new java.awt.Color[]{
                        new java.awt.Color(212, 218, 224, 200),
                        new java.awt.Color(212, 218, 224, 20)});
        g2.setPaint(topEdge);
        g2.fillRect(0, y, w, 6);

        g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        java.awt.FontMetrics fm = g2.getFontMetrics();

        int x = 14;
        drawWeather(g2, x, y);
        x += 86;
        drawSearchBar(g2, x, y);
        x += 156;
        drawGlobe(g2, x, y);
        x += 28;
        drawMusic(g2, x, y);
        x += 28;
        drawExplorerE(g2, x, y);
        x += 28;
        drawFolder(g2, x, y);

        drawRightGroup(g2, w, y, fm);
    }
    
    public UI.SidebarPanel getSidebar() {
        return sidebar;
    }

    private void drawRightGroup(java.awt.Graphics2D g2, int w, int y, java.awt.FontMetrics fm) {
        int right = w - 64;
        String dt = "08/27/2026  01:17pm";
        int dtW = fm.stringWidth(dt);
        int dtX = right - dtW;

        g2.setColor(new java.awt.Color(255, 255, 255, 235));
        g2.drawString(dt, dtX, y + 24);

        int batX = dtX - 34;
        drawBattery(g2, batX, y);
    }

    private void drawWeather(java.awt.Graphics2D g2, int x, int y) {
        int cy = y + 18;
        g2.setColor(new java.awt.Color(255, 224, 130, 235));
        g2.fillOval(x + 4, cy - 11, 10, 10);
        g2.setColor(new java.awt.Color(240, 246, 250, 235));
        g2.fillOval(x, cy - 4, 15, 8);
        g2.fillOval(x + 7, cy - 8, 11, 9);
        g2.setColor(new java.awt.Color(255, 255, 255, 240));
        g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        g2.drawString("90°F", x + 22, cy + 4);
    }

    private void drawSearchBar(java.awt.Graphics2D g2, int x, int y) {
        int w = 140;
        int h = 20;
        int sy = y + (36 - h) / 2;

        g2.setColor(new java.awt.Color(255, 255, 255, 235));
        g2.fillRoundRect(x, sy, w, h, h, h);

        g2.setColor(new java.awt.Color(174, 194, 205));
        g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        g2.drawString("Search...", x + 8, sy + 15);

        g2.setColor(new java.awt.Color(150, 172, 184));
        g2.drawOval(x + w - 17, sy + 4, 9, 9);
        g2.drawLine(x + w - 10, sy + 12, x + w - 5, sy + 17);
    }

    private void drawGlobe(java.awt.Graphics2D g2, int x, int y) {
        int cy = y + 18;
        java.awt.RadialGradientPaint g = new java.awt.RadialGradientPaint(
                new java.awt.geom.Point2D.Double(x + 6, cy - 6), 12f,
                new float[]{0f, 0.7f, 1f},
                new java.awt.Color[]{
                        new java.awt.Color(255, 255, 255, 210),
                        new java.awt.Color(120, 200, 230),
                        new java.awt.Color(40, 120, 170)});
        g2.setPaint(g);
        g2.fillOval(x, cy - 8, 16, 16);

        g2.setColor(new java.awt.Color(255, 255, 255, 170));
        g2.drawOval(x + 4, cy - 8, 8, 16);
        g2.drawOval(x, cy - 8, 16, 16);
        g2.drawLine(x + 8, cy - 8, x + 8, cy + 8);
    }

    private void drawMusic(java.awt.Graphics2D g2, int x, int y) {
        int cy = y + 18;
        g2.setColor(new java.awt.Color(240, 246, 250, 235));
        g2.fillOval(x + 2, cy + 2, 7, 5);
        g2.setStroke(new java.awt.BasicStroke(2f));
        g2.drawLine(x + 9, cy + 6, x + 9, cy - 6);
        g2.drawLine(x + 9, cy - 6, x + 14, cy - 2);
    }

    private void drawExplorerE(java.awt.Graphics2D g2, int x, int y) {
        int cy = y + 18;
        java.awt.RadialGradientPaint g = new java.awt.RadialGradientPaint(
                new java.awt.geom.Point2D.Double(x + 6, cy - 6), 12f,
                new float[]{0f, 0.6f, 1f},
                new java.awt.Color[]{
                        new java.awt.Color(255, 255, 255, 220),
                        new java.awt.Color(90, 160, 230),
                        new java.awt.Color(30, 90, 170)});
        g2.setPaint(g);
        g2.fillOval(x, cy - 8, 16, 16);

        g2.setColor(new java.awt.Color(255, 255, 255, 200));
        g2.setStroke(new java.awt.BasicStroke(1.2f));
        g2.drawOval(x + 1, cy - 7, 14, 14);

        g2.setColor(java.awt.Color.WHITE);
        g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        g2.drawString("e", x + 5, cy + 4);
    }

    private void drawFolder(java.awt.Graphics2D g2, int x, int y) {
        int cy = y + 18;
        g2.setColor(new java.awt.Color(226, 233, 238, 235));
        g2.fillRoundRect(x + 1, cy - 3, 16, 12, 3, 3);
        g2.fillRoundRect(x + 1, cy - 6, 8, 4, 2, 2);
        g2.setColor(new java.awt.Color(255, 255, 255, 160));
        g2.drawLine(x + 3, cy + 1, x + 14, cy + 1);
    }

    private void drawBattery(java.awt.Graphics2D g2, int x, int y) {
        int cy = y + 18;
        g2.setColor(new java.awt.Color(240, 246, 250, 235));
        g2.drawRoundRect(x, cy - 6, 20, 12, 3, 3);
        g2.fillRect(x + 20, cy - 3, 3, 6);
        g2.setColor(new java.awt.Color(120, 220, 140, 230));
        g2.fillRoundRect(x + 2, cy - 4, 12, 8, 2, 2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new UI.ShadowLabel();
        divider = new UI.GradientDivider();
        subtitleLabel = new javax.swing.JLabel();
        professorLabel = new javax.swing.JLabel();
        memberTile1 = new UI.MemberTile();
        memberTile2 = new UI.MemberTile();
        memberTile3 = new UI.MemberTile();
        overlay = new UI.PassThroughPanel();

        titleLabel.setForeground(new java.awt.Color(225, 242, 237));
        titleLabel.setText("UNIVERSIDAD NACIONAL DE INGENIERÍA");
        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N

        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        subtitleLabel.setForeground(new java.awt.Color(175, 212, 206));
        subtitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitleLabel.setText("Algoritmización y Estructuras de Datos");

        professorLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        professorLabel.setForeground(new java.awt.Color(145, 182, 180));
        professorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        professorLabel.setText("MSc. Eliezer Aburto Plata");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(divider, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subtitleLabel)
                    .addComponent(professorLabel))
                .addGap(10, 10, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(memberTile1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(memberTile2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(memberTile3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, Short.MAX_VALUE))
            .addComponent(overlay, 0, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(divider, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(subtitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(professorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(memberTile1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(memberTile2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(memberTile3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
            .addComponent(overlay, 0, 600, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UI.GradientDivider divider;
    private UI.MemberTile memberTile1;
    private UI.MemberTile memberTile2;
    private UI.MemberTile memberTile3;
    private UI.PassThroughPanel overlay;
    private javax.swing.JLabel professorLabel;
    private javax.swing.JLabel subtitleLabel;
    private UI.ShadowLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}