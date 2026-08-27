package UI.Paneles;

import Algoritmos.Burbuja;
import Algoritmos.BusquedaBinaria;
import Algoritmos.IMetodoOrdenamiento;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

public class P_BusquedaBinaria extends javax.swing.JPanel {

    private int tamano;
    private boolean tamanoDefinido;
    private int[] arreglo;
    private java.awt.Image fondoImage;

    public P_BusquedaBinaria() {
        setOpaque(false);
        fondoImage = loadFondo();
        initComponents();
        jTextArea1.setEditable(false);
        jTextArea2.setEditable(false);
        jTextArea1.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        jTextArea2.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        jTextArea1.setBackground(new java.awt.Color(30, 46, 46));
        jTextArea2.setBackground(new java.awt.Color(51, 51, 51));
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new UI.Elementos.GlassButton("Guardar", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton2 = new UI.Elementos.GlassButton("Guardar", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton3 = new UI.Elementos.GlassButton("Buscar", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton4 = new UI.Elementos.GlassButton("Atr\u00e1s", new java.awt.Color(0, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(0, 51, 51));

        jLabel1.setFont(new java.awt.Font("Swis721 Blk BT", 0, 24));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Búsqueda Binaria");

        jLabel2.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tama\u00f1o del arreglo:");

        jLabel3.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Elementos (separados por coma):");

        jLabel4.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Elemento a buscar:");

        jLabel5.setFont(new java.awt.Font("Swis721 Blk BT", 0, 16));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Arreglo (ordenado)");

        jLabel6.setFont(new java.awt.Font("Swis721 Blk BT", 0, 16));
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Resultado");

        jTextField1.setBackground(new java.awt.Color(51, 51, 51));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));

        jTextField2.setBackground(new java.awt.Color(51, 51, 51));
        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));

        jTextField3.setBackground(new java.awt.Color(51, 51, 51));
        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextField3.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(0, 51, 51));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 51, 51));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 51, 51));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 51, 51));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Atr\u00e1s");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(6);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(30);
        jTextArea2.setRows(3);
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
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
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
        jTextArea2.setText("Arreglo cargado. Presiona Buscar.");
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        if (!tamanoDefinido) {
            JOptionPane.showMessageDialog(this, "Primero define el tamaño del arreglo.");
            return;
        }
        if (arreglo == null) {
            JOptionPane.showMessageDialog(this, "Primero ingresa los elementos del arreglo.");
            return;
        }
        String texto = jTextField3.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el elemento a buscar.");
            return;
        }
        if (!esNumeroEntero(texto)) {
            JOptionPane.showMessageDialog(this, "El elemento a buscar debe ser un valor numérico entero.");
            return;
        }
        int x = Integer.parseInt(texto);

        int[] ordenado = arreglo.clone();
        new Burbuja().ordenar(ordenado);
        int posicion = BusquedaBinaria.buscar(ordenado, x);

        jTextArea1.setText(IMetodoOrdenamiento.formatear(ordenado));
        if (posicion >= 0) {
            jTextArea2.setText("El elemento " + x + " se encuentra en la posición " + (posicion + 1));
        } else {
            jTextArea2.setText("El elemento " + x + " no se encuentra en el arreglo");
        }
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JFrame ventana = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        if (ventana != null) {
            ventana.setContentPane(new P_MenuArreglos());
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

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
}