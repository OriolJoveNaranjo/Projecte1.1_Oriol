package dam2.grup2.entities;
/**
 *
 * @author alber
 */
public class Board {
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;

    private Cell[][] cells;

    // Constructor: inicializa el tablero con celdas vacías
    public Board() {
        cells = new Cell[ROWS][COLUMNS];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                cells[r][c] = new Cell(r, c);
            }
        }
    }

    /**
     * Devuelve la celda en la posición indicada.
     * @param row fila
     * @param column columna
     * @return celda correspondiente
     */
    public Cell getCell(int row, int column) {
        return cells[row][column];
    }

    /**
     * Devuelve toda la matriz de celdas del tablero.
     * @return matriz de celdas
     */
    public Cell[][] getCell() {
        return cells;
    }

    /**
     * Verifica si una columna está llena (la celda superior está ocupada).
     * @param col índice de columna
     * @return true si la columna está llena
     */
    public boolean isColumnFull(int col) {
        if (col < 0 || col >= COLUMNS) {
            throw new IllegalArgumentException("Columna fuera de rango");
        }
        return !getCell(ROWS - 1, col).isEmpty();
    }

    /**
     * Coloca una ficha en la primera celda vacía de la columna.
     * @param col índice de columna
     * @param player player
     */
    public void placeDisc(int col, Player player) {
        if (col < 0 || col >= COLUMNS) {
            throw new IllegalArgumentException("Columna fuera de rango");
        }

        Token token = new Token(player);

        for (int row = 0; row < ROWS; row++) {
            Cell cell = getCell(row, col);
            if (cell.isEmpty()) {
                cell.setToken(token);
                return;
            }
        }

        throw new IllegalStateException("La columna está llena");
    }

    /**
     * Crea una copia profunda del tablero actual.
     * @return nuevo tablero con el mismo estado
     */
        public Board copy() {
            Board newBoard = new Board();

            Player playerX = new Player("Jugador X", 'X');
            Player playerO = new Player("Jugador O", 'O');
            playerX.setOpponent(playerO);
            playerO.setOpponent(playerX);

            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLUMNS; c++) {
                    Token originalToken = this.cells[r][c].getToken();
                    if (originalToken != null) {
                        Player owner = originalToken.getOwner();
                        Player mapped = (owner.getSymbol() == 'X') ? playerX : playerO;
                        newBoard.cells[r][c].setToken(new Token(mapped));
                    }
                }
            }

            return newBoard;
        }
    /**
     * Imprime el tablero en consola (para depuración).
     */
        public void printBoard() {
            // 1. Imprimir índices de columna
            System.out.print("   ");
            for (int c = 0; c < COLUMNS; c++) {
                System.out.print(c + " ");
            }
            System.out.println();

            // 2. Imprimir cada fila con su número
            for (int row = ROWS - 1; row >= 0; row--) {
                // número de fila
                System.out.print(row + " |");

                // contenido de la fila
                for (int col = 0; col < COLUMNS; col++) {
                    Token token = getCell(row, col).getToken();
                    char symbol = (token != null) ? token.getOwner().getSymbol() : '.';
                    System.out.print(symbol + " ");
                }
                System.out.println();
            }
        }

}

