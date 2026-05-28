package backend;

import java.util.ArrayList;
import java.util.List;

public class Rutina {

    private int idRutina;
    private int semana;
    private String cliente;
    private List<Ejercicio> ejercicios;

    public Rutina() {
        ejercicios = new ArrayList<>();
        this.cliente = "Cliente general";
    }

    public Rutina(int idRutina, int semana, String cliente) {
        this();
        this.idRutina = idRutina;
        this.semana = semana;
        this.cliente = (cliente == null || cliente.trim().isEmpty()) ? "Cliente general" : cliente;
    }

    public void agregarEjercicio(Ejercicio ejercicio) {
        ejercicios.add(ejercicio);
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public int getCantidadTotal() {
        return ejercicios.size();
    }

    public int calcularTiempoTotal() {
        int total = 0;

        for (Ejercicio ejercicio : ejercicios) {
            total += ejercicio.getTiempoEstimado();
        }

        return total;
    }

    public int contarPorTipo(TipoEjercicio tipo) {
        int contador = 0;

        for (Ejercicio ejercicio : ejercicios) {
            if (ejercicio.getTipo() == tipo) {
                contador++;
            }
        }

        return contador;
    }

    public int contarPorIntensidad(IntensidadEjercicio intensidad) {
        int contador = 0;

        for (Ejercicio ejercicio : ejercicios) {
            if (ejercicio.getIntensidad() == intensidad) {
                contador++;
            }
        }

        return contador;
    }

    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    public int getSemana() {
        return semana;
    }

    public void setSemana(int semana) {
        this.semana = semana;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = (cliente == null || cliente.trim().isEmpty()) ? "Cliente general" : cliente;
    }
}
