## 1. Mapa de Navegación y Enlace de Pantallas

La aplicación funciona mediante un flujo de menús y navegación modular:

- **Pantalla de Presentación (Splash/Inicio):** Es la pantalla inicial de bienvenida o presentación de la aplicación. Desde aquí se transiciona al menú principal del laboratorio. (Se puede dejar igual que en el LAB3)
    
- **Menú Principal ("Lab #4 Pilas, Colas y Notaciones Polacas"):** Actúa como el panel de control central. Contiene cuatro botones principales que permiten navegar hacia las diferentes secciones o módulos de la aplicación:
    
    1. **Botón "Pilas":** Abre la pantalla de gestión de Pilas.
        
    2. **Botón "Colas":** Abre la pantalla de gestión de Colas lineales.
        
    3. **Botón "Colas Circulares":** Abre la pantalla de gestión de Colas Circulares.
        
    4. **Botón "Notación Polaca":** Abre la pantalla de conversión de expresiones.
        
- **Botón de Retorno ("Atrás"):** Disponible en los módulos secundarios para regresar al menú principal del laboratorio.
    

## 2. Descripción y Requerimientos por Pantalla

### A. Pantalla de Menú Principal (`Lab #4`)

- **Qué lleva:** Un título descriptivo y un panel con cuatro botones interactivos para acceder a los módulos ("Pilas", "Colas", "Colas Circulares", "Notación Polaca") y un botón para volver atrás.
    
- **Cómo funciona:** Permite al usuario elegir qué estructura de datos desea simular o probar.
    

### B. Módulo de Pilas (Stack)

- Qué lleva:
    
    - Un campo de texto para definir el **Tamaño del arreglo** junto con un botón de confirmación/generación.
        
    - Un campo de texto para **Ingresar un elemento** y su botón respectivo para realizar la operación de _Push_ (Apilar).
        
    - Un indicador visual del estado actual (por ejemplo, `Tope = 1`).
        
    - Un botón de **Desapilar** (_Pop_).
        
    - Una representación gráfica del arreglo en forma de bloques o casillas que muestran los elementos almacenados (ej. `A`, `B`) siguiendo el principio LIFO (último en entrar, primero en salir).
        
- Cómo funciona: El usuario define el tamaño máximo de la pila. Luego, puede agregar elementos que se van colocando en la cima (tope) o retirarlos desde la misma posición, actualizándose gráficamente el contenedor y el indicador de tope.
    

### C. Módulo de Colas (Queue - Lineal)

- Qué lleva:
    
    - Un campo para configurar el **Tamaño del arreglo** con su botón de configuración.
        
    - Una representación gráfica lineal de celdas indexadas (con índices desde `0` hasta `5`, por ejemplo) que muestran los elementos almacenados (`A`, `B`, `C`, `D`, `E`).
        
    - Un campo de texto para **Ingresar un elemento** (encolar) con su botón de acción.
        
    - Un botón de **Desencolar** (_Dequeue_).
        
    - Indicadores de posición para el **Frente** y el **Final** de la cola.
        
- Cómo funciona: Sigue el principio FIFO (primero en entrar, primero en salir). Los elementos entran por el final y se retiran por el frente, moviendo los punteros correspondientes a medida que la cola se llena o se vacía.
    

### D. Módulo de Colas Circulares

- Qué lleva:
    
    - Un arreglo gráfico circular o lineal representado con índices (del `0` al `9`), donde se muestran elementos distribuidos (ej. letras de la `A` a la `J`).
        
    - Punteros visuales para denotar el **Frente** y el **Final**.
        
    - Controles de entrada: campo para **Ingresar el trabajo/elemento** y su tamaño o valor.
        
    - Indicadores de estado numéricos para **Frente** y **Final**.
        
    - Un botón de **Desencolar**.
        
- Cómo funciona: A diferencia de la cola lineal, aprovecha el espacio reutilizando las posiciones iniciales cuando el puntero final llega al límite del arreglo físico, ajustando dinámicamente los índices de frente y final de manera circular.
    

### E. Módulo de Notación Polaca

- Qué lleva:
    
    - Un campo de entrada para digitar una expresión en notación **Infija** (ej. `X + y * Z`) con su botón de procesamiento.
        
    - Opciones o botones de selección para convertir a notación **Prefija** y **Postfija**.
        
    - Un panel de pasos o historial numerado (`1`, `2`, `3`) que muestra el procedimiento o traza de conversión.
        
    - Una representación visual de una pila auxiliar vertical con su respectivo indicador de `Tope` (utilizada internamente para evaluar u operar con operadores como `*`, `+`, paréntesis, etc.).
        
    - Una sección de referencia con los caracteres y operadores válidos permitidos (`A-Z`, `*`, `/`, `+`, `-`, `()`, `^`).
        
- Cómo funciona: El usuario ingresa una expresión algebraica estándar (infija). Al seleccionar la conversión deseada, el sistema utiliza algoritmos basados en pilas para transformar la expresión a sufija (postfija) o prefija, mostrando paso a paso el comportamiento de la pila de operadores en la interfaz gráfica.