package backend;

public enum IntensidadEjercicio {
    BASICO("Basico"),
    INTERMEDIO("Intermedio"),
    AVANZADO("Avanzado"),
    ALTO_RENDIMIENTO("Alto rendimiento");

    private final String nombre;

    IntensidadEjercicio(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public static IntensidadEjercicio desdeString(String texto) {
        for (IntensidadEjercicio i : values()) {
            if (i.name().equalsIgnoreCase(texto)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Intensidad desconocida: " + texto);
    }
}
