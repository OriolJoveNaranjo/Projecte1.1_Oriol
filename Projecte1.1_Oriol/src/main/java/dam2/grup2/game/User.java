package dam2.grup2.game;

import dam2.grup2.entities.Board;
import dam2.grup2.entities.Player;
import dam2.grup2.entities.Move;
import dam2.grup2.entities.Node;
import dam2.grup2.logic.Minimax;
import dam2.grup2.logic.Score;
import dam2.grup2.logic.GameRules;
import dam2.grup2.logic.TreeBuilder;

import java.util.List;
import java.util.Scanner;

/**
 * Representa a un jugador real o IA en la partida de Conecta 4. Permite elegir
 * un movimiento ya sea leyendo del teclado o usando Minimax.
 */
public class User {

    private final String name;
    private final Player player;
    private final boolean ai;
    private final Minimax minimax;
    private final int aiDepth;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructor para un jugador humano.
     *
     * @param name Nombre del usuario
     * @param player Entidad Player asociada
     */
    public User(String name, Player player) {
        this.name = name;
        this.player = player;
        this.ai = false;
        this.minimax = null;
        this.aiDepth = 0;
    }

    /**
     * Constructor para un jugador IA.
     *
     * @param name Nombre de la IA
     * @param player Entidad Player asociada
     * @param score Heurística Score para evaluar tableros
     * @param aiDepth Profundidad máxima de búsqueda
     */
    public User(String name, Player player, Score score, int aiDepth) {
        this.name = name;
        this.player = player;
        this.ai = true;
        this.minimax = new Minimax(score);
        this.aiDepth = aiDepth;
    }

    /**
     * Pide y devuelve la columna donde el usuario (humano o IA) quiere colocar
     * ficha. Para jugadores humanos, valida la entrada y repite hasta que sea
     * válida.
     *
     * @param board Estado actual del tablero
     * @return índice de columna válido [0..6]
     */
    public int chooseColumn(Board board) {
        // Si es IA, delega en Minimax
        if (ai) {

            // 1. Crear nodo raíz con el tablero actual.
            // El jugador indicado en el nodo raiz da igual porque TreeBuilder usará playerX y playerO.
            Node root = new Node(board.copy(), player == player.getOpponent() ? 2 : 1, -1);

            // 2. Generar el árbol usando los dos jugadores reales
            // TreeBuilder ya alterna internamente según MAX_LEVEL
            TreeBuilder.generateTree(root, player, player.getOpponent());

            // 3. Ejecutar Minimax sobre el árbol generado
            Move bestMove = minimax.getBestMove(root, player);

            System.out.printf("IA %s elige columna %d%n", name, bestMove.getColumn());
            return bestMove.getColumn();
        }

        // Si es jugador humano, pedimos entrada y validamos
        List<Integer> validMoves = GameRules.getValidMoves(board);

        while (true) {
            System.out.printf("%s (%c), elige una columna %s: ",
                    name, player.getSymbol(), validMoves);

            String input = scanner.nextLine().trim();

            // Validar entrada vacía
            if (input.isEmpty()) {
                System.out.println("⚠️  Entrada vacía. Introduce un número.");
                continue;
            }

            // Intentar convertir a número
            int col;
            try {
                col = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Entrada no válida. Introduce un número entero.");
                continue;
            }

            // Validar si la columna está dentro de las jugadas válidas
            if (!validMoves.contains(col)) {
                System.out.printf("⚠️  Columna inválida o llena. Elige una de: %s%n", validMoves);
                continue;
            }

            // Todo correcto → devolvemos la columna
            return col;
        }
    }

    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isAI() {
        return ai;
    }
}
