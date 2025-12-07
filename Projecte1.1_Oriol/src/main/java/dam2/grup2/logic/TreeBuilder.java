/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dam2.grup2.logic;

import dam2.grup2.entities.Board;
import dam2.grup2.entities.Node;
import dam2.grup2.entities.Player;
 /**
  * 
 * @author alber
 */

/**
 * TreeBuilder - Clase encargada de construir el árbol de jugadas posibles
 * en el juego Conecta 4, hasta un nivel de profundidad determinado.
 *
 * Este árbol puede ser usado por algoritmos como Minimax para decidir
 * la mejor jugada posible.
 *
 * Esta versión utiliza instancias completas de Player con oponente asignado.
 */

public class TreeBuilder {

    /** Nivel máximo de profundidad del árbol */
    private static final int MAX_LEVEL = 4;

    /**
     * Método principal que genera el árbol completo desde el nodo raíz.
     *
     * @param root nodo raíz que representa el estado inicial del tablero
     * @param playerX jugador con símbolo 'X'
     * @param playerO jugador con símbolo 'O'
     * @return el nodo raíz con todos sus hijos generados
     */
    public static Node generateTree(Node root, Player playerX, Player playerO) {
        generateChildren(root, 0, root.getPlayer(), playerX, playerO);
        return root;
    }

    /**
     * Método recursivo que genera los hijos de un nodo hasta alcanzar el nivel máximo.
     *
     * @param node nodo actual
     * @param level profundidad actual
     * @param currentPlayerId identificador del jugador actual (1 = X, 2 = O)
     * @param playerX instancia del jugador X
     * @param playerO instancia del jugador O
     */
    private static void generateChildren(Node node, int level, int currentPlayerId, Player playerX, Player playerO) {
        // Caso base: si alcanzamos el nivel máximo, detenemos la recursión
        if (level >= MAX_LEVEL) return;

        // Alternamos el jugador para el siguiente turno
        int nextPlayerId = (currentPlayerId == 1) ? 2 : 1;

        // Determinar instancia del jugador actual
        Player currentPlayer = (currentPlayerId == 1) ? playerX : playerO;

        // Recorremos todas las columnas posibles del tablero
        for (int col = 0; col < Board.COLUMNS; col++) {
            // Si la columna no está llena, podemos colocar una ficha
            if (!node.getBoardState().isColumnFull(col)) {
                // Creamos una copia del tablero para simular el movimiento
                Board newBoard = node.getBoardState().copy();
                newBoard.placeDisc(col, currentPlayer); 

                // Creamos un nuevo nodo hijo con el nuevo estado del tablero
                Node child = new Node(newBoard, currentPlayerId, col);

                // Evaluamos el tablero con la heurística de Score
                Score score = new Score();
                child.setScore(score.score(newBoard, currentPlayer));

                // Añadimos el hijo al nodo actual
                node.addChild(child);

                // Llamada recursiva para generar los hijos del nuevo nodo
                generateChildren(child, level + 1, nextPlayerId, playerX, playerO);
            }
        }
    }
}
