import java.util.*;

// === PLAYER (Pre-populated) ===
class Player {
    private String name;
    private char symbol;

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }
}

// === BOARD ===
class Board {
    private int size;
    private char[][] grid;

    public Board(int size) {
        this.size = size;
        grid = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    public boolean placeSymbol(int row, int col, char symbol) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            System.out.println("Invalid position!");
            return false;
        }

        if (grid[row][col] != ' ') {
            System.out.println("Cell already occupied!");
            return false;
        }

        grid[row][col] = symbol;
        return true;
    }

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == ' ')
                    return false;
            }
        }
        return true;
    }

    public int getSize() {
        return size;
    }

    public char[][] getGrid() {
        return grid;
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("[" + grid[i][j] + "]");
            }
            System.out.println();
        }
        System.out.println();
    }
}

// === GAME ENGINE ===
class GameEngine {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public GameEngine(Player p1, Player p2, int boardSize) {
        this.board = new Board(boardSize);
        this.player1 = p1;
        this.player2 = p2;
        this.currentPlayer = p1;
    }

    public boolean checkWin(char symbol) {
        char[][] grid = board.getGrid();
        int size = board.getSize();

        // Check rows
        for (int i = 0; i < size; i++) {
            boolean win = true;
            for (int j = 0; j < size; j++) {
                if (grid[i][j] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win)
                return true;
        }

        // Check columns
        for (int j = 0; j < size; j++) {
            boolean win = true;
            for (int i = 0; i < size; i++) {
                if (grid[i][j] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win)
                return true;
        }

        // Main diagonal
        boolean win = true;
        for (int i = 0; i < size; i++) {
            if (grid[i][i] != symbol) {
                win = false;
                break;
            }
        }
        if (win)
            return true;

        // Secondary diagonal
        win = true;
        for (int i = 0; i < size; i++) {
            if (grid[i][size - 1 - i] != symbol) {
                win = false;
                break;
            }
        }
        if (win)
            return true;

        return false;
    }

    public void playTurn(int row, int col) {
        System.out.println(currentPlayer.getName() + " attempts to place '" +
                currentPlayer.getSymbol() + "' at (" + row + ", " + col + ")");

        if (board.placeSymbol(row, col, currentPlayer.getSymbol())) {

            board.printBoard();

            if (checkWin(currentPlayer.getSymbol())) {
                System.out.println(currentPlayer.getName() + " wins!");
                return;
            }

            if (board.isFull()) {
                System.out.println("It's a draw!");
                return;
            }

            switchPlayer();
        }
    }

    public void switchPlayer() {
        if (currentPlayer == player1)
            currentPlayer = player2;
        else
            currentPlayer = player1;
    }
}

// === MAIN CLASS ===
public class TicTacToe {
    public static void main(String[] args) {

        Player p1 = new Player("Alice", 'X');
        Player p2 = new Player("Bob", 'O');

        GameEngine engine = new GameEngine(p1, p2, 3);

        engine.playTurn(0, 0);
        engine.playTurn(1, 1);
        engine.playTurn(0, 1);
        engine.playTurn(1, 2);
        engine.playTurn(0, 2);
    }
}