package dam2.grup2.projecte1_equip2;

import dam2.grup2.entities.Player;
import dam2.grup2.logic.Score;
import dam2.grup2.game.GameEngine;
import dam2.grup2.game.User;
import java.util.Scanner;

/**
 * Clase principal del juego Connecta 4.
 * <p>
 * Esta clase permite al usuario iniciar una partida de Connecta 4
 * en dos modos: Humano vs Humano o Humano vs IA. Al finalizar cada
 * partida, se ofrece la opción de jugar otra partida o salir.
 * <p>
 * Cada partida crea nuevos objetos de jugadores y reinicia el tablero.
 * 
 * @author alber
 */
public class Projecte1_equip3 {

    /**
     * Método principal que arranca la aplicación.
     * Gestiona la selección de modo de juego, creación de jugadores
     * y el bucle principal de partidas.
     *
     * @param args Argumentos de línea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Mostrar reglas iniciales
        System.out.println("Benvingut al joc del **Connecta 4**!");
        System.out.println("Regles del joc:");
        System.out.println("- Dos jugadors (tu i un altre jugador / tu i la IA) col·loqueu fitxes per torns en un tauler de 7 columnes i 6 files.");
        System.out.println("- L'objectiu és connectar quatre fitxes del teu color en línia (horitzontal, vertical o diagonal).");
        System.out.println("- El joc acaba quan un jugador connecta 4 fitxes o el tauler està ple (empat).");
        System.out.println("Comença la partida!\n");
        
        System.out.println("Bienvenido a Conecta 4");

        boolean playAgain = true; // Controla si el usuario quiere jugar otra partida

        while (playAgain) {
            // Selección de modo de juego con validación
            int option = 0;
            while (option != 1 && option != 2) {
                System.out.println("Selecciona modo de juego:");
                System.out.println("1. Humano vs Humano");
                System.out.println("2. Humano vs IA");
                System.out.print("Elige opción (1 o 2): ");
                String input = scanner.nextLine().trim();
                try {
                    option = Integer.parseInt(input);
                    if (option != 1 && option != 2) {
                        System.out.println("⚠️ Opción inválida. Debes elegir 1 o 2.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Entrada no válida. Introduce un número (1 o 2).");
                }
            }

            // Crear jugador 1 con validación de nombre
            User player1 = createPlayer(scanner, "Jugador 1", 'X');
            User player2;
            if (option == 2) {
                // Modo Humano vs IA
                int difficulty = selectAIDifficulty(scanner);
                player2 = createAI("Computadora", 'O', difficulty);
            } else {
                // Modo Humano vs Humano
                player2 = createPlayer(scanner, "Jugador 2", 'O');
            }

            // Crear el motor de juego y empezar la partida
            GameEngine game = new GameEngine(player1, player2);
            game.startGame();

            // Preguntar si quieren jugar otra vez
            String response = "";
            while (!response.equalsIgnoreCase("s") && !response.equalsIgnoreCase("n")) {
                System.out.print("¿Quieres jugar otra partida? (s/n): ");
                response = scanner.nextLine().trim();
                if (response.equalsIgnoreCase("s")) {
                    playAgain = true; // Repetir el bucle principal
                } else if (response.equalsIgnoreCase("n")) {
                    playAgain = false; // Salir del bucle
                } else {
                    System.out.println("⚠️ Respuesta inválida. Introduce 's' o 'n'.");
                }
            }
        }

        System.out.println("¡Gracias por jugar a Conecta 4!");
        scanner.close(); // Cerrar el scanner al finalizar
    }

    /**
     * Crea un jugador humano solicitando su nombre por consola.
     * <p>
     * Valida que el nombre introducido no esté vacío.
     * Valida que el nombre no esté vacío.     *
     * @param scanner Scanner para lectura de consola
     * @param label Etiqueta para mostrar en la consola (ej: "Jugador 1")
     * @param symbol Símbolo que representa al jugador ('X' o 'O')
     * @return Objeto User representando al jugador humano
     */
    private static User createPlayer(Scanner scanner, String label, char symbol) {
        String name = "";
        while (name.isEmpty()) {
            System.out.printf("%s - introduce tu nombre: ", label);
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("⚠️ Nombre no puede estar vacío. Inténtalo de nuevo.");
            }
        }
        Player player = new Player(name, symbol);
        return new User(name, player);
    }

    /**
     * Crea un jugador IA con heurística y profundidad de búsqueda.
     * Crea un jugador IA con una heurística y profundidad de búsqueda.
     *
     * @param name Nombre de la IA
     * @param symbol Símbolo que representa a la IA ('X' o 'O')
     * @param depth Profundidad máxima de búsqueda del Minimax
     * @return Objeto User representando al jugador IA
     */
    private static User createAI(String name, char symbol, int depth) {
        Player aiPlayer = new Player(name, symbol);
        Score heuristic = new Score();
        return new User(name, aiPlayer, heuristic, depth);
    }

    /**

     * Permite al usuario seleccionar la dificultad de la IA.
     * <p>
     * La dificultad afecta la profundidad de búsqueda del algoritmo
     * Minimax. El valor debe estar entre 1 y 5.
     * Permite al usuario seleccionar la dificultad de la IA (aiDepth).
     * La IA pensará más turnos adelante cuanto mayor sea la dificultad.
     *
     * @param scanner Scanner para lectura de consola
     * @return Profundidad de búsqueda elegida
     */
    private static int selectAIDifficulty(Scanner scanner) {
        int difficulty = 0;
        while (difficulty < 1 || difficulty > 5) {
            System.out.print("Elige dificultad de la IA (1-5, donde 5 es más difícil): ");
            String input = scanner.nextLine().trim();
            try {
                difficulty = Integer.parseInt(input);
                if (difficulty < 1 || difficulty > 5) {
                    System.out.println("⚠️ Número fuera de rango. Introduce un valor entre 1 y 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Entrada no válida. Introduce un número entero entre 1 y 5.");
            }
        }
        return difficulty;
    }
}