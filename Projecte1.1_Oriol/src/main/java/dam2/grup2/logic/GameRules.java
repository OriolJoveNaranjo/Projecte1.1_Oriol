/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dam2.grup2.logic;

import dam2.grup2.entities.Board;
import dam2.grup2.entities.Cell;
import dam2.grup2.entities.Player;
import dam2.grup2.entities.Token;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene las reglas del juego Conecta4.
 * No guarda estado, solo ofrece métodos estáticos para operar sobre GameState y Board.
 *
 * @author alber
 */

public class GameRules {
    
    /**
     * Verifica si se puede insertar una ficha en la columna indicada.
     * @param board tablero actual
     * @param column columna donde el jugador quiere insertar la ficha
     * @return true si hay al menos una celda libre en esa columna
     */
    public static boolean isValidMove(Board board, int column) {
        // La columna debe estar dentro del rango válido
        if (column < 0 || column >= Board.COLUMNS) {
            return false;
        }

        // Si la celda superior (fila 5) de la columna está ocupada
        return board.getCell(Board.ROWS - 1, column).isEmpty();
    }
    
    /**
    * Aplica un movimiento en el tablero.
    * @param board tablero
    * @param column columna donde se quiere colocar la ficha
    * @param player
    */
    public static void applyMove(Board board, int column, Player player) {
        if (!isValidMove(board, column)) {
            throw new IllegalArgumentException("Columna inválida o llena");
        }

        // Crear la ficha del jugador
        Token token = new Token(player);

        // Buscar la primera celda libre desde abajo
        for (int row = 0; row < Board.ROWS; row++) {
            Cell cell = board.getCell(row, column);
            if (cell.isEmpty()) {
                cell.setToken(token); // colocamos la ficha
                break; // solo colocamos una ficha por turno
            }
        }
    }
    
    /**
    * Comprueba si el jugador tiene 4 fichas en línea.
    *
    * @param board tablero a comprobar
    * @param token ficha del jugador a verificar
    * @return true si el jugador gana
    */
   public static boolean checkWin(Board board, Token token) {
       // Obtenemos todas las celdas del tablero
        Cell[][] cells = board.getCell();

       // Guardamos el símbolo del jugador para comparar con las fichas del tablero
        char owner = token.getOwner().getSymbol();

       // Recorremos todas las filas
        for (int r = 0; r < Board.ROWS; r++) {
            // Recorremos todas las columnas
            for (int c = 0; c < Board.COLUMNS; c++) {
                // Guardamos el token que hay en la celda actual
                Token tokenInCell = cells[r][c].getToken();

                // Si la celda está vacía o no pertenece al jugador, saltamos
                if (tokenInCell == null || tokenInCell.getOwner().getSymbol() != owner) {
                    continue;
                }

               // Comprobamos 4 en línea horizontal hacia la derecha
                if (c + 3 < Board.COLUMNS &&
                   match(cells, r, c+1, owner) &&
                   match(cells, r, c+2, owner) &&
                   match(cells, r, c+3, owner)) return true;

               // Comprobamos 4 en línea vertical hacia arriba
                if (r + 3 < Board.ROWS &&
                   match(cells, r+1, c, owner) &&
                   match(cells, r+2, c, owner) &&
                   match(cells, r+3, c, owner)) return true;

               // Diagonal (hacia arriba a la derecha)
                if (r - 3 >= 0 && c + 3 < Board.COLUMNS &&
                    match(cells, r - 1, c + 1, owner) &&
                    match(cells, r - 2, c + 2, owner) &&
                    match(cells, r - 3, c + 3, owner)) return true;

                // Diagonal (hacia abajo a la derecha)
                if (r + 3 < Board.ROWS && c + 3 < Board.COLUMNS &&
                    match(cells, r + 1, c + 1, owner) &&
                    match(cells, r + 2, c + 2, owner) &&
                    match(cells, r + 3, c + 3, owner)) return true;

                // Diagonal (hacia arriba a la izquierda)
                if (r - 3 >= 0 && c - 3 >= 0 &&
                    match(cells, r - 1, c - 1, owner) &&
                    match(cells, r - 2, c - 2, owner) &&
                    match(cells, r - 3, c - 3, owner)) return true;

                // Diagonal (hacia abajo a la izquierda)
                if (r + 3 < Board.ROWS && c - 3 >= 0 &&
                    match(cells, r + 1, c - 1, owner) &&
                    match(cells, r + 2, c - 2, owner) &&
                    match(cells, r + 3, c - 3, owner)) return true;
            }
        }

        // Si no se encontró 4 en línea, el jugador no ha ganado
        return false;
    }

    /**
     * Comprueba si la celda especificada contiene un token del jugador.
     *
     * @param cells matriz de celdas del tablero
     * @param row fila de la celda
     * @param col columna de la celda
     * @param owner símbolo del jugador (char)
     * @return true si la celda tiene un token del jugador
     */
    public static boolean match(Cell[][] cells, int row, int col, char owner) {
        // Obtenemos el token de la celda
        Token t = cells[row][col].getToken();

        // Devolvemos true solo si la celda no está vacía y pertenece al jugador
        return t != null && t.getOwner().getSymbol() == owner;
    }
   
   /**
     * Comprueba si el tablero está completamente lleno (empate).
     *
     * @param board tablero a comprobar
     * @return true si no hay ninguna celda libre en la fila superior
     */
    public static boolean isBoardFull(Board board) {
        for (int c = 0; c < Board.COLUMNS; c++) {
            if (board.getCell(Board.ROWS - 1, c).isEmpty()) {
                return false; // Al menos una columna tiene espacio
            }
        }
        return true; // Todas las columnas están llenas
    }

    /**
     * Genera una lista de columnas válidas donde se puede jugar.
     * 
     * @param board tablero actual
     * @return lista de índices de columna disponibles
     */
    public static List<Integer> getValidMoves(Board board) {
        List<Integer> valid = new ArrayList<>();
        for (int c = 0; c < Board.COLUMNS; c++) {
            if (GameRules.isValidMove(board, c)) {
                valid.add(c);
            }
        }
        return valid;
    }

    /**
     * Comprueba si el tablero está en un estado terminal:
     * - Victoria de cualquier jugador.
     * - Empate (tablero lleno).
     *
     * @param board tablero actual
     * @param player1 jugador IA
     * @param player2 jugador oponente
     * @return true si el juego ha terminado
     */
    public static boolean isTerminalState(Board board, Player player1, Player player2) {
        Token t1 = new Token(player1);
        Token t2 = new Token(player2);
        return GameRules.checkWin(board, t1) || GameRules.checkWin(board, t2) || GameRules.isBoardFull(board);
    }
    
        public static boolean hasWon(Board board, Player player) {
        char symbol = player.getSymbol();
        for (int r = 0; r < Board.ROWS; r++) {
            for (int c = 0; c < Board.COLUMNS; c++) {
                // Horizontal
                if (c + 3 < Board.COLUMNS &&
                    board.getCell(r, c).hasSymbol(symbol) &&
                    board.getCell(r, c + 1).hasSymbol(symbol) &&
                    board.getCell(r, c + 2).hasSymbol(symbol) &&
                    board.getCell(r, c + 3).hasSymbol(symbol)) return true;

                // Vertical
                if (r + 3 < Board.ROWS &&
                    board.getCell(r, c).hasSymbol(symbol) &&
                    board.getCell(r + 1, c).hasSymbol(symbol) &&
                    board.getCell(r + 2, c).hasSymbol(symbol) &&
                    board.getCell(r + 3, c).hasSymbol(symbol)) return true;

                // Diagonal ↘
                if (r + 3 < Board.ROWS && c + 3 < Board.COLUMNS &&
                    board.getCell(r, c).hasSymbol(symbol) &&
                    board.getCell(r + 1, c + 1).hasSymbol(symbol) &&
                    board.getCell(r + 2, c + 2).hasSymbol(symbol) &&
                    board.getCell(r + 3, c + 3).hasSymbol(symbol)) return true;

                // Diagonal ↙
                if (r - 3 >= 0 && c + 3 < Board.COLUMNS &&
                    board.getCell(r, c).hasSymbol(symbol) &&
                    board.getCell(r - 1, c + 1).hasSymbol(symbol) &&
                    board.getCell(r - 2, c + 2).hasSymbol(symbol) &&
                    board.getCell(r - 3, c + 3).hasSymbol(symbol)) return true;
            }
        }
        return false;
    }
}

