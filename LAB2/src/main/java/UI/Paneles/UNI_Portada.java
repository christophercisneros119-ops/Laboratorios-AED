package UI.Paneles;

import UI.Elementos.UIConstants;
import static UI.Elementos.UIConstants.*;
import java.awt.Color;
import java.awt.Font;

public class UNI_Portada extends javax.swing.JPanel {

    private static final int SIDEBAR_W = UI.Elementos.SidebarPanel.SIDEBAR_WIDTH;

    private java.awt.Image fondoImage;

    private final UI.Elementos.TaskbarMenuButton menuButton = new UI.Elementos.TaskbarMenuButton();
    private final UI.Elementos.SidebarPanel sidebar = new UI.Elementos.SidebarPanel();
    private final UI.Elementos.DesktopDateWidget dateWidget = new UI.Elementos.DesktopDateWidget();
    private final UI.Elementos.AboutWindow aboutWindow = new UI.Elementos.AboutWindow();

    private boolean sidebarOpen;
    private int sidebarX;
    private int sidebarTarget;
    private javax.swing.Timer sidebarTimer;
    private Runnable onSlideDone;

    public UNI_Portada() {
        initComponents();
        setPreferredSize(new java.awt.Dimension(800, 600));
        setOpaque(false);

        fondoImage = new javax.swing.ImageIcon(getClass().getResource("/img/fondo.jpg")).getImage();

        memberTile1.setFullName("Janelly Romero");
        memberTile1.setCarnetNumber("2025-1905U");
        memberTile1.setPhoto(new javax.swing.ImageIcon(getClass().getResource("/img/Cards/J.jpg")).getImage());
        memberTile2.setFullName("Moises Alemán");
        memberTile2.setCarnetNumber("2025-2560U");
        memberTile2.setPhoto(new javax.swing.ImageIcon(getClass().getResource("/img/Cards/M.jpg")).getImage());
        memberTile3.setFullName("Christopher Cisneros");
        memberTile3.setCarnetNumber("2025-0032U");
        memberTile3.setPhoto(new javax.swing.ImageIcon(getClass().getResource("/img/Cards/C.jpg")).getImage());

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
        overlay.add(aboutWindow);
        aboutWindow.setVisible(false);
        setComponentZOrder(aboutWindow, 0);
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
        sidebar.getArreglosButton().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                navegarA(new UI.Paneles.P_MenuArreglos());
            }
        });
        sidebar.getAboutButton().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dateWidget.setVisible(false);
                aboutWindow.setVisible(true);
            }
        });
        aboutWindow.getRegresarButton().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                aboutWindow.setVisible(false);
                dateWidget.setVisible(true);
            }
        });
    }

    private void navegarA(javax.swing.JPanel destino) {
        javax.swing.JFrame ventana = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (ventana != null) {
            if (sidebarTimer != null && sidebarTimer.isRunning()) {
                sidebarTimer.stop();
            }
            ventana.setContentPane(destino);
            ventana.revalidate();
            ventana.repaint();
        }
    }

    private void openSidebar() {
        if (sidebarOpen) return;
        sidebarOpen = true;
        menuButton.setVisible(false);
        startSlide(getWidth() - SIDEBAR_W, null);
    }

    private void closeSidebar() {
        if (!sidebarOpen) return;
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
            if (onSlideDone != null) onSlideDone.run();
        } else {
            sidebarX += (int) Math.signum(dx) * 12;
            placeSidebar();
        }
    }

    private void placeSidebar() {
        sidebar.setBounds(sidebarX, 0, SIDEBAR_W, getHeight());
        sidebar.repaint();
    }

    private void reposition() {
        int w = getWidth();
        int h = getHeight();
        menuButton.setBounds(w - 36, h - 31, 26, 26);
        dateWidget.setBounds((w - 148) / 2, 196, 148, 104);
        aboutWindow.setBounds((w - UI.Elementos.AboutWindow.ABOUT_W) / 2 - 25,
                (h - UI.Elementos.AboutWindow.ABOUT_H) / 2,
                UI.Elementos.AboutWindow.ABOUT_W, UI.Elementos.AboutWindow.ABOUT_H);
        sidebarX = sidebarOpen ? w - SIDEBAR_W : w;
        placeSidebar();
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        drawBackground(g2, w, h);
        paintTaskbar(g2, w, h);

        g2.dispose();
    }

    private void drawBackground(java.awt.Graphics2D g2, int w, int h) {
        int iw = fondoImage.getWidth(null);
        int ih = fondoImage.getHeight(null);
        double scale = Math.max(w / (double) iw, h / (double) ih);
        int dw = (int) (iw * scale);
        int dh = (int) (ih * scale);
        g2.drawImage(fondoImage, (w - dw) / 2, (h - dh) / 2, dw, dh, null);

        java.awt.LinearGradientPaint veil = new java.awt.LinearGradientPaint(
                0, 0, 0, h, new float[]{0f, 0.6f, 1f},
                new Color[]{VEIL_TOP, VEIL_MID, VEIL_BOTTOM});
        g2.setPaint(veil);
        g2.fillRect(0, 0, w, h);

        g2.setColor(new Color(10, 28, 34, 110));
        g2.fillRoundRect(28, 26, 520, 152, 30, 30);
    }

    private void paintTaskbar(java.awt.Graphics2D g2, int w, int h) {
        Color cloud = new Color(240, 246, 250, 235);
        Color whiteTaskbar = new Color(255, 255, 255, 235);

        int th = 36;
        int y = h - th;

        Color taskbarFill = new Color(48, 52, 56, 205);
        Color taskbarShineStart = new Color(212, 218, 224, 200);
        Color taskbarShineEnd = new Color(212, 218, 224, 20);

        g2.setColor(taskbarFill);
        g2.fillRoundRect(0, y, w, th, 10, 10);
        g2.setPaint(new java.awt.LinearGradientPaint(
                0, y, 0, y + 6, new float[]{0f, 1f},
                new Color[]{taskbarShineStart, taskbarShineEnd}));
        g2.fillRect(0, y, w, 6);

        int cy = y + 18;
        int x = 14;

        // Sun icon
        g2.setColor(new Color(255, 224, 130, 235));
        g2.fillOval(x + 4, cy - 11, 10, 10);
        g2.setColor(cloud);
        g2.fillOval(x, cy - 4, 15, 8);
        g2.fillOval(x + 7, cy - 8, 11, 9);
        g2.setColor(new Color(255, 255, 255, 240));
        g2.setFont(FONT_TASKBAR);
        g2.drawString("90°F", x + 22, cy + 4);
        x += 86;

        // Search bar
        int sy = y + 8;
        g2.setColor(whiteTaskbar);
        g2.fillRoundRect(x, sy, 140, 20, 20, 20);
        g2.setColor(new Color(174, 194, 205));
        g2.setFont(FONT_TASKBAR);
        g2.drawString("Search...", x + 8, sy + 15);
        g2.setColor(new Color(150, 172, 184));
        g2.drawOval(x + 123, sy + 4, 9, 9);
        g2.drawLine(x + 130, sy + 12, x + 135, sy + 17);
        x += 156;

        // Wi-Fi icon
        g2.setPaint(new java.awt.RadialGradientPaint(
                new java.awt.geom.Point2D.Double(x + 6, cy - 6), 12f,
                new float[]{0f, 0.7f, 1f},
                new Color[]{new Color(255, 255, 255, 210),
                        new Color(120, 200, 230),
                        new Color(40, 120, 170)}));
        g2.fillOval(x, cy - 8, 16, 16);
        g2.setColor(new Color(255, 255, 255, 170));
        g2.drawOval(x + 4, cy - 8, 8, 16);
        g2.drawOval(x, cy - 8, 16, 16);
        g2.drawLine(x + 8, cy - 8, x + 8, cy + 8);
        x += 28;

        // Bluetooth-like icon
        g2.setColor(cloud);
        g2.fillOval(x + 2, cy + 2, 7, 5);
        g2.setStroke(new java.awt.BasicStroke(2f));
        g2.drawLine(x + 9, cy + 6, x + 9, cy - 6);
        g2.drawLine(x + 9, cy - 6, x + 14, cy - 2);
        g2.setStroke(new java.awt.BasicStroke(1f));
        x += 28;

        // Email icon
        g2.setPaint(new java.awt.RadialGradientPaint(
                new java.awt.geom.Point2D.Double(x + 6, cy - 6), 12f,
                new float[]{0f, 0.6f, 1f},
                new Color[]{new Color(255, 255, 255, 220),
                        new Color(90, 160, 230),
                        new Color(30, 90, 170)}));
        g2.fillOval(x, cy - 8, 16, 16);
        g2.setColor(new Color(255, 255, 255, 200));
        g2.setStroke(new java.awt.BasicStroke(1.2f));
        g2.drawOval(x + 1, cy - 7, 14, 14);
        java.awt.font.TextLayout tl = new java.awt.font.TextLayout("e",
                new Font("Segoe UI", Font.BOLD, 11), g2.getFontRenderContext());
        java.awt.geom.Rectangle2D erb = tl.getBounds();
        g2.setColor(Color.WHITE);
        tl.draw(g2, (float) (x + 8 - erb.getCenterX()), (float) (cy - erb.getCenterY()));
        g2.setStroke(new java.awt.BasicStroke(1f));
        x += 28;

        // Document icon
        g2.setColor(new Color(226, 233, 238, 235));
        g2.fillRoundRect(x + 1, cy - 3, 16, 12, 3, 3);
        g2.fillRoundRect(x + 1, cy - 6, 8, 4, 2, 2);
        g2.setColor(new Color(255, 255, 255, 160));
        g2.drawLine(x + 3, cy + 1, x + 14, cy + 1);

        // Battery + clock
        int batX = menuButton.getX() - 35;
        g2.setColor(cloud);
        g2.drawRoundRect(batX, cy - 6, 20, 12, 3, 3);
        g2.fillRect(batX + 20, cy - 3, 3, 6);
        g2.setColor(new Color(120, 220, 140, 230));
        g2.fillRoundRect(batX + 2, cy - 4, 12, 8, 2, 2);
        String hora = "1:17 pm";
        g2.setFont(FONT_TASKBAR);
        java.awt.FontMetrics fm = g2.getFontMetrics();
        g2.setColor(whiteTaskbar);
        g2.drawString(hora, batX - 12 - fm.stringWidth(hora), y + 24);
    }

    private void initComponents() {

        titleLabel = new UI.Elementos.ShadowLabel();
        divider = new UI.Elementos.GradientDivider();
        subtitleLabel = new javax.swing.JLabel();
        professorLabel = new javax.swing.JLabel();
        memberTile1 = new UI.Elementos.MemberTile();
        memberTile2 = new UI.Elementos.MemberTile();
        memberTile3 = new UI.Elementos.MemberTile();
        overlay = new UI.Elementos.PassThroughPanel();

        titleLabel.setForeground(new java.awt.Color(225, 242, 237));
        titleLabel.setText("UNIVERSIDAD NACIONAL DE INGENIERÍA");
        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 30));

        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 17));
        subtitleLabel.setForeground(new java.awt.Color(175, 212, 206));
        subtitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitleLabel.setText("Algoritmización y Estructuras de Datos");

        professorLabel.setFont(FONT_BODY);
        professorLabel.setForeground(new java.awt.Color(145, 182, 180));
        professorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        professorLabel.setText("MSc. Eliezer Aburto Plata");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(overlay, 0, 800, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(memberTile1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(memberTile2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(memberTile3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(divider, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subtitleLabel)
                    .addComponent(professorLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    }

    private UI.Elementos.GradientDivider divider;
    private UI.Elementos.MemberTile memberTile1;
    private UI.Elementos.MemberTile memberTile2;
    private UI.Elementos.MemberTile memberTile3;
    private UI.Elementos.PassThroughPanel overlay;
    private javax.swing.JLabel professorLabel;
    private javax.swing.JLabel subtitleLabel;
    private UI.Elementos.ShadowLabel titleLabel;
}
