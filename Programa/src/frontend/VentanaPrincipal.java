package frontend;

import backend.GestorAplicacion;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private GestorAplicacion gestor;

    public VentanaPrincipal() {
        gestor = new GestorAplicacion();

        setTitle("Sistema de Rutinas Personalizadas");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Sistema de Rutinas de Entrenamiento", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel panelCentral = new JPanel(new GridBagLayout());
        JLabel instruccion = new JLabel("Seleccione una opcion para comenzar:");
        instruccion.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton btnCargarCSV = new JButton("Cargar ejercicios desde archivo CSV");
        JButton btnCargarBD = new JButton("Cargar ejercicios desde base de datos");
        JButton btnCrearBD = new JButton("Crear base de datos de ejercicios");
        JButton btnRutinasExistentes = new JButton("Ver rutinas existentes");

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panelBotones.add(btnCargarCSV);
        panelBotones.add(btnCargarBD);
        panelBotones.add(btnCrearBD);
        panelBotones.add(btnRutinasExistentes);

        panelCentral.add(instruccion, new GridBagConstraints());
        panelCentral.add(panelBotones, new GridBagConstraints());

        add(titulo, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);

        btnCargarCSV.addActionListener(e -> {
            VentanaCarga ventanaCarga = new VentanaCarga(gestor, false);
            ventanaCarga.setVisible(true);
        });

        btnCargarBD.addActionListener(e -> {
            VentanaCarga ventanaCarga = new VentanaCarga(gestor, true);
            ventanaCarga.setVisible(true);
        });

        btnCrearBD.addActionListener(e -> crearBaseDatos());

        btnRutinasExistentes.addActionListener(e -> verRutinasExistentes());

        JLabel lblNotificaciones = new JLabel(" ");
        lblNotificaciones.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblNotificaciones, BorderLayout.SOUTH);
    }

    private void verRutinasExistentes() {
        if (!gestor.hayRutinasGeneradas()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No existen rutinas generadas durante esta ejecucion.",
                    "Sin rutinas",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        VentanaRutinasExistentes ventana = new VentanaRutinasExistentes(gestor);
        ventana.setVisible(true);
    }

    private void crearBaseDatos() {
        JFileChooser selector = new JFileChooser();
        selector.setSelectedFile(new java.io.File("ejercicios.db"));
        int resultado = selector.showSaveDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = selector.getSelectedFile().getAbsolutePath();
            try {
                backend.CreadorBD.crearBaseDatos(ruta);
                JOptionPane.showMessageDialog(this,
                        "Base de datos creada exitosamente en:\n" + ruta,
                        "Exito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al crear base de datos: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
