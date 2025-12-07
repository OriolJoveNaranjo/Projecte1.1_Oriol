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
 * Representa una jugada en el juego Conecta4.
 * Una jugada consiste en colocar una ficha en una columna concreta del tablero.
 *
 * <p>Esta clase pertenece al package 'entities' porque solo almacena datos,
 * sin incluir ninguna lógica de juego.</p>
 */
public class Move {

    /** Columna donde el jugador coloca su ficha (0 = primera columna) */
    private final int column;

    /** Fila donde acaba cayendo la ficha (0 = fila inferior, -1 si aún no se ha calculado) */
    private final int row;

    /** Jugador que realiza la jugada */
    private final Player player;

    /**
     * Crea una jugada completa (columna, fila y jugador).
     * @param column columna donde se coloca la ficha (0–6)
     * @param row fila final donde se coloca la ficha (0–5)
     * @param player jugador que realiza la jugada
     */
    public Move(int column, int row, Player player) {
        this.column = column;
        this.row = row;
        this.player = player;
    }
    
     /**
     * Crea una jugada completa (columna, fila y jugador).
     * @param column columna donde se coloca la ficha (0–6)
     */
    public Move(int column) {
        this.column = column;
        this.row = -1; // aún no calculado
        this.player = null; // aún no asignado
    }

    /**
     * Crea una jugada sin especificar la fila (útil antes de aplicar la jugada en el tablero).
     * @param column columna donde se quiere colocar la ficha
     * @param player jugador que realiza la jugada
     */
    public Move(int column, Player player) {
        this(column, -1, player);
    }

    /** @return columna donde se realiza la jugada */
    public int getColumn() {
        return column;
    }

    /** @return fila donde cae la ficha (-1 si no se ha determinado aún) */
    public int getRow() {
        return row;
    }

    /** @return jugador que realiza la jugada */
    public Player getPlayer() {
        return player;
    }
}


