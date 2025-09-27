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
        JPanel panel = new JPanel(new GridLayout(4,2));

        //Etiquetas
        JLabel nombreL = new JLabel("Ingresa el nombre");
        JLabel documentoL = new JLabel("Ingresa el documento");
        JLabel cursoL = new JLabel("Ingresa el curso");
        JLabel fechaL = new JLabel("Ingresa la fecha");

        //Imputs
        JTextField nombre = new JTextField();
        JTextField documento = new JTextField();
        JTextField curso = new JTextField();
        JTextField fecha = new JTextField();

        panel.add(nombreL);
        panel.add(nombre);
        panel.add(documentoL);
        panel.add(documento);
        panel.add(cursoL);
        panel.add(curso);
        panel.add(fechaL);
        panel.add(fecha);

        ventana.add(panel);

        ventana.setVisible(true);
    }
}
