package dam2.grup2.logic;

import dam2.grup2.entities.Move;
import dam2.grup2.entities.Node;
import dam2.grup2.entities.Player;

public class Minimax {

    private final Score score;

    public Minimax(Score score) {
        this.score = score;
    }

    /**
     * Obtiene el mejor movimiento desde el árbol generado por TreeBuilder.
     *
     * @param root nodo raíz del árbol
     * @param maximizingPlayer jugador IA que queremos favorecer
     */
    public Move getBestMove(Node root, Player maximizingPlayer) {
        int bestValue = Integer.MIN_VALUE;
        Integer bestColumn = null;

        for (Node child : root.getChildren()) {

            int value = minimax(child, false, maximizingPlayer);

            if (value > bestValue) {
                bestValue = value;
                bestColumn = child.getColumn();
            }
        }

        return new Move(bestColumn);
    }

    /**
     * Implementación del algoritmo Minimax NO generativo.
     * Usa únicamente el árbol ya creado por TreeBuilder.
     */
    private int minimax(Node node, boolean maximizing, Player maximizingPlayer) {

        // Caso base: nodo hoja
        if (node.getChildren().isEmpty()) {
            // Recalculamos bien la heurística respecto al jugador IA
            return score.score(node.getBoardState(), maximizingPlayer);
        }

        int bestValue = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Node child : node.getChildren()) {
            int value = minimax(child, !maximizing, maximizingPlayer);

            if (maximizing) {
                bestValue = Math.max(bestValue, value);
            } else {
                bestValue = Math.min(bestValue, value);
            }
        }

        return bestValue;
    }
}
