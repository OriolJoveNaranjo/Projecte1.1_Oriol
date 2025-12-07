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
 * Representa a un jugador del juego Conecta4.
 * Puede ser una persona o la IA.
 */
public class Player {

    /** Nombre del jugador (para mostrar en la interfaz) */
    private final String name;

    /** Símbolo que representa al jugador ('X' o 'O') */
    private final char symbol;
    
    /**Jugador oponente */
    private Player opponent;    

    /**
     * Crea un nuevo jugador.
     * @param name nombre del jugador
     * @param symbol símbolo que representa al jugador ('X' o 'O')
     */
    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }
    
    /**
     * Crea un jugador solo con símbolo (usado para IA o simulaciones).
     * @param symbol símbolo del jugador ('X' o 'O')
     */
    public Player(char symbol) {
        this.name = "Jugador " + symbol;
        this.symbol = symbol;
    }

    /** @return el nombre del jugador */
    public String getName() {
        return name;
    }

    /** @return el símbolo asociado al jugador */
    public char getSymbol() {
        return symbol;
    }
    
     /**
     * Asigna el oponente de este jugador.
     * @param opponent
     */
    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
    
     /**
     * Devuelve el jugador oponente.
     * @return el oponente 
     */
    public Player getOpponent() {
        return opponent;
    }
}

