package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CargadorEjercicios implements FuenteDatos {

    @Override
    public List<Ejercicio> cargarEjercicios(String rutaArchivo) throws IOException {
        List<Ejercicio> ejercicios = new ArrayList<>();

        File archivo = new File(rutaArchivo);

        if (!archivo.exists()) {
            throw new IOException("El archivo no existe: " + rutaArchivo);
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {
                numeroLinea++;

                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(";");

                if (datos.length != 7) {
                    throw new IOException("Formato incorrecto en la linea " + numeroLinea
                            + ": se esperaban 7 campos, se encontraron " + datos.length);
                }

                for (String dato : datos) {
                    if (dato.trim().isEmpty()) {
                        throw new IOException("Informacion incompleta en la linea " + numeroLinea);
                    }
                }

                String codigo = datos[0].trim();
                String nombre = datos[1].trim();
                String tipoStr = datos[2].trim();
                String intensidadStr = datos[3].trim();

                int tiempoEstimado;
                int ultimaSemanaUso;

                try {
                    tiempoEstimado = Integer.parseInt(datos[4].trim());
                    ultimaSemanaUso = Integer.parseInt(datos[6].trim());
                } catch (NumberFormatException e) {
                    throw new IOException("Error numerico en la linea " + numeroLinea
                            + ": tiempo y ultimaSemanaUso deben ser numeros enteros");
                }

                String descripcion = datos[5].trim();

                TipoEjercicio tipo;
                IntensidadEjercicio intensidad;
                try {
                    tipo = TipoEjercicio.desdeString(tipoStr);
                    intensidad = IntensidadEjercicio.desdeString(intensidadStr);
                } catch (IllegalArgumentException e) {
                    throw new IOException("Error en linea " + numeroLinea + ": " + e.getMessage());
                }

                Ejercicio ejercicio = new Ejercicio(
                        codigo, nombre, tipo, intensidad,
                        tiempoEstimado, descripcion, ultimaSemanaUso
                );

                ejercicios.add(ejercicio);
            }
        }

        if (ejercicios.isEmpty()) {
            throw new IOException("El archivo no contiene ejercicios validos.");
        }

        return ejercicios;
    }
}
