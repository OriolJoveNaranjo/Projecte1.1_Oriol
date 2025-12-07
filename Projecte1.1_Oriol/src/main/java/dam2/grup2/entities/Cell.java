/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dam2.grup2.entities;

/**
 *
 * @author alber
 */

/**
 * Representa una posición del tablero de Conecta4.
 * Una celda puede estar vacía o contener una ficha de un jugador.
 */
public class Cell {

    /** Índice de la fila (0 = fila inferior) */
    private final int row;

    /** Índice de la columna (0 = columna izquierda) */
    private final int column;

    /** Ficha que ocupa esta celda, o null si está vacía */
    private Token token;

    /**
     * Crea una nueva celda vacía en una posición específica.
     * @param row índice de fila (0 es la inferior)
     * @param column índice de columna (0 es la izquierda)
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.token = null;
    }

    /** @return la fila de esta celda */
    public int getRow() {
        return row;
    }

    /** @return la columna de esta celda */
    public int getColumn() {
        return column;
    }

    /** @return la ficha contenida en la celda (null si está vacía) */
    public Token getToken() {
        return token;
    }

    /**
     * Establece la ficha que ocupa la celda.
     * @param token ficha a colocar (puede ser null para dejarla vacía)
     */
    public void setToken(Token token) {
        this.token = token;
    }

    /**
     * Indica si la celda está vacía.
     * @return true si no hay ficha, false si está ocupada
     */
    public boolean isEmpty() {
        return token == null;
    }
    
    /**
     * Comprueba si este jugador tiene un token con el símbolo indicado.
     *
     * @param symbol Símbolo a comprobar (por ejemplo 'X' o 'O')
     * @return true si el jugador tiene un token y su propietario tiene el símbolo indicado,
     *         false si el token es null o el símbolo no coincide
     */
    public boolean hasSymbol(char symbol) {
        Token token = getToken();
        return token != null && token.getOwner().getSymbol() == symbol;
    }   
}
