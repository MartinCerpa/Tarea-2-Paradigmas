# Tarea 2 - Sistema de rutinas de entrenamiento

## Descripción

Este programa permite cargar ejercicios y generar rutinas de entrenamiento personalizadas según el tipo de ejercicio, la intensidad y la semana actual.

El sistema fue desarrollado en Java usando Swing para la interfaz gráfica. La lógica del programa está separada en el paquete `backend` y las ventanas están en el paquete `frontend`.

## Funcionalidades

El programa permite:

* Cargar ejercicios desde un archivo CSV.
* Cargar ejercicios desde una base de datos SQLite.
* Crear una base de datos de ejemplo con ejercicios.
* Ver un resumen de los ejercicios cargados.
* Generar una rutina según:

  * nombre del cliente;
  * cantidad de ejercicios cardiovasculares;
  * intensidad cardiovascular;
  * cantidad de ejercicios de fuerza;
  * intensidad de fuerza;
  * semana actual.
* Revisar la rutina ejercicio por ejercicio.
* Ver un resumen final de la rutina.
* Revisar rutinas generadas anteriormente durante la misma ejecución del programa.

## Estructura del proyecto

```text
Programa/
├── datos/
│   └── ejercicios.csv
├── lib/
│   └── sqlite-jdbc.jar
├── src/
│   ├── backend/
│   ├── frontend/
│   └── Main.java
├── compilar.bat
├── ejecutar.bat
└── README.md
```

## Formato del archivo CSV

El archivo de ejercicios debe tener las columnas separadas por punto y coma `;`.

Formato:

```text
codigo;nombre;tipo;intensidad;tiempo;descripcion;ultimaSemanaUso
```

Ejemplo:

```text
E001;Trote suave;CARDIOVASCULAR;BASICO;15;Trotar a ritmo comodo manteniendo respiracion controlada;0
```

## Tipos de ejercicio permitidos

```text
CARDIOVASCULAR
FUERZA
```

## Intensidades permitidas

```text
BASICO
INTERMEDIO
AVANZADO
ALTO\_RENDIMIENTO
```

## Explicación de `ultimaSemanaUso`

El campo `ultimaSemanaUso` indica la última semana en que fue usado un ejercicio.

Por ejemplo, el valor:

```text
0
```

significa que el ejercicio no ha sido usado anteriormente.

Si un ejercicio fue usado en la semana anterior a la semana actual, el sistema no lo considera disponible para la rutina nueva. Esto se hace para cumplir la restricción de no repetir ejercicios en semanas consecutivas.

## Cómo compilar

Desde la carpeta principal del proyecto, ejecutar:

```bat
compilar.bat
```

También se puede compilar manualmente con:

```bat
javac -cp "lib/\*" -d bin src/Main.java src/backend/\*.java src/frontend/\*.java
```

## Cómo ejecutar

Después de compilar, ejecutar:

```bat
ejecutar.bat
```

O manualmente:

```bat
java -cp "bin;lib/\*" Main
```

## Uso del programa

1. Abrir el programa.
2. Cargar los ejercicios desde CSV o desde base de datos.
3. Revisar el resumen de ejercicios cargados.
4. Presionar el botón para generar una rutina.
5. Ingresar los datos solicitados:

   * nombre del cliente;
   * cantidad de ejercicios cardiovasculares;
   * intensidad cardiovascular;
   * cantidad de ejercicios de fuerza;
   * intensidad de fuerza;
   * semana actual.
6. Generar la rutina.
7. Revisar los ejercicios con los botones de navegación.
8. Al llegar al último ejercicio, abrir el resumen de la rutina.
9. También se pueden revisar las rutinas existentes desde la ventana principal o desde la ventana de revisión.

## Rutinas existentes

Las rutinas generadas se guardan en memoria mientras el programa está abierto.

Esto permite revisar una rutina anterior sin tener que generarla de nuevo. Al cerrar el programa, este historial se pierde.

## Supuestos considerados

* Se usa CSV o SQLite porque el enunciado permite cargar ejercicios desde archivo o base de datos.
* Los ejercicios cargados se mantienen en memoria durante la ejecución del programa.
* Las rutinas generadas también se mantienen en memoria durante la ejecución.
* Si no hay suficientes ejercicios para cumplir una solicitud, el sistema muestra un mensaje de error.
* La semana actual debe ser mayor a 0.
* El tiempo estimado de cada ejercicio debe ser mayor a 0.
* El campo `ultimaSemanaUso` debe ser mayor o igual a 0.
* El programa usa un sistema simple de observadores para comunicar cambios entre el backend y el frontend.

## Paquetes principales

### backend

Contiene la lógica del sistema:

* carga de ejercicios;
* generación de rutinas;
* validaciones;
* manejo de datos en memoria;
* comunicación con observadores.

### frontend

Contiene las ventanas del programa:

* ventana principal;
* ventana de carga;
* ventana de generación;
* ventana de revisión;
* ventana de resumen;
* ventana de rutinas existentes.

## Integrantes


```text
Nombre: Martin Cerpa Diaz
RUT: 21.629.439-4
NRC: 13046
```

