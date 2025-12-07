package dam2.grup2.logic;

import dam2.grup2.entities.Board;
import dam2.grup2.entities.Cell;
import dam2.grup2.entities.Player;
import dam2.grup2.entities.Token;

/**
 *
 * @author alber
 */

/**
 * Clase Score - Evalúa la "bondad" de un tablero de Conecta 4
 * para un jugador concreto.
 *
 * Escala orientativa de valores:
 * <ul>
 *     <li><b>+10000</b> → victoria del jugador.</li>
 *     <li><b>-10000</b> → victoria del oponente.</li>
 *     <li><b>+100 .. +10</b> → ventajas parciales (2 o 3 fichas seguidas).</li>
 *     <li><b>0</b> → situación neutra o equilibrada.</li>
 * </ul>
 *
 * Este sistema de puntuación se usa en el algoritmo Minimax.
 */
public class Score {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;

    /**
     * Evalúa el tablero actual para un jugador.
     *
     * @param board Tablero del juego
     * @param player Jugador para el cual se evalúa el tablero
     * @return Valor entero positivo si el tablero es favorable, negativo si es desfavorable
     */
    public int score(Board board, Player player) {
        int totalScore = 0;
        char playerSymbol = player.getSymbol(); // Ejemplo: 'X' o 'O'
        char opponentSymbol = player.getOpponent().getSymbol();

        // 1️⃣ Bonus por controlar el centro del tablero (columna 3)
        totalScore += centerControl(board, playerSymbol);

        // 2️⃣ Analizar todas las direcciones posibles (horizontal, vertical y diagonales)
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                // Horizontal →
                if (col + 3 < COLUMNS) {
                    totalScore += evaluateWindow(board, row, col, 0, 1, playerSymbol, opponentSymbol);
                }
                // Vertical ↓
                if (row + 3 < ROWS) {
                    totalScore += evaluateWindow(board, row, col, 1, 0, playerSymbol, opponentSymbol);
                }
                // Diagonal ↘
                if (row + 3 < ROWS && col + 3 < COLUMNS) {
                    totalScore += evaluateWindow(board, row, col, 1, 1, playerSymbol, opponentSymbol);
                }
                // Diagonal ↙
                if (row - 3 >= 0 && col + 3 < COLUMNS) {
                    totalScore += evaluateWindow(board, row, col, -1, 1, playerSymbol, opponentSymbol);
                }
            }
        }

        return totalScore;
    }

    /**
     * Evalúa una ventana (grupo de 4 casillas consecutivas) del tablero
     * y devuelve su puntuación según las fichas del jugador y del oponente.
     *
     * @param board Tablero
     * @param row Fila inicial
     * @param col Columna inicial
     * @param dRow Incremento de fila (dirección)
     * @param dCol Incremento de columna (dirección)
     * @param playerSymbol Símbolo del jugador actual
     * @param opponentSymbol Símbolo del jugador oponente
     * @return Puntuación de esa ventana concreta
     */
    public int evaluateWindow(Board board, int row, int col, int dRow, int dCol, char playerSymbol, char opponentSymbol) {
        int playerCount = 0;
        int opponentCount = 0;

        // Recorre las 4 casillas de la ventana
        for (int i = 0; i < 4; i++) {
            Cell cell = board.getCell(row + i * dRow, col + i * dCol);
            Token token = cell.getToken();

            // Si hay ficha, comprobamos de quién es
            if (token != null) {
                char symbol = token.getOwner().getSymbol();
                if (symbol == playerSymbol) playerCount++;
                else if (symbol == opponentSymbol) opponentCount++;
            }
        }

        // 3️⃣ Asignar puntuación según el patrón encontrado
        if (playerCount == 4) return 10000;      // victoria directa
        if (opponentCount == 4) return -10000;   // derrota directa
        if (playerCount == 3 && opponentCount == 0) return 100;   // ventaja clara
        if (playerCount == 2 && opponentCount == 0) return 10;    // pequeña ventaja
        if (opponentCount == 3 && playerCount == 0) return -100;  // amenaza fuerte del rival
        if (opponentCount == 2 && playerCount == 0) return -10;   // amenaza leve del rival

        return 0; // situación neutra
    }

    /**
     * Calcula un bonus por el control de la columna central del tablero.
     * Esta columna permite más oportunidades de conectar 4 fichas.
     *
     * @param board Tablero actual
     * @param playerSymbol Ficha del jugador ('X' o 'O')
     * @return Puntuación adicional positiva por controlar el centro
     */
    public int centerControl(Board board, char playerSymbol) {
        int centerColumn = COLUMNS / 2; // Columna 3 en un tablero 7x6
        int points = 0;

        for (int row = 0; row < ROWS; row++) {
            Cell cell = board.getCell(row, centerColumn);
            Token token = cell.getToken();

            if (token != null && token.getOwner().getSymbol() == playerSymbol) {
                points += 5; // cada ficha en el centro da +5 puntos
            }
        }

        return points;
    }
}

