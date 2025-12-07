import dam2.grup2.entities.Board;
import dam2.grup2.entities.Move;
import dam2.grup2.entities.Node;
import dam2.grup2.entities.Player;
import dam2.grup2.entities.Token;
import dam2.grup2.logic.GameRules;
import dam2.grup2.logic.Minimax;
import dam2.grup2.logic.Score;
import dam2.grup2.logic.TreeBuilder;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class minimaxTest {

    private Board board;
    private Player playerX;
    private Player playerO;
    private Score score;
    private Minimax minimax;

    @BeforeEach
    void setUp() {
        board = new Board();
        playerX = new Player('X');
        playerO = new Player('O');
        playerX.setOpponent(playerO);
        playerO.setOpponent(playerX);
        score = new Score();
        minimax = new Minimax(score);
    }

    /**
     * ---------------- TEST MOVIMIENTO VÁLIDO ----------------
     */
    @Test
    void testBestMoveIsValid() {

        Node root = new Node(board.copy(), 0, -1);
        TreeBuilder.generateTree(root, playerX, playerO);

        Move move = minimax.getBestMove(root, playerX);

        List<Integer> validMoves = GameRules.getValidMoves(board);

        assertTrue(validMoves.contains(move.getColumn()),
                "El movimiento elegido debe ser válido");
    }

    /**
     * ---------------- TEST VICTORIA INMEDIATA ----------------
     */
    @Test
    void testBestMoveLeadsToVictory() {

        // 3 fichas de X alineadas
        board.getCell(0, 0).setToken(new Token(playerX));
        board.getCell(0, 1).setToken(new Token(playerX));
        board.getCell(0, 2).setToken(new Token(playerX));

        Node root = new Node(board.copy(), 0, -1);
        TreeBuilder.generateTree(root, playerX, playerO);

        Move move = minimax.getBestMove(root, playerX);

        assertEquals(3, move.getColumn(),
                "El Minimax debería elegir la columna que da victoria inmediata");
    }

    /**
     * ---------------- TEST EVITAR DERROTA INMEDIATA ----------------
     */
    @Test
    void testBestMoveBlocksOpponentVictory() {

        // 3 fichas de O alineadas
        board.getCell(0, 0).setToken(new Token(playerO));
        board.getCell(0, 1).setToken(new Token(playerO));
        board.getCell(0, 2).setToken(new Token(playerO));

        Node root = new Node(board.copy(), 0, -1);
        TreeBuilder.generateTree(root, playerX, playerO);

        Move move = minimax.getBestMove(root, playerX);

        assertEquals(3, move.getColumn(),
                "El Minimax debería bloquear la victoria del oponente");
    }

    /**
     * ---------------- TEST PROFUNDIDAD LIMITADA ----------------
     */
    @Test
    void testBestMoveWithDepth1() {

        // Aunque la profundidad ya no se pasa, el árbol se genera igual en TreeBuilder
        Node root = new Node(board.copy(), 0, -1);
        TreeBuilder.generateTree(root, playerX, playerO);

        Move move = minimax.getBestMove(root, playerX);

        assertNotNull(move, "Debe devolver un movimiento incluso con profundidad simulada en TreeBuilder");
    }

    /**
     * ---------------- TEST MOVIMIENTO EN TABLERO CON COL. LLENA ----------------
     */
    @Test
    void testBestMoveOnFullColumn() {

        // Llenamos columna 0
        for (int row = 0; row < 6; row++) {
            board.getCell(row, 0).setToken(new Token(playerX));
        }

        Node root = new Node(board.copy(), 0, -1);
        TreeBuilder.generateTree(root, playerX, playerO);

        Move move = minimax.getBestMove(root, playerX);

        assertNotEquals(0, move.getColumn(),
                "No debe elegir columna llena");
    }
}
