package backend;

import java.util.List;

public interface FuenteDatos {
    List<Ejercicio> cargarEjercicios(String origen) throws Exception;
}
