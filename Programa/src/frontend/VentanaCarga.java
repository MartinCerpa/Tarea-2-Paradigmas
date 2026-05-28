package frontend;

import backend.GestorAplicacion;
import backend.IntensidadEjercicio;
import backend.ObservadorSistema;
import backend.TipoEjercicio;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class VentanaCarga extends JFrame implements ObservadorSistema {

    private GestorAplicacion gestor;
    private boolean usandoBD;
    private JTextArea areaInformacion;
    private JTextArea areaNotificaciones;
    private JButton btnGenerarRutina;

    public VentanaCarga(GestorAplicacion gestor, boolean usandoBD) {
        this.gestor = gestor;
        this.usandoBD = usandoBD;
        this.gestor.agregarObservador(this);

        String tituloTexto = usandoBD
                ? "Carga de ejercicios desde base de datos"
                : "Carga de ejercicios desde archivo CSV";

        setTitle(tituloTexto);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel(tituloTexto, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        areaInformacion = new JTextArea();
        areaInformacion.setEditable(false);
        areaInformacion.setFont(new Font("Monospaced", Font.PLAIN, 12));

        areaNotificaciones = new JTextArea(3, 50);
        areaNotificaciones.setEditable(false);
        areaNotificaciones.setForeground(new Color(0, 100, 0));
        areaNotificaciones.setFont(new Font("Arial", Font.ITALIC, 11));

        String btnTexto = usandoBD ? "Seleccionar archivo de base de datos" : "Seleccionar archivo CSV";
        JButton btnSeleccionarArchivo = new JButton(btnTexto);

        btnGenerarRutina = new JButton("Generar rutina");
        btnGenerarRutina.setEnabled(false);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnSeleccionarArchivo);
        panelBotones.add(btnGenerarRutina);

        add(titulo, BorderLayout.NORTH);
        add(new JScrollPane(areaInformacion), BorderLayout.CENTER);
        add(new JScrollPane(areaNotificaciones), BorderLayout.SOUTH);
        add(panelBotones, BorderLayout.PAGE_END);

        btnSeleccionarArchivo.addActionListener(e -> seleccionarArchivo());

        btnGenerarRutina.addActionListener(e -> {
            VentanaGeneracion ventanaGeneracion = new VentanaGeneracion(gestor);
            ventanaGeneracion.setVisible(true);
            this.dispose();
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                gestor.removerObservador(VentanaCarga.this);
            }
        });
    }

    private void seleccionarArchivo() {
        JFileChooser selector = new JFileChooser();

        if (usandoBD) {
            selector.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Base de datos SQLite (*.db)", "db"));
        } else {
            selector.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Archivo CSV (*.csv)", "csv"));
        }

        int resultado = selector.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();

            try {
                gestor.cargarEjercicios(archivo.getAbsolutePath(), usandoBD);
                mostrarResumenCarga();
                btnGenerarRutina.setEnabled(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al cargar: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void mostrarResumenCarga() {
        String texto = "";

        texto += "EJERCICIOS CARGADOS EXITOSAMENTE\n";
        texto += "================================\n\n";

        texto += "Cantidad total de ejercicios: " + gestor.getCantidadEjercicios() + "\n";
        texto += "Tiempo estimado total disponible: " + gestor.getTiempoTotalDisponible() + " minutos\n\n";

        texto += "Cantidad de ejercicios por tipo:\n";
        texto += "  Cardiovascular: " + gestor.contarEjerciciosPorTipo(TipoEjercicio.CARDIOVASCULAR) + "\n";
        texto += "  Fuerza: " + gestor.contarEjerciciosPorTipo(TipoEjercicio.FUERZA) + "\n\n";

        texto += "Cantidad de ejercicios por intensidad:\n";
        texto += "  Basico: " + gestor.contarEjerciciosPorIntensidad(IntensidadEjercicio.BASICO) + "\n";
        texto += "  Intermedio: " + gestor.contarEjerciciosPorIntensidad(IntensidadEjercicio.INTERMEDIO) + "\n";
        texto += "  Avanzado: " + gestor.contarEjerciciosPorIntensidad(IntensidadEjercicio.AVANZADO) + "\n";
        texto += "  Alto rendimiento: " + gestor.contarEjerciciosPorIntensidad(IntensidadEjercicio.ALTO_RENDIMIENTO) + "\n";

        areaInformacion.setText(texto);
    }

    @Override
    public void actualizar(String mensaje) {
        areaNotificaciones.append("> " + mensaje + "\n");
    }
}
