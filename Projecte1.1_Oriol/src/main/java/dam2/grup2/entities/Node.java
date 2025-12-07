/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dam2.grup2.entities;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author orjon
 */

/**
 * Clase Node - Representa un nodo en el árbol de jugadas del juego Conecta 4.
 * Cada nodo contiene el estado del tablero, el jugador que hizo la jugada,
 * la columna donde se colocó la ficha, el valor heurístico del tablero,
 * el nodo padre y una lista de hijos que representan las jugadas siguientes.
 *
 * Esta clase se utiliza en la construcción del árbol de decisiones para
 * algoritmos como Minimax.
 */
public class Node {
    private Board boardState;       // Estado del tablero
    private int player;             // Jugador que realiza la jugada (1 = X, 2 = O, 0 = vacío)
    private int column;             // Columna donde se ha colocado la ficha
    private int score;              // Valor heurístico del nodo
    private Node parent;            // Nodo padre
    private List<Node> children;    // Hijos (siguientes jugadas posibles)

    // Constructor principal
    public Node(Board boardState, int player, int column) {
        this.boardState = boardState;
        this.player = player;
        this.column = column;
        this.score = 0;
        this.children = new ArrayList<>();
        this.parent = null;
    }

    // Métodos getter y setter
    public Board getBoardState() {
        return boardState;
    }

    public void setBoardState(Board boardState) {
        this.boardState = boardState;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    /**
     * Añade un hijo al nodo actual y asigna el nodo padre al hijo.
     *
     * @param child nodo hijo que se va a añadir
     */
    public void addChild(Node child) {
        this.children.add(child);
        child.setParent(this);
    }
}

