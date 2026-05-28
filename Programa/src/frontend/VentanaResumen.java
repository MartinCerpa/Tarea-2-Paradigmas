package frontend;

import backend.GestorAplicacion;
import backend.IntensidadEjercicio;
import backend.Rutina;
import backend.TipoEjercicio;

import javax.swing.*;
import java.awt.*;

public class VentanaResumen extends JFrame {

    private GestorAplicacion gestor;
    private Rutina rutina;

    public VentanaResumen(GestorAplicacion gestor) {
        this.gestor = gestor;
        this.rutina = gestor.getRutinaActual();

        setTitle("Resumen de la rutina");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Resumen de la rutina", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JTextArea areaResumen = new JTextArea();
        areaResumen.setEditable(false);
        areaResumen.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaResumen.setMargin(new Insets(10, 10, 10, 10));

        String texto = "";
        texto += "RESUMEN DE LA RUTINA GENERADA\n";
        texto += "==============================\n\n";

        texto += "Cantidad total de ejercicios: " + rutina.getCantidadTotal() + "\n\n";

        texto += "Cantidad por tipo:\n";
        texto += "  Cardiovascular: " + rutina.contarPorTipo(TipoEjercicio.CARDIOVASCULAR) + "\n";
        texto += "  Fuerza: " + rutina.contarPorTipo(TipoEjercicio.FUERZA) + "\n\n";

        texto += "Cantidad por intensidad:\n";
        texto += "  Basico: " + rutina.contarPorIntensidad(IntensidadEjercicio.BASICO) + "\n";
        texto += "  Intermedio: " + rutina.contarPorIntensidad(IntensidadEjercicio.INTERMEDIO) + "\n";
        texto += "  Avanzado: " + rutina.contarPorIntensidad(IntensidadEjercicio.AVANZADO) + "\n";
        texto += "  Alto rendimiento: " + rutina.contarPorIntensidad(IntensidadEjercicio.ALTO_RENDIMIENTO) + "\n\n";

        texto += "Tiempo total estimado: " + rutina.calcularTiempoTotal() + " minutos\n";

        areaResumen.setText(texto);

        JButton btnVolverMenu = new JButton("Volver al menu principal");
        JButton btnVerRutinas = new JButton("Ver rutinas existentes");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnVolverMenu);
        panelBotones.add(btnVerRutinas);

        add(titulo, BorderLayout.NORTH);
        add(new JScrollPane(areaResumen), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnVolverMenu.addActionListener(e -> this.dispose());

        btnVerRutinas.addActionListener(e -> {
            VentanaRutinasExistentes ventana = new VentanaRutinasExistentes(gestor);
            ventana.setVisible(true);
            this.dispose();
        });
    }
}
