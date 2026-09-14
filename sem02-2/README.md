# Laboratorio 02: Sistema de Gestión Académica (SW505-2026-2)

Proyecto de NetBeans / Java para la gestión de un pequeño sistema académico, implementando **Abstracción**, **Modularidad** y **Encapsulamiento**.

## Estructura del Proyecto

```text
SW505-2026-2/
└── sem02-2/
    ├── build/
    │   └── classes/           # Clases compiladas (.class)
    ├── nbproject/             # Configuración del proyecto NetBeans
    ├── src/
    │   ├── Alumno.java        # Clase Alumno con encapsulación y estados
    │   ├── Docente.java       # Clase Docente con atributos y métodos
    │   ├── Curso.java         # Clase Curso asociada a Docente
    │   ├── Matricula.java     # Clase Matrícula con arreglo de Cursos
    │   └── Main.java          # Clase Principal de prueba
    ├── Sem02-Laboratorio.txt  # Enunciado del laboratorio
    ├── build.xml              # Script Ant de construcción
    ├── manifest.mf            # Manifiesto del proyecto
    ├── run.sh                 # Script de compilación y ejecución rápida
    └── README.md
```

## Compilación y Ejecución

### Opción 1: Desde NetBeans
Abrir el folder `sem02-2` como proyecto en Apache NetBeans y presionar **Run (F6)**.

### Opción 2: Desde terminal con script
```bash
./run.sh
```

### Opción 3: Manual por línea de comandos
```bash
mkdir -p build/classes
javac -d build/classes src/*.java
java -cp build/classes Main
```
