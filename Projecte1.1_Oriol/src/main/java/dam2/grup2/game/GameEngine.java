package dam2.grup2.game;

import dam2.grup2.entities.Board;
import dam2.grup2.logic.GameRules;
import java.util.List;

/**
 * Controla el bucle de juego de una partida de Conecta 4
 * alternando turnos entre dos instancias de User.
 */
public class GameEngine {
    private final Board board = new Board();
    private final User player1;
    private final User player2;
    
    /**
     * Construye el gestor de partida y enlaza los jugadores entre sí.
     *
     * @param player1 Primer jugador
     * @param player2 Segundo jugador
     */
    public GameEngine(User player1, User player2) {
        this.player1 = player1;
        this.player2 = player2;
        // Asignamos oponentes en la entidad Player
        player1.getPlayer().setOpponent(player2.getPlayer());
        player2.getPlayer().setOpponent(player1.getPlayer());
    }

    /**
     * Inicia la partida por consola y gestiona turnos, condiciones de victoria y empate.
     */
    public void startGame() {
        User currentTurn = player1;
        User waitingTurn = player2;

        while (true) {
            board.printBoard();
            System.out.printf("Turno de %s (%c)%n",
                currentTurn.getName(), currentTurn.getPlayer().getSymbol());

            int column = currentTurn.chooseColumn(board);
            board.placeDisc(column, currentTurn.getPlayer());

            // Evaluamos si el jugador actual ha ganado con esta jugada
            if (GameRules.hasWon(board, currentTurn.getPlayer())) {
                board.printBoard();
                System.out.printf("¡%s gana la partida!%n", currentTurn.getName());
                break;
            }

            // Empate: sin movimientos disponibles
            List<Integer> validMoves = GameRules.getValidMoves(board);
            if (validMoves.isEmpty()) {
                board.printBoard();
                System.out.println("La partida ha terminado en empate.");
                break;
            }

            // Intercambiamos turnos
            User temp = currentTurn;
            currentTurn = waitingTurn;
            waitingTurn = temp;
        }
    }
}
