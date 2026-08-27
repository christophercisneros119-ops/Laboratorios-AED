package UI.Paneles;

import Arreglosdesordenados.Ejercicio3;
import javax.swing.*;

public class P_Ejercicio3 extends javax.swing.JPanel {

    private final Ejercicio3 backend;

    public P_Ejercicio3() {
        initComponents();
        backend = new Ejercicio3(10);
        jTextArea1.setEditable(false);
        limpiarCampos();
    }

    private void limpiarCampos() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new UI.Elementos.GlassButton("Dar de alta", new java.awt.Color(51, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton2 = new UI.Elementos.GlassButton("Dar de baja", new java.awt.Color(51, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton3 = new UI.Elementos.GlassButton("Actualizar edad", new java.awt.Color(51, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton4 = new UI.Elementos.GlassButton("Imprimir registro de empleados varones", new java.awt.Color(51, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton5 = new UI.Elementos.GlassButton("Salir", new java.awt.Color(51, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton6 = new UI.Elementos.GlassButton("Imprimir registro", new java.awt.Color(51, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jButton7 = new UI.Elementos.GlassButton("Atr\u00e1s", new java.awt.Color(51, 51, 51), java.awt.Color.WHITE, java.awt.Color.WHITE);
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(0, 51, 51));

        jLabel1.setFont(new java.awt.Font("Swis721 Blk BT", 0, 24));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Ejercicio #3");

        jLabel2.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Sexo:");

        jLabel4.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Edad:");

        jTextField1.setBackground(new java.awt.Color(51, 51, 51));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));

        jTextField2.setBackground(new java.awt.Color(51, 51, 51));
        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));

        jTextField3.setBackground(new java.awt.Color(51, 51, 51));
        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextField3.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Dar de alta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Dar de baja");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Actualizar edad");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(51, 51, 51));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Imprimir registro de empleados varones");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(51, 51, 51));
        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Salir");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(51, 51, 51));
        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Imprimir registro");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(51, 51, 51));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Atr\u00e1s");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton5))
                .addGap(15, 15, 15))
        );
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String nombre = jTextField1.getText().trim();
        String sexoText = jTextField2.getText().trim();
        String edadText = jTextField3.getText().trim();

        if (nombre.isEmpty() || sexoText.isEmpty() || edadText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        try {
            char sexo = sexoText.charAt(0);
            int edad = Integer.parseInt(edadText);

            if (backend.insertaDesordenado(nombre, sexo, edad)) {
                JOptionPane.showMessageDialog(this, "Empleado registrado con \u00e9xito.");
                jTextArea1.setText("");
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingresa un valor num\u00e9rico v\u00e1lido para la edad.");
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        String nombre = jTextField1.getText().trim();
        String edadText = jTextField3.getText().trim();

        if (nombre.isEmpty() || edadText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el nombre y la nueva edad.");
            return;
        }

        try {
            int nuevaEdad = Integer.parseInt(edadText);

            if (backend.modificaEdadDesordenado(nombre, nuevaEdad)) {
                JOptionPane.showMessageDialog(this, "Edad actualizada con \u00e9xito.");
                jTextArea1.setText("");
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingresa un n\u00famero v\u00e1lido para la edad.");
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        String nombre = jTextField1.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el nombre del empleado a eliminar en el campo Nombre.");
            return;
        }

        if (backend.eliminaDesordenado(nombre)) {
            JOptionPane.showMessageDialog(this, "Empleado eliminado con \u00e9xito.");
            jTextArea1.setText("");
            limpiarCampos();
        }
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        jTextArea1.setText(backend.imprimirVarones());
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
        String nombre = jTextField1.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el nombre del empleado para consultar.");
            return;
        }

        jTextArea1.setText(backend.imprimirRegistro(nombre));
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JFrame ventana = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        if (ventana != null) {
            ventana.setContentPane(new P_MenuDesordenados());
            ventana.revalidate();
            ventana.repaint();
        }
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
}
