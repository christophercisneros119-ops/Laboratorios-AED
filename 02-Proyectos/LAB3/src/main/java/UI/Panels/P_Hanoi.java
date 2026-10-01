package UI.Panels;

import Recursivos.Constantes;
import Recursivos.Hanoi;
import Recursivos.PasoHanoi;
import UI.Elements.BotonEstilizado;
import UI.Elements.Imagenes;
import UI.Elements.UIConstants;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class P_Hanoi extends P_EjercicioBase {

private final JTextField txtDiscos =
            new JTextField(String.valueOf(Constantes.DISCOS_MAXIMOS), 4);

    private List<PasoHanoi> pasos = List.of();
    private List<List<Integer>> torres = torresVacias();
    private int discos = Constantes.DISCOS_MAXIMOS;

    /** El panel tiene que poder dibujarse antes de que se toque ningun boton. */
    private static List<List<Integer>> torresVacias() {
        List<List<Integer>> lista = new ArrayList<>();
        for (int torre = 0; torre < Constantes.TORRES_TOTAL; torre++) {
            lista.add(new ArrayList<>());
        }
        return lista;
    }

public P_Hanoi() {
super("Torres de Hanói",
                "Mueve los discos de la columna A a la columna C usando la columna B.",
                UIConstants.FONDO_HANOI, UIConstants.CAFE_OSCURO, UIConstants.CAFE_OSCURO);
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila.add(rotulo("Discos:"));
        fila.add(txtDiscos);
        fila.add(rotulo("Velocidad:"));
        fila.add(velocidad());
        BotonEstilizado btnGenerar = BotonEstilizado.texto("Generar", 150,
                UIConstants.GRIS_SUAVE, UIConstants.GRIS_OSCURO, UIConstants.GRIS_PRESION);
        txtDiscos.setFont(UIConstants.FONT_TEXTO);
        btnGenerar.addActionListener(evento -> reiniciar());
        fila.add(btnGenerar);
        // Enter en el campo genera igual que el boton.
        txtDiscos.addActionListener(evento -> reiniciar());
        addConfiguracion(fila);
        preparar();
        refrescar();
    }

    @Override
    protected boolean preparar() {
        int cantidad = leerDiscos();
        if (cantidad < 1) {
            return false;
        }
        discos = cantidad;
        pasos = Hanoi.resolver(cantidad);
        torres = torresVacias();
        for (int disco = discos; disco >= 1; disco--) {
            torres.get(Constantes.TORRE_ORIGEN).add(disco);
        }
        return true;
    }

private int leerDiscos() {
        int cantidad;
        try {
            cantidad = Integer.parseInt(txtDiscos.getText().trim());
        } catch (NumberFormatException excepcion) {
            avisar("La cantidad de discos debe ser un número entero.");
            return -1;
        }
        if (cantidad < 1 || cantidad > Constantes.DISCOS_MAXIMOS) {
            avisar("La cantidad de discos debe estar entre 1 y "
                    + Constantes.DISCOS_MAXIMOS + ".");
            return -1;
        }
        return cantidad;
    }

    @Override
    protected int totalPasos() {
        return pasos.size();
    }

    @Override
    protected void aplicarPaso(int indice) {
        PasoHanoi paso = pasos.get(indice);
        torres.get(paso.torreOrigen()).removeLast();
        torres.get(paso.torreDestino()).add(paso.numeroDisco());
    }

    private static int posicionTorre(int ancho, int torre) {
// Espacio entre torres: un paso menos que antes para acercarlas, sin que
        // la tarta mayor (disco 7) llegue a rozar la torre vecina.
        int paso = ancho / (Constantes.TORRES_TOTAL + 2);
        return ancho / 2 + (torre - 1) * paso;
    }

@Override
    protected void pintarLienzo(Graphics2D g2, int ancho, int alto) {
        int[] ventana = Imagenes.ventanaFoto(ancho, alto);
        int vx = ventana[0];
        int vy = ventana[1];
        int aw = ventana[2];
        int ah = ventana[3];
        Imagenes.dibujarFondoVentana(g2, ventana, "fondo_hanoi");

        // La tarta se apoya unos pixeles mas abajo que el margen, asi queda mas
        // cerca del borde y las letras A/B/C siguen apoyadas en el fondo.
        int base = vy + ah - UIConstants.MARGEN + 10;
        // Piso de tarta de altura fija: divido siempre por 10, asi los 7 pisos
        // maximos calzan en la ventana y la tarta no se espesa si usas menos.
        int altoPiso = ah / (Constantes.DISCOS_MAXIMOS + Constantes.TORRES_TOTAL);
        int grosor = Math.max(4, ancho / 200);
        int radio = Math.max(6, altoPiso / 3);
        // Pisos angostos: el piso mayor (disco 7) llega a ancho/7, para que la
        // tarta no invada las torres vecinas. Dividir por mas espacios los achica.
        int divisorAncho = Constantes.DISCOS_MAXIMOS * (Constantes.TORRES_TOTAL + 4);

        // No hay barra horizontal: los postes nacen en la base y la tarta se
        // apoya directo sobre el fondo.

        // Grosor real de cada piso: la altura de su dibujo en el sprite, escalada
        // para que los 7 pisos juntos ocupen el mismo alto de siempre (7*piso). Asi
        // el piso 1 sale grueso y el 7 finito, como lo dibujaron, sin estirar nada.
        boolean sprites = true;
        double[] altoDibujo = new double[Constantes.DISCOS_MAXIMOS + 1];
        double sumaDibujo = 0;
        for (int d = 1; d <= Constantes.DISCOS_MAXIMOS; d++) {
            int[] caja = Imagenes.cajaArte(pisoDelDisco(d));
            if (caja == null) {
                sprites = false;
                break;
            }
            altoDibujo[d] = caja[3] - caja[2] + 1;
            sumaDibujo += altoDibujo[d];
        }
        double escala = sprites
                ? (Constantes.DISCOS_MAXIMOS * altoPiso) / sumaDibujo
                : 1.0;

        // Los postes suben desde la base hasta justo el limite superior del
        // quequito morado (el tope de la tarta completa) y no asoman por arriba.
        g2.setColor(UIConstants.PIEZA_ALT);
        double pilaMaxima = 0;
        for (int d = 1; d <= Constantes.DISCOS_MAXIMOS; d++) {
            pilaMaxima += altoDibujo[d] * escala;
        }
        int topePoste = sprites ? (int) Math.round(base - pilaMaxima) : vy + ah / 5;
        for (int torre = 0; torre < Constantes.TORRES_TOTAL; torre++) {
            int centro = posicionTorre(aw, torre);
            g2.fillRect(centro - grosor / 2, topePoste, grosor, base - topePoste);
        }

        // Piezas: los discos, de abajo hacia arriba en cada torre. Los pisos los
        // nombre al reves: el disco grande usa el piso_1 y el chico el piso_7
        // (el quequito morado). La vela es un sprite aparte (velita.png) y solo
        // corona la torre C cuando se completa la secuencia.
        for (int torre = 0; torre < Constantes.TORRES_TOTAL; torre++) {
            List<Integer> pila = torres.get(torre);
            if (pila.isEmpty()) {
                continue;
            }
            int centro = posicionTorre(aw, torre);
            if (!sprites) {
                for (int nivel = 0; nivel < pila.size(); nivel++) {
                    int disco = pila.get(nivel);
                    int anchoDisco = aw * disco / divisorAncho;
                    int y = base - (nivel + 1) * altoPiso;
                    caja(g2, centro - anchoDisco / 2, y, anchoDisco, altoPiso - 2, radio,
                            UIConstants.PIEZA, UIConstants.BORDE);
                    g2.setColor(UIConstants.CASILLA);
                    g2.fillRect(centro - anchoDisco / 2 + 3, y + 3, anchoDisco - 6, 3);
                }
                continue;
            }
            double totalPila = 0;
            for (int nivel = 0; nivel < pila.size(); nivel++) {
                totalPila += altoDibujo[pila.get(nivel)] * escala;
            }
            // El tope de la pila es el piso mas chico (disco 1): se apoya arriba
            // y cada piso siguiente cae justo debajo, hasta apoyarse en la base.
            double y = base - totalPila;
            for (int nivel = pila.size() - 1; nivel >= 0; nivel--) {
                int disco = pila.get(nivel);
                int anchoDisco = aw * disco / divisorAncho;
                double altoTier = altoDibujo[disco] * escala;
                int hTier = Math.max(1, (int) Math.round(altoTier));
                int yTier = (int) Math.round(y);
                Imagenes.dibujarPiso(g2, pisoDelDisco(disco),
                        centro - anchoDisco / 2, yTier, anchoDisco, hTier);
                y += altoTier;
            }
        }

        // La velita: su llama es un sprite aparte que solo corona la torre C
        // cuando la secuencia termino y los discos ya estan todos ordenados.
        if (sprites && completa()) {
            int centro = posicionTorre(aw, Constantes.TORRE_DESTINO);
            List<Integer> pila = torres.get(Constantes.TORRE_DESTINO);
            double totalPila = 0;
            for (int nivel = 0; nivel < pila.size(); nivel++) {
                totalPila += altoDibujo[pila.get(nivel)] * escala;
            }
            int[] cajaVela = Imagenes.cajaArte("velita");
            if (cajaVela != null) {
                double altoVela = (cajaVela[3] - cajaVela[2] + 1) * escala;
                int hVela = Math.max(1, (int) Math.round(altoVela));
                int anchoVela = Math.max(1, aw / divisorAncho);
                int yVela = (int) Math.round(base - totalPila) - hVela;
                Imagenes.dibujarPiso(g2, "velita", centro - anchoVela / 2,
                        yVela, anchoVela, hVela);
            }
        }

        // Columnas: A, B y C, debajo de cada poste, pegadas al borde de la ventana.
        String[] columnas = {"A", "B", "C"};
        for (int torre = 0; torre < Constantes.TORRES_TOTAL; torre++) {
            textoCentrado(g2, columnas[torre], posicionTorre(aw, torre),
                    vy + ah - 4, ah, UIConstants.CAFE_OSCURO);
        }
    }

    /** Sprite del piso de un disco: al reves, el grande es el piso_1 y el
     *  chico (disco 1) el piso_7, el quequito morado que corona la tarta. */
    private static String pisoDelDisco(int disco) {
        return "piso_" + (Constantes.DISCOS_MAXIMOS + 1 - disco);
    }

    /** True cuando la ultima pieza ya cayo en la torre C y toca prender la vela. */
    private boolean completa() {
        return discos >= 1 && torres.get(Constantes.TORRE_DESTINO).size() == discos;
    }
}
