package backend;

public enum TipoEjercicio {
    CARDIOVASCULAR("Cardiovascular"),
    FUERZA("Fuerza");

    private final String nombre;

    TipoEjercicio(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public static TipoEjercicio desdeString(String texto) {
        for (TipoEjercicio t : values()) {
            if (t.name().equalsIgnoreCase(texto)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo de ejercicio desconocido: " + texto);
    }
}
