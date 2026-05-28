package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreadorBD {

    public static void crearBaseDatos(String rutaBD) throws Exception {
        Class.forName("org.sqlite.JDBC");

        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + rutaBD);
             Statement stmt = conexion.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS ejercicios ("
                    + "codigo TEXT PRIMARY KEY,"
                    + "nombre TEXT NOT NULL,"
                    + "tipo TEXT NOT NULL,"
                    + "intensidad TEXT NOT NULL,"
                    + "tiempo INTEGER NOT NULL,"
                    + "descripcion TEXT NOT NULL,"
                    + "ultimaSemanaUso INTEGER NOT NULL DEFAULT 0"
                    + ")");

            stmt.execute("DELETE FROM ejercicios");

            String[] inserts = {
                "INSERT INTO ejercicios VALUES ('E001','Trote suave','CARDIOVASCULAR','BASICO',15,'Trotar a ritmo comodo manteniendo respiracion controlada',0)",
                "INSERT INTO ejercicios VALUES ('E002','Bicicleta estatica','CARDIOVASCULAR','INTERMEDIO',20,'Pedalear a ritmo constante durante el tiempo indicado',1)",
                "INSERT INTO ejercicios VALUES ('E003','Burpees','CARDIOVASCULAR','AVANZADO',12,'Realizar burpees controlando la postura y la respiracion',2)",
                "INSERT INTO ejercicios VALUES ('E004','Saltar la cuerda','CARDIOVASCULAR','INTERMEDIO',10,'Saltar de forma continua manteniendo coordinacion',0)",
                "INSERT INTO ejercicios VALUES ('E005','Sprint corto','CARDIOVASCULAR','ALTO_RENDIMIENTO',8,'Correr a maxima velocidad en intervalos cortos',3)",
                "INSERT INTO ejercicios VALUES ('E006','Sentadillas','FUERZA','BASICO',10,'Bajar y subir manteniendo la espalda recta',0)",
                "INSERT INTO ejercicios VALUES ('E007','Flexiones','FUERZA','INTERMEDIO',10,'Realizar flexiones manteniendo el cuerpo alineado',1)",
                "INSERT INTO ejercicios VALUES ('E008','Peso muerto','FUERZA','AVANZADO',15,'Levantar el peso manteniendo la espalda firme',2)",
                "INSERT INTO ejercicios VALUES ('E009','Plancha abdominal','FUERZA','BASICO',8,'Mantener el cuerpo recto apoyado en antebrazos',0)",
                "INSERT INTO ejercicios VALUES ('E010','Dominadas','FUERZA','ALTO_RENDIMIENTO',12,'Elevar el cuerpo usando la fuerza de brazos y espalda',3)"
            };

            for (String insert : inserts) {
                stmt.execute(insert);
            }
        }
    }
}
