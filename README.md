# Laboratorios-AED

Prácticas de laboratorio de **Algoritmos y Estructuras de Datos**.

## Estructura

```
Laboratorios-AED/
├── 01-Guias/          Guías de laboratorio (PDF)
|	└── LAB4/          Guía del LAB4 y un .md
├── 02-Proyectos/
│   ├── LAB1/          Arreglos desordenados y ordenados
│   ├── LAB2/          Ordenación y búsqueda
│   └── LAB3/          Algoritmos recursivos
│   └── LAB4/          Pilas, colas y notaciones polacas
└── 03-Trabajos/       Trabajos entregables (PDF)
```

## Proyectos

Cada laboratorio es un proyecto Maven de Java Swing que abre en pantalla
completa desde `com.mycompany.labN.LABN`.

| Proyecto | Tema                                                                 |
| -------- | -------------------------------------------------------------------- |
| LAB1     | Arreglos: desordenados, ordenados, búsqueda binaria                  |
| LAB2     | Ordenación (burbuja, inserción, selección) y búsqueda                |
| LAB3     | Recursividad: Torres de Hanói, salto de la rana, 8 Reinas, QuickSort |
| LAB4     | Pilas, colas y conversiones polacas                                  |

## Cómo correr

Importar el proyecto en NetBeans (o `mvn clean package`) y ejecutar la clase
principal `com.mycompany.labN.LABN`. En los LAB1 a LAB3 cada simulación se
reproduce paso a paso con los controles Iniciar / Pausa / Reiniciar; en el
LAB4 cada estructura se opera a mano (Apilar, Desapilar, Encolar, Desencolar)
y la conversión polaca se recorre con Anterior / Siguiente.