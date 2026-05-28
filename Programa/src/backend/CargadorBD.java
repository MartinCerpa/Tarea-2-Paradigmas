package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CargadorBD implements FuenteDatos {

    @Override
    public List<Ejercicio> cargarEjercicios(String rutaBD) throws Exception {
        List<Ejercicio> ejercicios = new ArrayList<>();

        Class.forName("org.sqlite.JDBC");

        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + rutaBD);
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT codigo, nombre, tipo, intensidad, "
                     + "tiempo, descripcion, ultimaSemanaUso FROM ejercicios")) {

            while (rs.next()) {
                TipoEjercicio tipo = TipoEjercicio.desdeString(rs.getString("tipo"));
                IntensidadEjercicio intensidad = IntensidadEjercicio.desdeString(rs.getString("intensidad"));

                Ejercicio ejercicio = new Ejercicio(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        tipo,
                        intensidad,
                        rs.getInt("tiempo"),
                        rs.getString("descripcion"),
                        rs.getInt("ultimaSemanaUso")
                );

                ejercicios.add(ejercicio);
            }
        }

        if (ejercicios.isEmpty()) {
            throw new Exception("La base de datos no contiene ejercicios.");
        }

        return ejercicios;
    }
}
