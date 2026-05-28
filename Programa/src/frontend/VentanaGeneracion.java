package frontend;

import backend.GestorAplicacion;
import backend.IntensidadEjercicio;

import javax.swing.*;
import java.awt.*;

public class VentanaGeneracion extends JFrame {

    private GestorAplicacion gestor;

    private JTextField txtCantidadCardio;
    private JTextField txtCantidadFuerza;
    private JTextField txtSemanaActual;
    private JTextField txtCliente;
    private JLabel lblNotificacion;

    private JComboBox<IntensidadEjercicio> comboIntensidadCardio;
    private JComboBox<IntensidadEjercicio> comboIntensidadFuerza;

    public VentanaGeneracion(GestorAplicacion gestor) {
        this.gestor = gestor;

        setTitle("Generacion de rutina");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Generar rutina de entrenamiento", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));

        txtCantidadCardio = new JTextField();
        txtCantidadFuerza = new JTextField();
        txtSemanaActual = new JTextField();
        txtCliente = new JTextField();

        comboIntensidadCardio = new JComboBox<>(IntensidadEjercicio.values());
        comboIntensidadFuerza = new JComboBox<>(IntensidadEjercicio.values());

        panelFormulario.add(new JLabel("Nombre del cliente:"));
        panelFormulario.add(txtCliente);

        panelFormulario.add(new JLabel("Cantidad ejercicios cardiovasculares:"));
        panelFormulario.add(txtCantidadCardio);

        panelFormulario.add(new JLabel("Intensidad cardiovascular:"));
        panelFormulario.add(comboIntensidadCardio);

        panelFormulario.add(new JLabel("Cantidad ejercicios de fuerza:"));
        panelFormulario.add(txtCantidadFuerza);

        panelFormulario.add(new JLabel("Intensidad fuerza:"));
        panelFormulario.add(comboIntensidadFuerza);

        panelFormulario.add(new JLabel("Semana actual:"));
        panelFormulario.add(txtSemanaActual);

        JButton btnGenerar = new JButton("Generar rutina");

        lblNotificacion = new JLabel(" ", SwingConstants.CENTER);
        lblNotificacion.setFont(new Font("Arial", Font.ITALIC, 11));

        add(titulo, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.CENTER);
        add(lblNotificacion, BorderLayout.SOUTH);
        add(btnGenerar, BorderLayout.PAGE_END);

        btnGenerar.addActionListener(e -> generarRutina());
    }

    private void generarRutina() {
        try {
            String textoCardio = txtCantidadCardio.getText().trim();
            String textoFuerza = txtCantidadFuerza.getText().trim();
            String textoSemana = txtSemanaActual.getText().trim();
            String cliente = txtCliente.getText().trim();

            if (textoCardio.isEmpty() || textoFuerza.isEmpty() || textoSemana.isEmpty()) {
                throw new Exception("Todos los campos deben estar completos.");
            }

            int cantidadCardio = Integer.parseInt(textoCardio);
            int cantidadFuerza = Integer.parseInt(textoFuerza);
            int semanaActual = Integer.parseInt(textoSemana);

            if (cantidadCardio < 1 || cantidadFuerza < 1) {
                throw new Exception("Las cantidades deben ser mayores a 0.");
            }

            if (semanaActual < 1) {
                throw new Exception("La semana debe ser mayor a 0.");
            }

            IntensidadEjercicio intensidadCardio = (IntensidadEjercicio) comboIntensidadCardio.getSelectedItem();
            IntensidadEjercicio intensidadFuerza = (IntensidadEjercicio) comboIntensidadFuerza.getSelectedItem();

            if (cliente.isEmpty()) {
                cliente = "Cliente general";
            }

            gestor.generarRutina(
                    cantidadCardio,
                    intensidadCardio,
                    cantidadFuerza,
                    intensidadFuerza,
                    semanaActual,
                    cliente
            );

            lblNotificacion.setText("Rutina generada correctamente.");

            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "Rutina generada correctamente.\n¿Desea revisar la rutina?",
                    "Exito",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (respuesta == JOptionPane.YES_OPTION) {
                VentanaRevision ventanaRevision = new VentanaRevision(gestor);
                ventanaRevision.setVisible(true);
                this.dispose();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar numeros enteros validos en los campos numericos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al generar rutina: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
