package edu.cerp.checkin.ui;

import edu.cerp.checkin.logic.SesionService;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Clase que muestra la interfaz gráfica de Check In.
 */
public class CheckInGUI {

    /**
     * Muestra la ventana de Check In.
     * @param service Servicio que gestiona las inscripciones.
     */
    public static void show(SesionService service) {
        // Ventana principal
        JFrame ventana = new JFrame("Check In Aula");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(500, 400);
        ventana.setLayout(new BorderLayout());

        // Panel de fondo con degradado
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(70, 130, 180);   // azul oscuro
                Color color2 = new Color(176, 224, 230);  // azul claro
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        panelFondo.setLayout(new BorderLayout());
        ventana.setContentPane(panelFondo);

        // Panel de entradas con GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;

        // Etiquetas
        JLabel nombreL = new JLabel("Ingresa el nombre");
        JLabel documentoL = new JLabel("Ingresa el documento");
        JLabel cursoL = new JLabel("Selecciona el curso");
        JLabel fechaL = new JLabel("Ingresa la fecha");

        // Campos de texto
        JTextField nombre = new JTextField();
        JTextField documento = new JTextField();
        JTextField fecha = new JTextField();

        // Dropdown para curso
        String[] cursos = {"Prog 1", "Prog 2", "Base de Datos", "Redes", "Graduado"};
        JComboBox<String> comboCurso = new JComboBox<>(cursos);
        comboCurso.setPreferredSize(new Dimension(100, 22));
        comboCurso.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Mejoras visuales para JTextField
        Font fuenteCampos = new Font("Segoe UI", Font.PLAIN, 12);
        nombre.setFont(fuenteCampos);
        documento.setFont(fuenteCampos);
        fecha.setFont(fuenteCampos);

        nombre.setPreferredSize(new Dimension(100, 22));
        documento.setPreferredSize(new Dimension(100, 22));
        fecha.setPreferredSize(new Dimension(100, 22));

        Border bordeSuave = BorderFactory.createLineBorder(new Color(200, 200, 200), 1);
        nombre.setBorder(bordeSuave);
        documento.setBorder(bordeSuave);
        fecha.setBorder(bordeSuave);

        nombre.setBackground(new Color(245, 245, 245));
        documento.setBackground(new Color(245, 245, 245));
        fecha.setBackground(new Color(245, 245, 245));

        // Primera fila: nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(nombreL, gbc);
        gbc.gridx = 1;
        panel.add(nombre, gbc);

        // Segunda fila: documento
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(documentoL, gbc);
        gbc.gridx = 1;
        panel.add(documento, gbc);

        // Tercera fila: curso (dropdown)
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(cursoL, gbc);
        gbc.gridx = 1;
        panel.add(comboCurso, gbc);

        // Cuarta fila: fecha
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(fechaL, gbc);
        gbc.gridx = 1;
        panel.add(fecha, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setOpaque(false);

        // Botón Check In
        JButton btnCheckIn = new JButton("Check In");
        btnCheckIn.setPreferredSize(new Dimension(120, 40));
        btnCheckIn.setBackground(new Color(70, 130, 180));
        btnCheckIn.setForeground(Color.WHITE);
        btnCheckIn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCheckIn.setFocusPainted(false);
        btnCheckIn.setBorderPainted(false);
        btnCheckIn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover botón
        btnCheckIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCheckIn.setBackground(new Color(100, 149, 237));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCheckIn.setBackground(new Color(70, 130, 180));
            }
        });

        // Acción al presionar Check In
        btnCheckIn.addActionListener(e -> {
            String nombreValor = nombre.getText();
            String documentoValor = documento.getText();
            String cursoValor = (String) comboCurso.getSelectedItem();

            if(nombreValor.isBlank() || documentoValor.isBlank() || cursoValor.isBlank()) {
                JOptionPane.showMessageDialog(ventana, "Por favor completa todos los campos");
                return;
            }

            service.registrar(nombreValor, documentoValor, cursoValor);
            JOptionPane.showMessageDialog(ventana, "Check In registrado correctamente");

            nombre.setText("");
            documento.setText("");
            comboCurso.setSelectedIndex(0);
            fecha.setText("");
        });

        // Botón Cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.setBackground(new Color(220, 20, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelar.setBackground(new Color(255, 69, 90));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelar.setBackground(new Color(220, 20, 60));
            }
        });

        btnCancelar.addActionListener(e -> ventana.dispose());

        panelBotones.add(btnCheckIn);
        panelBotones.add(btnCancelar);

        panelFondo.add(panel, BorderLayout.CENTER);
        panelFondo.add(panelBotones, BorderLayout.SOUTH);

        ventana.setVisible(true);
    }
}

