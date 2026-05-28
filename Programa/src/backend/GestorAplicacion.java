package backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GestorAplicacion {

    private List<Ejercicio> ejercicios;
    private Rutina rutinaActual;
    private List<ObservadorSistema> observadores;
    private String rutaOrigen;
    private boolean usandoBD;
    private Vector<Rutina> historialRutinas;
    private int contadorRutinas;

    public GestorAplicacion() {
        ejercicios = new ArrayList<>();
        rutinaActual = new Rutina();
        observadores = new ArrayList<>();
        rutaOrigen = "";
        usandoBD = false;
        historialRutinas = new Vector<>();
        contadorRutinas = 0;
    }

    public void agregarObservador(ObservadorSistema observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void removerObservador(ObservadorSistema observador) {
        observadores.remove(observador);
    }

    public void notificar(String mensaje) {
        for (ObservadorSistema observador : observadores) {
            observador.actualizar(mensaje);
        }
    }

    public void cargarEjercicios(String rutaArchivo, boolean esBD) throws Exception {
        FuenteDatos fuente;
        if (esBD) {
            fuente = new CargadorBD();
        } else {
            fuente = new CargadorEjercicios();
        }

        ejercicios = fuente.cargarEjercicios(rutaArchivo);
        this.rutaOrigen = rutaArchivo;
        this.usandoBD = esBD;
        notificar("Ejercicios cargados correctamente desde " + (esBD ? "base de datos" : "archivo") + ".");
    }

    public void guardarUltimaSemanaUso() throws Exception {
        if (rutaOrigen.isEmpty()) {
            return;
        }

        if (usandoBD) {
            guardarEnBD();
        } else {
            guardarEnCSV();
        }
    }

    private void guardarEnBD() throws Exception {
        java.sql.Connection conexion = java.sql.DriverManager.getConnection("jdbc:sqlite:" + rutaOrigen);
        java.sql.Statement stmt = conexion.createStatement();

        for (Ejercicio e : ejercicios) {
            stmt.execute("UPDATE ejercicios SET ultimaSemanaUso = " + e.getUltimaSemanaUso()
                    + " WHERE codigo = '" + e.getCodigo() + "'");
        }

        stmt.close();
        conexion.close();
    }

    private void guardarEnCSV() throws Exception {
        try (java.io.PrintWriter pw = new java.io.PrintWriter(rutaOrigen)) {
            pw.println("codigo;nombre;tipo;intensidad;tiempo;descripcion;ultimaSemanaUso");
            for (Ejercicio e : ejercicios) {
                pw.println(e.toString());
            }
        }
    }

    public void generarRutina(
            int cantidadCardio,
            IntensidadEjercicio intensidadCardio,
            int cantidadFuerza,
            IntensidadEjercicio intensidadFuerza,
            int semanaActual,
            String cliente
    ) throws Exception {

        if (ejercicios.isEmpty()) {
            throw new Exception("Primero debe cargar ejercicios.");
        }

        if (semanaActual <= 0) {
            throw new Exception("La semana actual debe ser mayor a 0.");
        }

        GeneradorRutina generador = new GeneradorRutina();

        contadorRutinas++;
        Rutina nuevaRutina = generador.generarRutina(
                ejercicios,
                cantidadCardio,
                intensidadCardio,
                cantidadFuerza,
                intensidadFuerza,
                semanaActual,
                cliente
        );

        nuevaRutina.setIdRutina(contadorRutinas);
        rutinaActual = nuevaRutina;
        historialRutinas.add(nuevaRutina);
        notificar("Rutina #" + contadorRutinas + " agregada al historial.");
    }

    public Vector<Rutina> getHistorialRutinas() {
        return historialRutinas;
    }

    public boolean hayRutinasGeneradas() {
        return !historialRutinas.isEmpty();
    }

    public Rutina buscarRutinaPorId(int idRutina) {
        for (Rutina r : historialRutinas) {
            if (r.getIdRutina() == idRutina) {
                return r;
            }
        }
        return null;
    }

    public void seleccionarRutinaExistente(int idRutina) throws Exception {
        Rutina encontrada = buscarRutinaPorId(idRutina);
        if (encontrada == null) {
            throw new Exception("La rutina con ID " + idRutina + " no existe.");
        }
        rutinaActual = encontrada;
        notificar("Rutina existente seleccionada (ID: " + idRutina + ").");
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public Rutina getRutinaActual() {
        return rutinaActual;
    }

    public int getCantidadEjercicios() {
        return ejercicios.size();
    }

    public int getTiempoTotalDisponible() {
        int total = 0;

        for (Ejercicio ejercicio : ejercicios) {
            total += ejercicio.getTiempoEstimado();
        }

        return total;
    }

    public int contarEjerciciosPorTipo(TipoEjercicio tipo) {
        int contador = 0;

        for (Ejercicio ejercicio : ejercicios) {
            if (ejercicio.getTipo() == tipo) {
                contador++;
            }
        }

        return contador;
    }

    public int contarEjerciciosPorIntensidad(IntensidadEjercicio intensidad) {
        int contador = 0;

        for (Ejercicio ejercicio : ejercicios) {
            if (ejercicio.getIntensidad() == intensidad) {
                contador++;
            }
        }

        return contador;
    }

    public String getRutaOrigen() {
        return rutaOrigen;
    }

    public boolean isUsandoBD() {
        return usandoBD;
    }
}
