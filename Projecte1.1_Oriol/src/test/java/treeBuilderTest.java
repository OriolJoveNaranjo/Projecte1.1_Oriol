/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */



/**
 *
 * @author orjon
 */
import dam2.grup2.entities.Board;
import dam2.grup2.entities.Node;
import dam2.grup2.entities.Player;
import dam2.grup2.logic.TreeBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test para la clase TreeBuilder.
 * Verifica que se generen correctamente los nodos hijos hasta el nivel máximo.
 */
public class treeBuilderTest {

    private Player playerX;
    private Player playerO;
    private Board emptyBoard;

    @BeforeEach
    void setUp() {
        // Creamos jugadores y asignamos oponentes
        playerX = new Player("Jugador X", 'X');
        playerO = new Player("Jugador O", 'O');
        playerX.setOpponent(playerO);
        playerO.setOpponent(playerX);

        // Creamos un tablero vacío
        emptyBoard = new Board();
    }

    @Test
    void testGenerateTreeRootHasChildren() {
        // Creamos nodo raíz con tablero vacío
        Node root = new Node(emptyBoard, 1, -1); // 1 = jugador X, columna -1 = raíz
        Node tree = TreeBuilder.generateTree(root, playerX, playerO);

        // La raíz debe tener al menos un hijo (cualquier columna disponible)
        assertFalse(tree.getChildren().isEmpty(), "El nodo raíz debe tener hijos");
    }

    @Test
    void testGenerateTreeDepth() {
        Node root = new Node(emptyBoard, 1, -1);
        TreeBuilder.generateTree(root, playerX, playerO);

        // Verificar que el primer hijo tenga nietos (profundidad >= 2)
        Node firstChild = root.getChildren().get(0);
        assertFalse(firstChild.getChildren().isEmpty(), "El primer hijo debe tener hijos (profundidad >= 2)");
    }

    @Test
    void testGenerateTreeScoresAssigned() {
        Node root = new Node(emptyBoard, 1, -1);
        TreeBuilder.generateTree(root, playerX, playerO);

        for (Node child : root.getChildren()) {
            assertNotNull(child.getScore(), "Cada hijo debe tener un score asignado");
        }
    }

    @Test
    void testGenerateTreeColumnValues() {
        Node root = new Node(emptyBoard, 1, -1);
        TreeBuilder.generateTree(root, playerX, playerO);

        for (Node child : root.getChildren()) {
            int col = child.getColumn();
            assertTrue(col >= 0 && col < Board.COLUMNS, "La columna del hijo debe estar dentro de los límites del tablero");
        }
    }
}
