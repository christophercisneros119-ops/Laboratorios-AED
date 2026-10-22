 Dentro de este .md se encuentra un código de java concreto y el resto son pseudocódigos que el maestro nos brindó para  la realización del laboratorio. 
 
1. Código en java SOLO de Infija a Prefija:

```java
import javax.swing.*;
	
	public class Prefija
	{
	    public static void main(String arg[])
	    {
	        int i, l, Tope = -1;
	        String EI = JOptionPane.showInputDialog("Escriba la expresion infija");
	        String EPRE = "", simb, IEpre = "";
	        l=EI.length();
	        String Pila[] = new String[l];
	        i=l-1;
	        while(i>=0)
	        {
	            simb=EI.substring(i,i+1);
	            i--;
	            if (simb.equalsIgnoreCase(")")) {
	                Tope++;
	                Pila[Tope] = simb;
	            }
	            else
	            {
	                if(simb.equalsIgnoreCase("("))
	                {
	                    while(Pila[Tope].compareTo(")") != 0 )
	                    {
	                        EPRE+=Pila[Tope];
	                        Tope--;
	                    }
	                    Tope--;
	                }
	                else
	                {
	                    if((simb.compareTo("A") >= 0) && (simb.compareTo("Z") <= 0))
	                    {
	                        EPRE+=simb;
	                    }
	                    else
	                    {
	                        if(Tope>=0)
	                        {
	                            while(Prioridad(simb)<Prioridad(Pila[Tope]))
	                            {
	                                EPRE+=Pila[Tope];
	                                Tope--;
	                                if(Tope== (-1))
	                                {
	                                    break;
	                                }
	                            }
	                        }
	                        Tope++;
	                        Pila[Tope]=simb;
	                    }
	                }
	            }
	        }
	        while (Tope>=0)
	        {
	            EPRE+=Pila[Tope];
	            Tope--;
	        }
	        for(i= (EPRE.length() - 1); i >= 0; i--)
	            IEpre += EPRE.substring(i, i+1);
	
	        JOptionPane.showMessageDialog(null,"Expresion Infija = "+EI+"\nExpresion Prefija="+IEpre);
	    }
	
	    public static int Prioridad(String operador)
	    {
	        if(operador.equals("^"))
	        {
	            return 4;
	        }
	        else
	        {
	            if((operador.equals("*")) || (operador.equals("/")))
	            {
	                return 3;
	            }
	            else
	            {
	                if((operador.equals("+")) || (operador.equals("-")))
	                {
	                    return 2;
	                }
	                else
	                {
	                    return 1;
	                }
	            }
	        }
	    }
	}

```

2. Pseudocódigo para conversión de expresiones Infijas a Postfijas:

```shell
CONVPOSTFIJA (EI, EPOS)

{Convierte una expresión infija (EI) a postfija (EPOS), haciendo uso de Pila}

    Hacer Tope ← -1
    Repetir mientras EI sea diferente de la cadena vacía.
        Tomar el símbolo más a la izquierda de EI, recortando luego la expresión
        Si el símbolo es paréntesis izquierdo entonces {Poner símbolo en pila}
            Hacer Tope ← Tope + 1 y Pila[Tope] ← símbolo
        Si no
            Si símbolo es paréntesis derecho
                Repetir mientras Pila[Tope] ≠ paréntesis izquierdo
                    Hacer EPOS ← Pila[Tope] y Tope ← Tope -1
                {fin ciclo}
                {Sacamos el paréntesis izquierdo de Pila y no lo agregamos a EPOS}
                Hacer Tope ← Tope -1
            Si no
                Si símbolo es un operando entonces
                    Agregar símbolo a EPOS
                Si no {Es un operador}
                    Si (tope ≥ 0) entonces
                        Repetir mientras la prioridad del operador sea menor o igual que la prioridad del operador que está en la cima de Pila.
                            Hacer EPOS ← Pila[Tope] y Tope ← Tope -1
                            Si tope = -1 interrumpir ciclo
                        {Fin del ciclo}
                    {Fin del condicional}
                    Hacer Tope ← Tope + 1 y Pila [Tope] ← símbolo
                {fin condicional operando/operador}
            {fin condicional paréntesis derecho}
        {Fin condicional paréntesis izquierdo}
    {Fin ciclo repetir EI}

    {Sacar los elementos de la Pila y agregarlos a EPOS}
    Repetir mientras (Tope >= 0)
        Hacer EPOS ← Pila[Tope] y Tope ← Tope -1
    {Fin ciclo}
    Escribir EPOS.

```

3.  Pseudocódigo para conversión de expresiones Infijas a Prefijas:
   
```shell
CONVPREFIJA (EI, EPRE)

{Convierte una expresión infija (EI) a prefija (EPRE), haciendo uso de Pila}

    Hacer Tope ← -1
    Repetir mientras EI sea diferente de la cadena vacía.
        Tomar el símbolo más a la derecha de EI recortando luego la expresión
        Si el símbolo es paréntesis derecho entonces {Poner símbolo en pila}
            Hacer Tope ← Tope + 1 y Pila[Tope] ← símbolo
        Si no
            Si símbolo es paréntesis izquierdo
                Repetir mientras Pila[Tope] ≠ paréntesis derecho
                    Hacer EPRE ← Pila[Tope] y Tope ← Tope -1
                {fin ciclo}
                {Sacamos el paréntesis derecho de Pila y no lo agregamos a EPRE}
                Hacer Tope ← Tope -1
            Si no
                Si símbolo es un operando entonces
                    Agregar símbolo a EPRE
                Si no {Es un operador}
                    Si (tope ≥ 0) entonces
                        Repetir mientras la prioridad del operador sea menor que la prioridad del operador que está en la cima de Pila.
                            Hacer EPRE ← Pila[Tope] y Tope ← Tope -1
                            Si tope = -1 interrumpir ciclo
                        {Fin del ciclo}
                    {Fin del condicional}
                    Hacer Tope ← Tope + 1 y Pila[Tope] ← símbolo
                {fin condicional operando/operador}
            {fin condicional paréntesis izquierdo}
        {Fin condicional paréntesis derecho}
    {Fin ciclo repetir EI}

    {Sacar los elementos de la Pila y agregarlos a EPRE}
    Repetir mientras (Tope >= 0)
        Hacer EPRE ← Pila[Tope] y Tope ← Tope -1
    {Fin ciclo 4}
    Escribir EPRE en forma inversa.

```

4. PilaLlena:

```shell
PilaLlena(Pila, Tope, Max, Band)

	{Verifica si una estructura tipo pila está llena, asignando a Band el valor de verdad correspondiente. La pila se implementa en un arreglo unidimensional de Max elementos. Tope es de tipo entero. Band es de tipo boolean}
	
	    Si (Tope = Max-1) entonces
	        Hacer Band ← Verdadero {pila llena}
	    si no
	        Hacer Band ← Falso {pila vacía}
	    {Fin del condicional del paso 1}

```

4. Push:

```shell
Push(Pila, Tope, Max, Dato)

{Agrega el elemento Dato en una estructura tipo Pila, si la misma no esta llena. Actualiza Tope, Max representa el numero máximo de elementos que puede almacenar Pila. Tope es un parámetro de tipo entero. Band variable booleana que es Verdadera si la pila esta llena}
		
		    Llamar a PilaLlena con Pila, Tope, Max y Band
		    Si (Band = Verdadero) entonces
		        escribir “Desbordamiento -Pila llena”
		    si no
		        {Actualiza tope e inserta el nuevo elemento en el Tope de Pila}
		        Hacer Tope ← Tope + 1
		        Pila[Tope] ← Dato
		    { Fin del condicional del paso 2}

```
   
6. PilaVacia:

```shell
Pilavacia(Pila, Tope, Band)

{Verifica si una estructura tipo pila esta vacia, asignando a Band el valor de verdad correspondiente. La pila se implementa en un arreglo unidimensional. Tope es de tipo entero. Band es de tipo boolean}

    Si (Tope = -1) entonces
        Hacer Band ← Verdadero {pila vacia}
    si no
        Hacer Band ← Falso { pila llena}
    {Fin del condicional del paso 1}

```

7. Pop:
   
```shell
Pop(Pila, Tope, Dato)

{Saca un elemento Dato de una estructura tipo Pila, si la misma no esta vacía. El elemento que se elimina es el que se encuentra en la posición indicada por Tope}

    Llamar a PilaVacia con Pila, Tope y Band
    Si (Band = Verdadero) entonces
        escribir “Subdesbordamiento -Pila vacia”
    si no 
        {Actualiza tope}
        Hacer Dato ← Pila[Tope]
        Escribir Dato
        Tope ← Tope - 1
    { Fin del condicional del paso 2}

```

8. Encolar:

```shell
Encolar(Cola, Max, Frente, Final, Dato)

{ Inserta Dato al final de la Cola. Frente y Final indican el inicio y el final de la Cola. Max es el máximo número de elementos que puede almacenar la cola} {Frente y Finalse inicializan en -1}

    Si (Final < Max-1) { Verifica que hay espacio libre en la cola}
        Final ← Final + 1
        Cola[Final] ← Dato
        Si (Final = 0) o (Final = 1) entonces {Se inserto el primer elemento en la cola}
            Frente ← 0
        {Fin del condicional 1.1}
    si no
        Escribir “Desbordamiento – Cola llena”
    {Fin del condicional del paso 1}
	

```

10. Desencolar:

```shell
Desencolar(Cola, Frente, Final, Dato)

{ Elimina el primer elemento de la Cola y lo almacena en Dato. Frente y Final indican el inicio y el final de la Cola.}

    Si (Frente != -1) { Verifica que la cola no este vacía}
        Dato ← Cola[Frente]
        Si (Frente = Final) entonces {Si hay un solo elemento}
            Frente ← -1 y Final ← -1 {Indica cola vacía}
        si no
            Frente ← Frente + 1
        {Fin del condicional 1.1}
    si no
        Escribir “Subdesbordamiento – Cola vacía”
    {Fin del condicional del paso 1}	

```

11. EncolarCircular:

```shell
EncolarCircular(ColaCir, Max, Frente, Final, Dato)

{ Inserta Dato al final de la Cola circular. Frente y Final indican el inicio y el final de la Cola. Max es el máximo numero de elementos que puede almacenar la cola circular}

    Si ((Final = Max -1) y (Frente = 0)) o ((Final +1 = Frente))
        Escribir “Desbordamiento – Cola llena”
    si no
        Si (Final = Max-1) { Verifica que Final este en la ultima celda de la cola}
            Final ← 0
        si no
            Final ← Final + 1
        {Fin del condicional 1.1}
        ColaCir[Final] ← Dato
        Si (Frente = -1) entonces
            Frente ← 0
        {Fin del condicional 1.3}
    {Fin del condicional del paso 1}

```

12. DesencolarCircular:

```shell
DesencolarCircular(ColaCir, Max, Frente, Final, Dato)

{ Inserta Dato al final de la Cola circular. Frente y Final indican el inicio y el final de la Cola. Max es el máximo numero de elementos que puede almacenar la cola circular}

    Si (Frente = -1) {Verifica si la cola esta vacía}
        Escribir “Subdesbordamiento – Cola vacía”
    si no
        Dato ← ColaCir[Frente]
        Si (Frente = Final) { Si hay un solo elemento}
            Frente ← -1 y Final ← -1
        si no
            Si (Frente = Max -1)
                Frente ← 0
            si no
                Frente ← Frente + 1
            {Fin del condicional 1.1.1}
        {Fin del condicional 1.1}
    {Fin del condicional del paso 1}

```