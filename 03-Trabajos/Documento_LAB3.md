# Laboratorio #3 · Algoritmos Recursivos
## Documento técnico, cumplimiento de la guía y plan de defensa

| | |
|---|---|
| **Asignatura** | Algoritmización y Estructuras de Datos (AED) |
| **Docente** | MSc. Ing. Eliezer Aburto Plata |
| **Guía** | `01-Guias/Guia_3_Recursividad_Act.pdf` (10 páginas) |
| **Proyecto** | `02-Proyectos/LAB3` — Java 21 · Swing · Maven |
| **Equipo (3)** | Janelly Romero `2025-1905U` · Moisés Alemán `2025-2560U` · Christopher Cisneros `2025-0032U` |
| **Estado** | Compila sin warnings · 7/7 pruebas automáticas en verde · commit `54efdba` en `develop` |
| **Tamaño** | 26 clases Java · ~3.232 líneas · ~110 recursos (sprites, fondos animados, fuentes) |

---

## 1. Estructura del proyecto

### 1.1 El repositorio

```
Laboratorios-AED/
├── 01-Guias/          Guías PDF del docente (Guia_3 = nuestra consigna)
├── 02-Proyectos/
│   ├── LAB1/  LAB2/   Prácticas anteriores
│   └── LAB3/          ★ Este proyecto (Maven: src/main/java + resources)
└── 03-Trabajos/       Entregables (capturas, Word, este documento)
```

### 1.2 Paquetes de LAB3

```
src/main/java/
├── com/mycompany/lab3/LAB3.java      ← Punto de entrada (main)
├── Recursivos/                       ← NÚCLEO ALGORÍTMICO (sin Swing, sin UI)
│   ├── Constantes.java               4 algoritmos + constantes del problema
│   ├── Hanoi.java · SaltoRana.java        Paso*.java (records inmutables)
│   ├── Reinas.java · QuickSort.java       de cada paso)
└── UI/
    ├── Elements/                     Motor gráfico compartido
    │   Navegacion · Lienzo · Imagenes · BotonEstilizado
    │   FondoAnimado · Reproductor · TarjetaMiembro · Fuentes · UIConstants
    └── Panels/                       Pantallas
        UNI_Portada · P_Menu
        P_EjercicioBase (abstracto) → P_Hanoi · P_Rana · P_Reinas · P_QuickSort
```

| Capa (paquete) | Clases | Responsabilidad |
|---|---|---|
| `com.mycompany.lab3` | `LAB3` | `main`, ventana **pantalla completa**, DPI, Escape para salir |
| `Recursivos` | `Hanoi`, `SaltoRana`, `Reinas`, `QuickSort` + `Paso*` + `Constantes` | **La recursividad pura**: resuelven y devuelven la lista de pasos. No conocen Java Swing |
| `UI.Panels` | `UNI_Portada`, `P_Menu`, `P_EjercicioBase`, `P_*` | Pantallas: entrada de datos, validación, reproducción y dibujo de la simulación |
| `UI.Elements` | `Reproductor`, `Lienzo`, `Imagenes`, `BotonEstilizado`, `Navegacion`, `FondoAnimado`… | Servicios reutilizables: motor de pasos, lienzo de dibujo, carga de imágenes, navegación entre pantallas |

### 1.3 Arquitectura en capas

```
        ┌──────────────────────────────────────────────────────┐
        │  UI.Panels  (vista + control del usuario)            │
        │  Portada → Menú → {Hanoi | Rana | Reinas | QuickSort}│
        └───────────────┬──────────────────────────────────────┘
                        │ llama y reproduce (no calcula)
                        ▼
        ┌──────────────────────────────────────────────────────┐
        │  Recursivos  (núcleo: recursión + backtracking)      │
        │  resolver(...)  →  List<PasoX>  (paso inmutable)     │
        └──────────────────────────────────────────────────────┘
```

> **Idea central del diseño:** el algoritmo recursivo **no dibuja nada**: ejecuta
> una vez y devuelve la *lista de pasos* (records inmutables). La UI luego
> **reproduce** esa lista paso a paso con un temporizador. Así la recursión se
> puede estudiar sola y la animación se controla sola.

---

## 2. Cómo se conecta todo

### 2.1 Navegación (una sola ventana, se cambia el contenido)

```
LAB3.main() ──▶ JFrame en pantalla completa
                 └── UNI_Portada      [Comenzar] ──▶ P_Menu
                        ▲                              │
                        │ [Regresar]                   │ clic en opción
                        └──────────────────────────────┤
                                                       ▼
                                 P_Hanoi · P_Rana · P_Reinas · P_QuickSort
                                        └── [Regresar] ──▶ P_Menu
```

* `Navegacion.irA(origen, destino)` solo reemplaza el `contentPane` de la ventana
  (no crea ventanas nuevas). Todos los `Regresar` usan ese mismo método.

### 2.2 Ciclo de vida de un ejercicio (flujo de datos)

```
  ┌─ usuario escribe datos (Discos: / Valores:) y pulsa [Generar] ──────┐
  │                                                                     ▼
  │        P_XXX.preparar()  ── valida la entrada ──▶  error: avisar() (línea en rojo)
  │                │ ok
  │                ▼
  │        Recursivos.X.resolver(...)  →  List<Paso>   ◀── LA RECURSIÓN
  │                │
  │                ▼
  │        Reproductor (javax.swing.Timer)  [Iniciar / Pausa / Repetir]
  │                │ cada N ms: paso++
  │                ▼
  │        aplicarPaso(i)  →  actualiza el estado dibujado (torres, casillas…)
  │                │
  └────────────────┴──▶ lienzo.repaint() ─▶ pintarLienzo(g2, w, h)
                                              │
                              Imagenes/sprites▼ (fondo de la ventana de foto)
```

* **Contrato** que cumple cada panel (`P_EjercicioBase` es abstracto):
  `preparar()` · `totalPasos()` · `aplicarPaso(i)` · `pintarLienzo(g2, w, h)`.
* **Velocidad en vivo**: el combo *Velocidad* llama `Reproductor.cambiarRitmo(ms)`
  aunque la reproducción esté corriendo (1 s / 0,5 s / 0,25 s).
* **Contador** en pantalla: `Paso k / n` (o `Listo · n pasos`).

---

## 3. Cumplimiento de la Guía 3 (rúbrica)

### 3.1 Requisitos de la guía ↔ implementación

| # | Requisito textual de la guía | Dónde está en el código | Estado |
|---|---|---|---|
| 1 | *“Simulación gráfica de la **torre de Hanoi** con el método recursivo, **3 torres** y **N discos digitados por el usuario** (máx. **7**)"* | `Recursivos/Hanoi.java` → `mover()` recursivo; campo **Discos:** en `P_Hanoi` con validación `1..7` (`leerDiscos()`); 3 torres A/B/C dibujadas en `pintarLienzo` | ✅ |
| 2 | *“Simulación gráfica **'El salto de la rana'** con métodos recursivos"* + pseudocódigo `SaltoRana(N, ESTADO, MOVIMIENTOS)` con **swap / llamar / deshacer** | `Recursivos/SaltoRana.java`: `buscar()` ⇄ `probarDesde()` ⇄ `intentar()` reproduce exactamente *intercambiar → llamar → deshacer*. Estado inicial `VVV _ CCC` (igual que la guía) | ✅ |
| 3a | *8 reinas: **primera reina en (0,0)**"* | `Reinas.probarColumna()` arranca en columna 0 → la primera colocación es `(0,0)` *(verificado en ejecución)* | ✅ |
| 3b | *“…**validar que la posición no afecte** a las demás"* | `Reinas.puedeColocar()` con marcas de columna y de las 2 diagonales | ✅ |
| 3c | *“…**hasta completar el ingreso de las 8 reinas**"* | Recursión por filas: caso base `fila == 8`; animación coloca las 8 reinas | ✅ |
| 3d | *“Deberá existir un **botón** que permita que el usuario ingrese la **siguiente reina**"* | Botón **[Siguiente reina]** en `P_Reinas` (fila de configuración, junto a *Velocidad:*); llama a `P_EjercicioBase.siguientePaso()` → `Reproductor.avanzarUno()`: coloca la siguiente reina en pausa y también durante la animación | ✅ (ver 3.3) |
| 4 | *“Con **QuickSort**, simulación de cómo se realiza el **intercambio entre elementos** hasta que el arreglo quede ordenado"* | `Recursivos/QuickSort.java`: partición de **Lomuto**; cada `intercambiar()` genera un `PasoQuickSort` (origen, destino, subrango del pivote) que la UI pinta y resalta | ✅ |
| — | *“Proponer una forma **diferente** a la de la imagen (92 soluciones)"* | Nuestro `Reinas.resolver()` devuelve **una** solución distinta de la del enunciado *(verificada: 8 reinas, cero ataques)* | ✅ |

### 3.2 Verificaciones ejecutadas (no son humo: se corrieron)

| Prueba | Resultado |
|---|---|
| Hanói con 7 discos | **127 pasos** = 2⁷−1; deltas torres `[-7, 0, 7]` (todos llegaron a C) |
| Rana | **15 movimientos** = mínimo `n·(n+2)` = 3·5; estado final **`CCC _ VVV`** ✅ |
| 8 Reinas | **8 colocaciones**, primera en **(0,0)**, **sin ataques** (ni fila, columna ni diagonal) |
| QuickSort `9,3,7,1,8,2,10,5,4,6` | 11 intercambios → final **`1,2,3,4,5,6,7,8,9,10`** ordenado ✅ |
| Pruebas visuales del proyecto (píxeles/interfaz) | **7/7 OK** (menú, portada, marcos, animación, sprites, validación, oscuridad) |
| Compilación | `javac --release 21` **sin una sola línea de warning** |

### 3.3 ✅ Resuelto: el botón de “siguiente reina"

La guía pide literalmente *“un botón que permita que el usuario ingrese la
siguiente reina"*. **Ya existe**: el botón **[Siguiente reina]** en la cabecera
de `P_Reinas` (mismo gris redondeado que Iniciar, a la derecha del selector de
Velocidad).

- **Cómo funciona:** `P_Reinas` lo crea con `BotonEstilizado.textoAjustado(...)`
  y su acción llama a `P_EjercicioBase.siguientePaso()` (nuevo método
  `protected final`), que invoca `Reproductor.avanzarUno()`.
- **`Reproductor.avanzarUno()`** (nuevo): avanza **exactamente un paso** cuando
  la reproducción está *pausada*; si está *activa* no hace nada (Iniciar/Pausa
  ya cubre ese caso) y si terminó, tampoco (no se pasa del final).
- **Flujo de defensa:** pausar → ir reina por reina con [Siguiente reina] →
  completar las 8 → reinas inmediatamente después, con su validación visible.
- **Verificado:** 7/7 pruebas (el test de menú ahora cuenta con el caso
  `botonManual`, que comprueba su posición en Reinas y **no** aparece en los
  otros 3 paneles, más una prueba funcional: 9 clics → contador
  `Listo · 8 pasos`); render con píxeles `GRIS_SUAVE` en el rectángulo
  234×44 de `x=294..520, y=110..146`.

### 3.4 Entrega que pide la guía (página 10)

- [ ] **Código fuente en .zip** (carpeta del proyecto).
- [ ] **Documento Word** con: *Portada · Introducción · Desarrollo de la guía
      (con capturas del programa compilado) · Conclusión · Bibliografía*
      → plantilla lista en la sección **8.4**.
- [ ] Defensa oral: preguntas de control de conocimiento del docente
      → preparadas en la sección **7**.

---

## 4. Cómo aplicamos la recursividad

### 4.1 Vista general

| Algoritmo | Caso base | Paso recursivo | Parámetros que bajan | Resultado |
|---|---|---|---|---|
| **Hanói** | `discos <= 0` | `mover(n−1)` → mover disco n → `mover(n−1)` | cantidad de discos | 2ⁿ−1 pasos |
| **Rana** | 15 movimientos alcanzados (solución) o casillas agotadas | probar un movimiento, **deshacer** si no lleva a nada | posición de prueba | lista de 15 saltos |
| **Reinas** | `fila == 8` (tablero completo) | colocar en columna válida y bajar de fila; **desmarcar** si falla | fila / columna | 8 colocaciones válidas |
| **QuickSort** | `primero >= ultimo` | particionar y recursar a izquierda y derecha; dentro, `explorar(explorado+1)` | subrango `primero..ultimo` | arreglo ordenado + intercambios |

> **Todos los núcleos recursivos están escritos sin `for`/`while`**: los bucles
> del pseudocódigo del docente (p. ej. `Para i ← 0 hasta 6` de la rana) se
> resuelven con recursión en diagonal (`posición + 1`, `columna + 1`,
> `explorado + 1`). Es justamente lo que pide el objetivo del laboratorio.

### 4.2 Torres de Hanói — `Recursivos/Hanoi.java`

```java
private static void mover(int discos, int origen, int auxiliar, int destino,
                          List<PasoHanoi> pasos) {
    if (discos <= 0) { return; }                    // caso base
    mover(discos - 1, origen, destino, auxiliar, pasos);   // 1) deja libres los n-1
    pasos.add(new PasoHanoi(discos, origen, destino));     // 2) mueve el disco n
    mover(discos - 1, auxiliar, origen, destino, pasos);   // 3) los n-1 encima
}
```

* Tres parámetros (origen/auxiliar/destino) que **se rotan** en cada llamada:
  el auxiliar de una llamada es el destino de la otra.
* Costo: **2ⁿ−1**; con 7 discos → **127 pasos** (el contador de la pantalla
  muestra exactamente eso).
* La simulación no reposiciona discos a mano: `P_Hanoi.aplicarPaso()` hace
  `torres[origen].removeLast()` + `torres[destino].add(disco)` por cada paso.

### 4.3 Salto de la rana — `Recursivos/SaltoRana.java`

Mapeo directo del pseudocódigo de la guía (págs. 6-8):

| Pseudocódigo del docente | Nuestro código |
|---|---|
| `Si ES_SOLUCION(ESTADO)` → escribir solución | `if (pasos.size() == MOVIMIENTOS_RANA) return true;` (15 = 3·(3+2)) |
| `Para i ← 0 hasta 6` | `probarDesde(estado, pasos, posicion + 1)` ← **recursión en lugar del for** |
| `Intercambiar … Llamar SaltoRana … Deshacer` | `intentar()`: `intercambiar()` → `pasos.add()` → `buscar()` → si falla: `pasos.remove()` + `intercambiar()` (**backtracking**) |
| Verde a la derecha / café a la izquierda | `estado[posicion]=='V'` avanza `+1/+2`… `'C'` avanza `−1/−2` |
| Regla de salto (saltar sobre una rana distinta a la casilla vacía) | `puedeSaltar(estado, medio, destino, ranaContraria)` |

* **Backtracking explícito**: cada movimiento se “deshace" si la rama no llega
  a la solución, dejando el estado como estaba.
* Verificado en ejecución: `VVV _ CCC` → **15 movimientos** → `CCC _ VVV`.

### 4.4 Ocho reinas — `Recursivos/Reinas.java`

```java
colocar(fila)   → probarColumna(fila, 0)                 // intenta columnas
   puedeColocar(fila, col) = !columna ocupada
                          && !diagonal principal ocupada
                          && !diagonal secundaria ocupada
   si falla  → probarColumna(fila, col + 1)              // siguiente columna
   si sirve  → marcar(); probarColumna(fila + 1)…        // baja una fila
   si abajo no hay solución → desmarcar(); probarColumna(fila, col + 1)
caso base: fila == 8 → solución completa
```

* **Marcas en arreglos booleanos** (`columnas`, `diagonalesPrincipal[]`,
  `diagonalesSecundaria[]`) en vez de recorrer el tablero: comprobación O(1).
* Índices de diagonal: `fila − columna + 7` y `fila + columna` (las 15+15
  diagonales de un tablero 8×8).
* Arranca en `(0,0)` (columna 0 siempre disponible al inicio) ✅.

### 4.5 QuickSort — `Recursivos/QuickSort.java`

* `ordenar(valores, primero, ultimo)`: caso base `primero >= ultimo`;
  partición (Lomuto, pivote = último) y **dos llamadas recursivas**.
* La partición también es recursiva: `explorar(explorado + 1)` recorre el
  subrango sin ningún `for`, acumulando el límite en `int[] limite` (arreglo de
  una celda, porque una función recursiva “clásica" no puede devolver dos
  valores a la vez).
* Cada `intercambiar()` agrega un `PasoQuickSort(valores, origen, destino,
  primero, ultimo)` → la UI resalta **el par intercambiado** y **el subrango
  activo del pivote** para “ver cómo se hace el intercambio" (requisito 4).

---

## 5. El código explicado

### 5.1 Núcleo `Recursivos` (lo que hay que poder explicar de memoria)

| Clase | Qué es | Clave |
|---|---|---|
| `Constantes` | 3 torres, 7 discos máx., 3+3 ranas, tablero 8×8 | números mágicos centralizados |
| `Hanoi.resolver(n)` | lista de `PasoHanoi(disco, origen, destino)` | 2ⁿ−1 pasos |
| `SaltoRana.resolver()` | lista de `PasoRana(origen, destino)` | backtracking + deshacer |
| `Reinas.resolver()` | lista de `PasoReina(fila, columna)` | backtracking con marcas O(1) |
| `QuickSort.resolver(valores)` | lista de `PasoQuickSort(copia de valores, i, j, primero, ultimo)` | el record **clona** el arreglo: cada paso guarda un snapshot |
| `Paso*` | `record` de Java: inmutables, sin equals manual | la UI no puede “corromper" la solución |

### 5.2 Motor de la interfaz (`UI.Elements`)

| Clase | Rol | Detalle |
|---|---|---|
| `Reproductor` | Motor de reproducción compartido | `javax.swing.Timer`; `iniciar/alternar/reiniciar`, `cambiarRitmo()` en vivo, `avanzarUno()` (un paso por clic, para [Siguiente reina]), se detiene solo al terminar para ofrecer *Repetir* |
| `Navegacion` | Cambio de pantalla | `irA(origen, destino)` reemplaza el `contentPane` |
| `Lienzo` | Superficie de dibujo | llama a `pintarLienzo(g2, w, h)` del panel dueño |
| `Imagenes` | Carga/cache de imágenes | `ventanaFoto()` (rectángulo de la foto), cover centrado, sprites, secuencias animadas (`fondo_menu_00…`, `fondo_quicksort_00…`) |
| `BotonEstilizado` | Botón propio | esquinas redondeadas; reposo/hover/clic sin que el hover deforme el botón; `texto()` y `textoAjustado()` |
| `FondoAnimado` | Panel con fondo de cuadros | usan portada y menú (15 fps, recorte tipo video) |
| `TarjetaMiembro` | Foto de integrante en la portada | marco pixel-art; si falta la foto, muestra iniciales |
| `UIConstants` | Paleta y métricas | un solo lugar para colores/fuentes: cambias el estilo de los 4 ejercicios de una vez |
| `Fuentes` | Fuentes pixel (Silkscreen TTF) | cargadas desde resources |

### 5.3 El contrato `P_EjercicioBase` (la clase más importante de la UI)

```
P_EjercicioBase (abstracta)
 ├─ construye: cabecera (título+instrucción+datos), lienzo, controles
 ├─ [Regresar] solo abajo-izquierda · [Iniciar/Pausa/Repetir] y [Reiniciar] centrados
 ├─ combo Velocidad → Reproductor.cambiarRitmo()
 ├─ siguientePaso() → Reproductor.avanzarUno()  (lo usa [Siguiente reina])
 ├─ avisar(msg) → línea de error en rojo (sin diálogos modales:
 │                en pantalla completa un JOptionPane esconde la ventana)
 └─ métodos que cada ejercicio implementa:
      preparar()       valida y calcula la solución recursiva
      totalPasos()     longitud de la lista de pasos
      aplicarPaso(i)   aplica el paso i al estado dibujado
      pintarLienzo()   dibuja fondo + piezas con la geometría actual
```

### 5.4 Los cuatro paneles

| Panel | Entrada del usuario | Estado dibujado | Dibujo (`pintarLienzo`) |
|---|---|---|---|
| `P_Hanoi` | campo **Discos:** (1..7) + [Generar] | `torres[3][discos]` | foto de cocina, 3 postes, tortas `piso_1..7.png`, letras A/B/C |
| `P_Rana` | solo velocidad | `casillas[7]` = `V/C/_` | lago animado, ranas con sprites (idle/salto), nenufares y piedra central |
| `P_Reinas` | velocidad + botón **[Siguiente reina]** (avanza un paso en pausa) | `columnaPorFila[8]` (`-1` = sin reina) | jardín, tablero `tablero.png` escalado entero, `queen.png` por celda |
| `P_QuickSort` | **Valores:** (10 enteros separados por coma) + [Generar] | `mostrados[10]`, `resaltados` | castillo animado, espadas `espada.png` como barras, resalta intercambio y subrango del pivote |

### 5.5 Validación de entradas (sin `JOptionPane`)

| Campo | Reglas (mensaje que aparece en rojo) |
|---|---|
| Discos (Hanói) | entero, **entre 1 y 7** |
| Valores (QuickSort) | sin signos, sin decimales, solo dígitos/comas, **exactamente 10** valores, ninguno vacío, **mayores que 0** |
| Errores de formato | “La cantidad de discos debe ser un número entero", etc. |

---

## 6. Extras (más allá de la guía, sirven en la defensa)

1. **Pantalla completa** exclusiva + Escape para salir; DPI fijo para que las
   imágenes no salgan borrosas.
2. **Portada institucional** con los 3 integrantes (foto, nombre y carnet).
3. **Fondos animados** (portada/menú/castillo de QuickSort) a 15 fps.
4. **Velocidad en vivo** (1 s / 0,5 s / 0,25 s) sin reiniciar la simulación.
5. **Estado por botón**: Iniciar → Pausa → Repetir; contador `Paso k / n`.
6. **Pruebas automáticas por píxeles** (7 herramientas `Verificar*`: menú,
   portada, marcos, animación, sprites, validación, oscuridad de sprites) que
   se corrían en cada cambio; **7/7 en verde**.
7. **Control de versiones**: todo commit en `develop` (`54efdba`), historial
   con mensajes convencionales.

---

## 7. Preparación para la defensa (preguntas probables)

| Pregunta típica | Respuesta corta sugerida |
|---|---|
| *¿Cuál es el caso base de Hanói?* | `discos <= 0`: no hay nada que mover; la recursión se detiene en 1 disco que “moverse solo". |
| *¿Por cuántos pasos pasa con 7 discos?* | 2⁷−1 = **127** (el contador lo muestra). |
| *¿Qué es el backtracking?* | Probar una opción, bajar, y si no funciona **deshacer** (rana: `pasos.remove` + swap de vuelta; reinas: `desmarcar`). |
| *¿Dónde está el “deshacer" del pseudocódigo de la rana?* | En `intentar()`: `intercambiar → buscar → (si falla) remove + intercambiar`. |
| *¿Cómo evitan ataques entre reinas?* | Tres arreglos booleanos: columna y las dos diagonales; se **marcan** al colocar y **desmarcan** al retroceder. |
| *¿Por qué QuickSort si no tiene bucles en la partición?* | `explorar(explorado+1)` es la recursión en diagonal que reemplaza al `for`; acumula el límite en `int[] limite`. |
| *¿Qué complejidad tiene QuickSort?* | Promedio O(n log n); peor caso O(n²) (arreglo ya ordenado con pivote al final). |
| *¿Qué es un `record`?* | Tipo inmutable de Java 16+: sin setters, `equals/toString` automáticos → cada paso es un snapshot seguro. |
| *¿Por qué separaron la recursión de la interfaz?* | Para poder probar el algoritmo solo (lista de pasos) y animarlo a cualquier velocidad sin tocar la recursión. |
| *¿El caso base de la rana es “15"? ¿no debería ser `ES_SOLUCION`?* | El mínimo teórico es n(n+2)=15; la búsqueda DFS encuentra exactamente esa longitud y el estado final verificado es `CCC _ VVV`. |
| *¿Y el botón de reinas que pide la guía?* | Está en la cabecera: **[Siguiente reina]** llama a `Reproductor.avanzarUno()` y coloca una reina por clic, con validación de columna/diagonales (sección 3.3); se demuestra pausando y pulsándolo. |

**Reglas de la defensa:** tener la app abierta *antes* de hablar, tener la guía
PDF a la mano, y si algo falla en vivo, usar las capturas (8.3).

---

## 8. Plan de exposición para 3 personas

### 8.1 Reparto (≈10-12 minutos + preguntas)

| Persona | Bloque | Tiempo | Qué muestra en pantalla |
|---|---|---|---|
| **A — Intro y arquitectura** | Presentación del equipo, guía, portada, menú, cómo está conectado todo (secciones 1-2) | ~2,5 min | Portada → Comenzar → Menú; menciona paquetes `Recursivos` vs `UI` |
| **B — Recursividad 1** | Hanói + Salto de la rana (sección 4.2-4.3) | ~4 min | Hanói con **7 discos** (127 pasos), cambiar velocidad en vivo; Rana `VVV _ CCC → CCC _ VVV` |
| **C — Recursividad 2 y cierre** | 8 Reinas + QuickSort + calidad/entrega (4.4-4.5, 6, 3.4) | ~4 min | Reinas colocando 8 sin ataques; QuickSort con los 10 valores y resaltado de intercambios; cierra con validaciones y pruebas |

### 8.2 Guion minuto a minuto

```
0:00  A · Portada institucional (3 integrantes, códigos) → [Comenzar]
0:40  A · Menú: 4 ejercicios, cada uno con su color
1:10  A · Arquitectura: “la recursión vive en Recursivos/ y devuelve pasos;
         la UI solo los reproduce" (diagrama 2.3 en la mano)
2:30  B · Hanói: escribe 7 discos → [Generar] → cuenta 2ⁿ−1 = 127
        “caso base discos<=0; dos llamadas recursivas alrededor del disco n"
4:15  B · Rana: muestra estado inicial, adelanta, explica backtracking
        (intercambiar → llamar → deshacer) y llega a CCC _ VVV
5:45  C · Reinas: primera reina en (0,0), pausa y avanza con [Siguiente
        reina] una por una, valida columna/diagonales, completa las 8
7:30  C · QuickSort: 10 valores, resalta el intercambio y el subrango
        del pivote; “partición de Lomuto, dos recursiones"
9:00  C · Calidad: validaciones en rojo, velocidad en vivo, pantalla
        completa, pruebas 7/7 y entrega (zip + Word)
10:00 Todos · Cierre y preguntas (reparto según tabla 7)
```

### 8.3 Demo en vivo + respaldo

* **Antes de la clase**: correr `LAB3` en pantalla completa, dejar Hanói con 7
  discos generado y QuickSort con una serie cargada (evita escribar en vivo).
* **Capturas de respaldo** para el Word (y si la app falla): portada, menú,
  y los 4 ejercicios corriendo. Si se quieren reutilizar los renders ya
  generados: `C:\tmp\cap_portada_1280.png`, `cap_menu_1280.png`,
  `cap_hanoi_1280.png`, `cap_rana_1280.png`, `cap_reinas_1280.png`,
  `cap_quicksort_1280.png`.
* **Preguntas**: quien no habló de un tema responde de ese tema (B cubre
  Hanói/Rana, C Reinas/QuickSort, A estructura y generalidades).

### 8.4 Estructura del documento Word (que pide la guía)

```
1. Portada           · Universidad, asignatura, “Laboratorio #3 · Algoritmos
                       recursivos", integrantes + carnets, docente, fecha
2. Introducción      · objetivos de la guía, por qué la recursividad (sección 4.1)
3. Desarrollo        · 3.1 Hanói · 3.2 Rana · 3.3 8 Reinas · 3.4 QuickSort
                       (explicación + código clave + CAPTURA de cada uno;
                       incluir la tabla de cumplimiento de la sección 3.1)
4. Conclusión        · qué se aprendió (recursión, backtracking, separación
                       algoritmo/interfaz) + resultados verificados (3.2)
5. Bibliografía      · Guía 3 del MSc. Aburto Plata; libro de texto del curso;
                       documentación oficial de Java (records, Swing)
```

---

*Documento generado para el equipo — LAB3 · AED · `develop@54efdba`.*
