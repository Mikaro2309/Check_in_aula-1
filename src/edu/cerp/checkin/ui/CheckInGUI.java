package edu.cerp.checkin.ui;

import edu.cerp.checkin.logic.SesionService;

import javax.swing.*;
import java.awt.*;

public class CheckInGUI {

    public static void show(SesionService service) {
        //Ventana
        JFrame ventana = new JFrame("Ventana");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(500, 400);
        ventana.setLayout(new BorderLayout());

        //Panel
        JPanel panel = new JPanel(new GridLayout(3,2));

        //Etiquetas
        JLabel nombreL = new JLabel("Ingresa el nombre");
        JLabel documentoL = new JLabel("Ingresa el documento");
        JLabel cursoL = new JLabel("Ingresa el curso");

        //Imputs
        JTextField nombre = new JTextField();
        JTextField documento = new JTextField();
        JTextField curso = new JTextField();

        panel.add(nombreL);
        panel.add(nombre);
        panel.add(documentoL);
        panel.add(documento);
        panel.add(cursoL);
        panel.add(curso);

        ventana.add(panel);

        //---Comenzamos con la conexion del GUI con el sistema pre establecido---

        //Panel de Botones, creado y añadido
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton registrarBtn = new JButton("Registrar");
        JButton listarBtn = new JButton("Listar");
        JButton buscarBtn = new JButton("Buscar");
        JButton resumenBtn = new JButton("Resumen");

        botones.add(registrarBtn);
        botones.add(listarBtn);
        botones.add(buscarBtn);
        botones.add(resumenBtn);

        ventana.add(botones, BorderLayout.SOUTH);

        //Funcionalidad del boton registrar
        registrarBtn.addActionListener(e -> {
            service.registrar(nombre.getText(), documento.getText(), curso.getText());

            JOptionPane.showMessageDialog(ventana, "✔ Inscripción registrada");

            nombre.setText("");
            documento.setText("");
            curso.setText("");
        });

        // Funcionalidad del boton Listar
        listarBtn.addActionListener(e -> {
            mostrarTabla(service.listar());
        });

        // Buscar
        buscarBtn.addActionListener(e -> {
            String q = JOptionPane.showInputDialog("Texto a buscar:");
            mostrarTabla(service.buscar(q));
        });

        // Resumen
        resumenBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(ventana, service.resumen());
        });

        ventana.setVisible(true);
    }

    //Se crea un motodo "Mostrar tabla" para lidiar con el listado, y busqueda de forma mas limpia
    private static void mostrarTabla(java.util.List<edu.cerp.checkin.model.Inscripcion> lista) {
        JFrame frame = new JFrame("Resultados");
        frame.setSize(500, 300);

        String[] columnas = {"Nombre", "Documento", "Curso", "Fecha/Hora"};
        String[][] datos = new String[lista.size()][4];

        for (int i = 0; i < lista.size(); i++) {
            var ins = lista.get(i);
            datos[i][0] = ins.getNombre();
            datos[i][1] = ins.getDocumento();
            datos[i][2] = ins.getCurso();
            datos[i][3] = ins.getFechaHora().toString();
        }

        JTable tabla = new JTable(datos, columnas);
        frame.add(new JScrollPane(tabla));

        frame.setVisible(true);
    }


}
