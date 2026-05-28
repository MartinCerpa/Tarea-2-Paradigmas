package backend;

public class Ejercicio {

    private String codigo;
    private String nombre;
    private TipoEjercicio tipo;
    private IntensidadEjercicio intensidad;
    private int tiempoEstimado;
    private String descripcion;
    private int ultimaSemanaUso;

    public Ejercicio(String codigo, String nombre, TipoEjercicio tipo, IntensidadEjercicio intensidad,
                     int tiempoEstimado, String descripcion, int ultimaSemanaUso) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.intensidad = intensidad;
        this.tiempoEstimado = tiempoEstimado;
        this.descripcion = descripcion;
        this.ultimaSemanaUso = ultimaSemanaUso;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoEjercicio getTipo() {
        return tipo;
    }

    public IntensidadEjercicio getIntensidad() {
        return intensidad;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getUltimaSemanaUso() {
        return ultimaSemanaUso;
    }

    public void setUltimaSemanaUso(int ultimaSemanaUso) {
        this.ultimaSemanaUso = ultimaSemanaUso;
    }

    @Override
    public String toString() {
        return codigo + ";" + nombre + ";" + tipo.name() + ";" + intensidad.name()
                + ";" + tiempoEstimado + ";" + descripcion + ";" + ultimaSemanaUso;
    }
}
