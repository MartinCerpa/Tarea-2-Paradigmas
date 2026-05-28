package frontend;

import backend.GestorAplicacion;
import backend.Rutina;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class VentanaRutinasExistentes extends JFrame {

    private GestorAplicacion gestor;
    private JTable tablaRutinas;
    private DefaultTableModel modeloTabla;
    private JButton btnRevisar;

    public VentanaRutinasExistentes(GestorAplicacion gestor) {
        this.gestor = gestor;

        setTitle("Rutinas existentes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Rutinas generadas durante la ejecucion", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        String[] columnas = {"ID", "Cliente", "Semana", "Ejercicios", "Tiempo total (min)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaRutinas = new JTable(modeloTabla);
        tablaRutinas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaRutinas.getTableHeader().setReorderingAllowed(false);

        cargarDatos();

        btnRevisar = new JButton("Revisar rutina seleccionada");

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnRevisar);

        add(titulo, BorderLayout.NORTH);
        add(new JScrollPane(tablaRutinas), BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);

        btnRevisar.addActionListener(e -> revisarRutina());
    }

    private void cargarDatos() {
        Vector<Rutina> historial = gestor.getHistorialRutinas();

        if (historial.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No existen rutinas generadas durante esta ejecucion.",
                    "Sin rutinas",
                    JOptionPane.INFORMATION_MESSAGE
            );
            btnRevisar.setEnabled(false);
            return;
        }

        for (Rutina r : historial) {
            Object[] fila = {
                r.getIdRutina(),
                r.getCliente(),
                r.getSemana(),
                r.getCantidadTotal(),
                r.calcularTiempoTotal()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void revisarRutina() {
        int filaSeleccionada = tablaRutinas.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar una rutina de la tabla.",
                    "Seleccion requerida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int idRutina = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        try {
            gestor.seleccionarRutinaExistente(idRutina);
            VentanaRevision ventanaRevision = new VentanaRevision(gestor);
            ventanaRevision.setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al seleccionar rutina: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
