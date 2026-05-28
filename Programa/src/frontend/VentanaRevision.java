package frontend;

import backend.Ejercicio;
import backend.GestorAplicacion;
import backend.Rutina;

import javax.swing.*;
import java.awt.*;

public class VentanaRevision extends JFrame {

    private GestorAplicacion gestor;
    private Rutina rutina;
    private int posicionActual;

    private JLabel lblNombre;
    private JLabel lblTipo;
    private JLabel lblIntensidad;
    private JLabel lblTiempo;
    private JTextArea areaDescripcion;
    private JLabel lblContador;

    private JButton btnVolver;
    private JButton btnSiguiente;
    private JButton btnRutinasExistentes;

    public VentanaRevision(GestorAplicacion gestor) {
        this.gestor = gestor;
        this.rutina = gestor.getRutinaActual();
        this.posicionActual = 0;

        setTitle("Revision de rutina");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Revision de ejercicios", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));

        JPanel panelDatos = new JPanel(new GridLayout(4, 1, 5, 5));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Ejercicio actual"));

        lblNombre = new JLabel();
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblTipo = new JLabel();
        lblIntensidad = new JLabel();
        lblTiempo = new JLabel();

        panelDatos.add(lblNombre);
        panelDatos.add(lblTipo);
        panelDatos.add(lblIntensidad);
        panelDatos.add(lblTiempo);

        areaDescripcion = new JTextArea();
        areaDescripcion.setEditable(false);
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setWrapStyleWord(true);
        areaDescripcion.setBorder(BorderFactory.createTitledBorder("Descripcion"));
        areaDescripcion.setFont(new Font("Arial", Font.PLAIN, 13));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelDatos, new JScrollPane(areaDescripcion));
        splitPane.setResizeWeight(0.4);
        splitPane.setEnabled(false);

        panelCentral.add(splitPane, BorderLayout.CENTER);

        lblContador = new JLabel("", SwingConstants.CENTER);
        lblContador.setFont(new Font("Arial", Font.ITALIC, 12));
        panelCentral.add(lblContador, BorderLayout.SOUTH);

        btnVolver = new JButton("Volver");
        btnSiguiente = new JButton("Siguiente");
        btnRutinasExistentes = new JButton("Rutinas existentes");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.add(btnVolver);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnRutinasExistentes);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(panelBotones, BorderLayout.CENTER);

        add(titulo, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);

        btnVolver.addActionListener(e -> volver());
        btnSiguiente.addActionListener(e -> siguiente());
        btnRutinasExistentes.addActionListener(e -> abrirRutinasExistentes());

        if (rutina == null || rutina.getCantidadTotal() == 0) {
            mostrarRutinaVacia();
        } else {
            mostrarEjercicio();
        }
    }

    private void mostrarRutinaVacia() {
        lblNombre.setText("Nombre: ---");
        lblTipo.setText("Tipo: ---");
        lblIntensidad.setText("Intensidad: ---");
        lblTiempo.setText("Tiempo estimado: ---");
        areaDescripcion.setText("No hay ejercicios en esta rutina.");
        lblContador.setText("Ejercicio 0 de 0");
        btnVolver.setEnabled(false);
        btnSiguiente.setEnabled(false);
    }

    private void mostrarEjercicio() {
        if (rutina == null || rutina.getCantidadTotal() == 0) {
            mostrarRutinaVacia();
            return;
        }

        Ejercicio ejercicio = rutina.getEjercicios().get(posicionActual);

        lblNombre.setText("Nombre: " + ejercicio.getNombre());
        lblTipo.setText("Tipo: " + ejercicio.getTipo().getNombre());
        lblIntensidad.setText("Intensidad: " + ejercicio.getIntensidad().getNombre());
        lblTiempo.setText("Tiempo estimado: " + ejercicio.getTiempoEstimado() + " minutos");
        areaDescripcion.setText(ejercicio.getDescripcion());

        btnVolver.setEnabled(posicionActual > 0);
        btnSiguiente.setEnabled(true);

        if (posicionActual == rutina.getCantidadTotal() - 1) {
            btnSiguiente.setText("Resumen de la rutina");
        } else {
            btnSiguiente.setText("Siguiente");
        }

        lblContador.setText("Ejercicio " + (posicionActual + 1) + " de " + rutina.getCantidadTotal());
    }

    private void volver() {
        if (posicionActual > 0) {
            posicionActual--;
            mostrarEjercicio();
        }
    }

    private void siguiente() {
        if (posicionActual == rutina.getCantidadTotal() - 1) {
            persistirYCerrar();
            VentanaResumen ventanaResumen = new VentanaResumen(gestor);
            ventanaResumen.setVisible(true);
            this.dispose();
        } else {
            posicionActual++;
            mostrarEjercicio();
        }
    }

    private void abrirRutinasExistentes() {
        VentanaRutinasExistentes ventana = new VentanaRutinasExistentes(gestor);
        ventana.setVisible(true);
        this.dispose();
    }

    private void persistirYCerrar() {
        try {
            gestor.guardarUltimaSemanaUso();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Advertencia: No se pudo guardar el estado: " + ex.getMessage(),
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}
