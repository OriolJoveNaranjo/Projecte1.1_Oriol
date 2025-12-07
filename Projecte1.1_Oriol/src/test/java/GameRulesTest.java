/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


import dam2.grup2.entities.Board;
import dam2.grup2.entities.Cell;
import dam2.grup2.entities.Player;
import dam2.grup2.entities.Token;
import dam2.grup2.logic.GameRules;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author alber
 */
public class GameRulesTest {
   
    private Board board;
    private Player player1;
    private Player player2;
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        board = new Board();
        player1 = new Player("Jugador1", 'X');
        player2 = new Player("Jugador2", 'O');
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    void testIsValidMove_ColumnValidAndEmpty() {
        // Columna 0 está vacía
        assertTrue(GameRules.isValidMove(board, 0));
    }
    
    @Test
    void testIsValidMove_ColumnValidButFull() {
        // Llenar completamente la columna 0
        for (int i = 0; i < Board.ROWS; i++) {
            GameRules.applyMove(board, 0, player1);
        }
        
        assertFalse(GameRules.isValidMove(board, 0));
    }
    
    @Test
    void testIsValidMove_ColumnOutOfRangeNegative() {
        assertFalse(GameRules.isValidMove(board, -1));
    }

    @Test
    void testIsValidMove_ColumnOutOfRangePositive() {
        assertFalse(GameRules.isValidMove(board, Board.COLUMNS));
    }

    @Test
    void testApplyMove_ValidMove() {
        GameRules.applyMove(board, 0, player1);
        
        // Verificar que la ficha se colocó en la posición correcta
        Cell cell = board.getCell(0, 0);
        assertFalse(cell.isEmpty());
        assertEquals(player1.getSymbol(), cell.getToken().getOwner().getSymbol());
    }
    

    @Test
    void testApplyMove_MultipleMovesSameColumn() {
        // Aplicar dos movimientos en la misma columna
        GameRules.applyMove(board, 0, player1);
        GameRules.applyMove(board, 0, player2);
        
        // Verificar que las fichas se apilan correctamente
        Cell cell1 = board.getCell(0, 0);
        Cell cell2 = board.getCell(1, 0);
        
        assertEquals(player1.getSymbol(), cell1.getToken().getOwner().getSymbol());
        assertEquals(player2.getSymbol(), cell2.getToken().getOwner().getSymbol());
    }
    
    @Test
    void testCheckWin_HorizontalWin() {
        // Crear 4 en línea horizontal
        GameRules.applyMove(board, 0, player1);
        GameRules.applyMove(board, 1, player1);
        GameRules.applyMove(board, 2, player1);
        GameRules.applyMove(board, 3, player1);
        
        Token token = new Token(player1);
        assertTrue(GameRules.checkWin(board, token));
    }
     @Test
    void testCheckWin_VerticalWin() {
        // Crear 4 en línea vertical
        for (int i = 0; i < 4; i++) {
            GameRules.applyMove(board, 0, player1);
            if (i < 3) {
                // Movimientos alternos para no interferir
                GameRules.applyMove(board, 1, player2);
            }
        }
        
        Token token = new Token(player1);
        assertTrue(GameRules.checkWin(board, token));
    }

    @Test
    void testCheckWin_DiagonalUpRightWin() {
        // Crear 4 en línea diagonal 
        // Patrón: 
        // [0,0]=X [1,1]=X [2,2]=X [3,3]=X
        GameRules.applyMove(board, 0, player1); // Fila 0, Col 0
        
        GameRules.applyMove(board, 1, player2); // Bloqueo
        GameRules.applyMove(board, 1, player1); // Fila 1, Col 1
        
        GameRules.applyMove(board, 2, player2); // Bloqueo
        GameRules.applyMove(board, 2, player2); // Bloqueo
        GameRules.applyMove(board, 2, player1); // Fila 2, Col 2
        
        GameRules.applyMove(board, 3, player2); // Bloqueo
        GameRules.applyMove(board, 3, player2); // Bloqueo
        GameRules.applyMove(board, 3, player2); // Bloqueo
        GameRules.applyMove(board, 3, player1); // Fila 3, Col 3
        
        Token token = new Token(player1);
        assertTrue(GameRules.checkWin(board, token));
    }

    @Test
    void testCheckWin_DiagonalUpLeftWin() {
        // Crear 4 en línea diagonal 
        // Patrón: 
        // [0,3]=X [1,2]=X [2,1]=X [3,0]=X
        GameRules.applyMove(board, 3, player1); // Fila 0, Col 3
        
        GameRules.applyMove(board, 2, player2); // Bloqueo
        GameRules.applyMove(board, 2, player1); // Fila 1, Col 2
        
        GameRules.applyMove(board, 1, player2); // Bloqueo
        GameRules.applyMove(board, 1, player2); // Bloqueo
        GameRules.applyMove(board, 1, player1); // Fila 2, Col 1
        
        GameRules.applyMove(board, 0, player2); // Bloqueo
        GameRules.applyMove(board, 0, player2); // Bloqueo
        GameRules.applyMove(board, 0, player2); // Bloqueo
        GameRules.applyMove(board, 0, player1); // Fila 3, Col 0
        
        Token token = new Token(player1);
        assertTrue(GameRules.checkWin(board, token));
    }

    @Test
    void testCheckWin_NoWin() {
        // Tablero sin 4 en línea
        GameRules.applyMove(board, 0, player1);
        GameRules.applyMove(board, 1, player1);
        GameRules.applyMove(board, 2, player1);
        // Solo 3 en línea, falta el cuarto
        
        Token token = new Token(player1);
        assertFalse(GameRules.checkWin(board, token));
    }

    @Test
    void testIsBoardFull_EmptyBoard() {
        assertFalse(GameRules.isBoardFull(board));
    }

    @Test
    void testIsBoardFull_FullBoard() {
        // Llenar completamente el tablero
        for (int col = 0; col < Board.COLUMNS; col++) {
            for (int row = 0; row < Board.ROWS; row++) {
                Player currentPlayer = (col + row) % 2 == 0 ? player1 : player2;
                GameRules.applyMove(board, col, currentPlayer);
            }
        }
        
        assertTrue(GameRules.isBoardFull(board));
    }

    @Test
    void testIsBoardFull_PartiallyFull() {
        // Llenar solo algunas columnas
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < Board.ROWS; row++) {
                GameRules.applyMove(board, col, player1);
            }
        }
        
        assertFalse(GameRules.isBoardFull(board));
    }

    @Test
    void testIsTerminalState_WinPlayer1() {
        // Crear victoria para player1
        GameRules.applyMove(board, 0, player1);
        GameRules.applyMove(board, 1, player1);
        GameRules.applyMove(board, 2, player1);
        GameRules.applyMove(board, 3, player1);
        
        assertTrue(GameRules.isTerminalState(board, player1, player2));
    }

    @Test
    void testIsTerminalState_WinPlayer2() {
        // Crear victoria para player2
        GameRules.applyMove(board, 0, player2);
        GameRules.applyMove(board, 1, player2);
        GameRules.applyMove(board, 2, player2);
        GameRules.applyMove(board, 3, player2);
        
        assertTrue(GameRules.isTerminalState(board, player1, player2));
    }

    @Test
    void testIsTerminalState_Draw() {
        // Llenar el tablero sin crear 4 en línea
        // Patrón alternado para evitar victorias
        for (int col = 0; col < Board.COLUMNS; col++) {
            for (int row = 0; row < Board.ROWS; row++) {
                Player currentPlayer = (col + row) % 2 == 0 ? player1 : player2;
                GameRules.applyMove(board, col, currentPlayer);
            }
        }
        
        assertTrue(GameRules.isTerminalState(board, player1, player2));
    }

    @Test
    void testIsTerminalState_NonTerminal() {
        // Tablero con solo algunos movimientos
        GameRules.applyMove(board, 0, player1);
        GameRules.applyMove(board, 1, player2);
        
        assertFalse(GameRules.isTerminalState(board, player1, player2));
    }

    @Test
    void testMatch_ValidMatch() {
        // Colocar una ficha y verificar match
        GameRules.applyMove(board, 0, player1);
        Cell[][] cells = board.getCell();
        
        assertTrue(GameRules.match(cells, 0, 0, player1.getSymbol()));
    }

    @Test
    void testMatch_EmptyCell() {
        Cell[][] cells = board.getCell();
        assertFalse(GameRules.match(cells, 0, 0, player1.getSymbol()));
    }

    @Test
    void testMatch_WrongOwner() {
        GameRules.applyMove(board, 0, player1);
        Cell[][] cells = board.getCell();
        
        assertFalse(GameRules.match(cells, 0, 0, player2.getSymbol()));
    }
}
