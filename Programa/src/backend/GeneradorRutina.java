package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneradorRutina {

    public Rutina generarRutina(
            List<Ejercicio> ejerciciosDisponibles,
            int cantidadCardio,
            IntensidadEjercicio intensidadCardio,
            int cantidadFuerza,
            IntensidadEjercicio intensidadFuerza,
            int semanaActual,
            String cliente
    ) throws Exception {

        Rutina rutina = new Rutina(0, semanaActual, cliente);

        agregarEjerciciosPorTipo(
                ejerciciosDisponibles,
                rutina,
                TipoEjercicio.CARDIOVASCULAR,
                intensidadCardio,
                cantidadCardio,
                semanaActual
        );

        agregarEjerciciosPorTipo(
                ejerciciosDisponibles,
                rutina,
                TipoEjercicio.FUERZA,
                intensidadFuerza,
                cantidadFuerza,
                semanaActual
        );

        return rutina;
    }

    private void agregarEjerciciosPorTipo(
            List<Ejercicio> ejerciciosDisponibles,
            Rutina rutina,
            TipoEjercicio tipo,
            IntensidadEjercicio intensidad,
            int cantidadSolicitada,
            int semanaActual
    ) throws Exception {

        List<Ejercicio> candidatos = new ArrayList<>();

        for (Ejercicio ejercicio : ejerciciosDisponibles) {
            boolean mismoTipo = ejercicio.getTipo() == tipo;
            boolean mismaIntensidad = ejercicio.getIntensidad() == intensidad;
            boolean noUsadoSemanaAnterior = ejercicio.getUltimaSemanaUso() != semanaActual - 1;

            if (mismoTipo && mismaIntensidad && noUsadoSemanaAnterior) {
                candidatos.add(ejercicio);
            }
        }

        if (candidatos.size() < cantidadSolicitada) {
            throw new Exception(
                    "No hay suficientes ejercicios disponibles de tipo "
                            + tipo.getNombre()
                            + " con intensidad "
                            + intensidad.getNombre()
                            + ". Se requieren " + cantidadSolicitada
                            + ", pero solo hay " + candidatos.size() + " disponibles."
            );
        }

        Collections.shuffle(candidatos);

        for (int i = 0; i < cantidadSolicitada; i++) {
            Ejercicio seleccionado = candidatos.get(i);
            seleccionado.setUltimaSemanaUso(semanaActual);
            rutina.agregarEjercicio(seleccionado);
        }
    }
}
