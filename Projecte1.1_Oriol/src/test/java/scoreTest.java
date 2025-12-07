
import dam2.grup2.entities.Board;
import dam2.grup2.entities.Player;
import dam2.grup2.entities.Token;
import dam2.grup2.logic.Score;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author panie
 */
public class scoreTest {
    private Board board;
    private Player playerX;
    private Player playerO;
    private Score score;

    @BeforeEach
    void setUp() {
        board = new Board(); // Tablero vacío 6x7
        playerX = new Player('X');
        playerO = new Player('O');
        playerX.setOpponent(playerO);
        playerO.setOpponent(playerX);
        score = new Score();
    }

    /** -------------------- TEST CENTER CONTROL -------------------- */
    @Test
    void testCenterControlEmpty() {
        assertEquals(0, score.centerControl(board, playerX.getSymbol()));
    }

    @Test
    void testCenterControlSingleToken() {
        board.getCell(0, 3).setToken(new Token(playerX));
        assertEquals(5, score.centerControl(board, playerX.getSymbol()));
    }

    @Test
    void testCenterControlMultipleTokens() {
        board.getCell(0, 3).setToken(new Token(playerX));
        board.getCell(1, 3).setToken(new Token(playerX));
        board.getCell(2, 3).setToken(new Token(playerX));
        assertEquals(15, score.centerControl(board, playerX.getSymbol()));
    }

    @Test
    void testCenterControlOpponentTokens() {
        board.getCell(0, 3).setToken(new Token(playerO));
        assertEquals(0, score.centerControl(board, playerX.getSymbol()));
    }

    /** -------------------- TEST EVALUATE WINDOW -------------------- */
    @Test
    void testEvaluateWindowVictoryPlayer() {
        for (int i = 0; i < 4; i++) {
            board.getCell(0, i).setToken(new Token(playerX));
        }
        int result = score.evaluateWindow(board, 0, 0, 0, 1, playerX.getSymbol(), playerO.getSymbol());
        assertEquals(10000, result);
    }

    @Test
    void testEvaluateWindowVictoryOpponent() {
        for (int i = 0; i < 4; i++) {
            board.getCell(0, i).setToken(new Token(playerO));
        }
        int result = score.evaluateWindow(board, 0, 0, 0, 1, playerX.getSymbol(), playerO.getSymbol());
        assertEquals(-10000, result);
    }

    @Test
    void testEvaluateWindowAdvantageClear() {
        board.getCell(0, 0).setToken(new Token(playerX));
        board.getCell(0, 1).setToken(new Token(playerX));
        board.getCell(0, 2).setToken(new Token(playerX));
        int result = score.evaluateWindow(board, 0, 0, 0, 1, playerX.getSymbol(), playerO.getSymbol());
        assertEquals(100, result);
    }

    @Test
    void testEvaluateWindowSmallAdvantage() {
        board.getCell(0, 0).setToken(new Token(playerX));
        board.getCell(0, 1).setToken(new Token(playerX));
        int result = score.evaluateWindow(board, 0, 0, 0, 1, playerX.getSymbol(), playerO.getSymbol());
        assertEquals(10, result);
    }

    @Test
    void testEvaluateWindowThreatStrong() {
        board.getCell(0, 0).setToken(new Token(playerO));
        board.getCell(0, 1).setToken(new Token(playerO));
        board.getCell(0, 2).setToken(new Token(playerO));
        int result = score.evaluateWindow(board, 0, 0, 0, 1, playerX.getSymbol(), playerO.getSymbol());
        assertEquals(-100, result);
    }

    @Test
    void testEvaluateWindowThreatWeak() {
        board.getCell(0, 0).setToken(new Token(playerO));
        board.getCell(0, 1).setToken(new Token(playerO));
        int result = score.evaluateWindow(board, 0, 0, 0, 1, playerX.getSymbol(), playerO.getSymbol());
        assertEquals(-10, result);
    }

    @Test
    void testEvaluateWindowNeutral() {
        board.getCell(0, 0).setToken(new Token(playerX));
        board.getCell(0, 1).setToken(new Token(playerO));
        int result = score.evaluateWindow(board, 0, 0, 0, 1, playerX.getSymbol(), playerO.getSymbol());
        assertEquals(0, result);
    }

    /** -------------------- TEST SCORE TOTAL -------------------- */
    @Test
    void testScoreEmptyBoard() {
        assertEquals(0, score.score(board, playerX));
    }

    @Test
    void testScoreCenterBonus() {
        board.getCell(0, 3).setToken(new Token(playerX));
        board.getCell(1, 3).setToken(new Token(playerX));
        int result = score.score(board, playerX);
        assertTrue(result >= 10); // incluye bonus del centro
    }

    @Test
    void testScoreVictoryHorizontal() {
        // Colocamos 4 fichas consecutivas en la fila 5 (abajo del tablero)
        for (int i = 0; i < 4; i++) {
            board.getCell(5, i).setToken(new Token(playerX));
        }

        int result = score.score(board, playerX);
        assertTrue(result >= 10000, "La puntuación debe ser al menos 10000 (victoria horizontal)");
    }

    @Test
    void testScoreVictoryVertical() {
        // Colocamos 4 fichas consecutivas en la columna 0, desde fila 2 a 5
        for (int i = 2; i < 6; i++) {
            board.getCell(i, 0).setToken(new Token(playerX));
        }

        int result = score.score(board, playerX);
        assertTrue(result >= 10000, "La puntuación debe ser al menos 10000 (victoria vertical)");
    }

    @Test
    void testScoreVictoryDiagonal() {
        // Colocamos 4 fichas en diagonal desde (2,0) ↘ (5,3)
        for (int i = 0; i < 4; i++) {
            board.getCell(2 + i, i).setToken(new Token(playerX));
        }

        int result = score.score(board, playerX);
        assertTrue(result >= 10000, "La puntuación debe ser al menos 10000 (victoria diagonal ↘)");
    }

    @Test
    void testScoreMixed() {
        // 3 fichas jugador en horizontal
        board.getCell(0, 0).setToken(new Token(playerX));
        board.getCell(0, 1).setToken(new Token(playerX));
        board.getCell(0, 2).setToken(new Token(playerX));

        // 2 fichas oponente en vertical
        board.getCell(0, 6).setToken(new Token(playerO));
        board.getCell(1, 6).setToken(new Token(playerO));

        // 1 ficha centro
        board.getCell(2, 3).setToken(new Token(playerX));

        int result = score.score(board, playerX);
        assertTrue(result > 0); // ventaja neta para jugadorX
    }
    @Test
    void testBlockedThreeInARowNeutralOptimized() {
        // 3 fichas jugador
        board.getCell(0, 0).setToken(new Token(playerX));
        board.getCell(0, 1).setToken(new Token(playerX));
        board.getCell(0, 2).setToken(new Token(playerX));
        // bloqueada por oponente
        board.getCell(0, 3).setToken(new Token(playerO));

        int result = score.score(board, playerX);
        assertTrue(result < 100, "Secuencia bloqueada no debería dar puntuación alta");
    }
}
