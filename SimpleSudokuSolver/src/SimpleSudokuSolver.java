import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Solves a Sudoku puzzle.
 *
 * @author jtnguye3
 */
public class SimpleSudokuSolver {

    /**
     * Main method to run program. Reads board, solves, and prints completed
     * puzzle
     *
     * @param args
     *            name of input file
     */
    public static void main ( final String[] args ) {
        final Board originalBoard = readFile( args[0] );
        final Board finalBoard = solve( originalBoard );
        printFinalBoard( finalBoard );
    }

    /**
     * Creates a Board using an input file
     *
     * @param file
     *            the file to convert to a Board
     * @return a Board
     */
    private static Board readFile ( final String file ) {
        return new Board( file );
    }

    /**
     * Prints the board
     *
     * @param finalBoard
     *            the Board to print
     */
    private static void printFinalBoard ( final Board finalBoard ) {
        finalBoard.printBoard();
    }

    /**
     * Solves the puzzle
     *
     * @param board
     *            the board to solve
     * @return
     */
    private static Board solve ( final Board board ) {
        boolean move = true;
        while ( move ) {
            move = inference( board );
        }
        if ( checkComplete( board.getBoard() ) && checkValid( board.getBoard() ) ) {
            return board;
        }
        System.out.println( "FAIL" );
        return null;
    }

    /**
     * Goes through a Board and makes all the moves it can without guessing. In
     * other words, makes moves that only have one possibility.
     *
     * @param board
     *            the Board
     * @return true if move was made, false if no move was made
     */
    private static boolean inference ( final Board board ) {
        boolean ret = false;
        final int[][] currentBoard = board.getBoard();
        final Possible[][] currentPossible = board.getPossible();
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                if ( currentBoard[i][j] == 0 && currentPossible[i][j].size() == 1 ) {
                    makeMove( board, i, j );
                    ret = true;
                }
            }
        }
        return ret;
    }

    /**
     * Makes a move on the Board
     *
     * @param board
     *            the Board to solve
     * @param x
     *            horizontal number
     * @param y
     *            vertical number
     */
    private static void makeMove ( final Board board, final int x, final int y ) {
        board.getBoard()[x][y] = board.getPossible()[x][y].get( 0 );

        for ( int i = 0; i < 9; i++ ) {
            if ( board.getBoard()[x][i] == 0 ) {
                board.getPossible()[x][i].remove( board.getBoard()[x][y] );
            }
            if ( board.getBoard()[i][y] == 0 ) {
                board.getPossible()[i][y].remove( board.getBoard()[x][y] );
            }
        }

        if ( x < 3 ) {
            // quadrant 1
            if ( y < 3 ) {
                for ( int i = 0; i < 3; i++ ) {
                    for ( int j = 0; j < 3; j++ ) {
                        if ( board.getBoard()[i][j] == 0 ) {
                            board.getPossible()[i][j].remove( board.getBoard()[x][y] );
                        }
                    }
                }
                return;
            }
            // quadrant 7
            else if ( y > 5 ) {
                for ( int i = 0; i < 3; i++ ) {
                    for ( int j = 6; j < 9; j++ ) {
                        if ( board.getBoard()[i][j] == 0 ) {
                            board.getPossible()[i][j].remove( board.getBoard()[x][y] );
                        }
                    }
                }
                return;
            }
            // quadrant 4
            else {
                for ( int i = 0; i < 3; i++ ) {
                    for ( int j = 3; j < 6; j++ ) {
                        if ( board.getBoard()[i][j] == 0 ) {
                            board.getPossible()[i][j].remove( board.getBoard()[x][y] );
                        }
                    }
                }
                return;
            }
        }
        else if ( x > 5 ) {
            // quadrant 3
            if ( y < 3 ) {
                for ( int i = 6; i < 9; i++ ) {
                    for ( int j = 0; j < 3; j++ ) {
                        if ( board.getBoard()[i][j] == 0 ) {
                            board.getPossible()[i][j].remove( board.getBoard()[x][y] );
                        }
                    }
                }
                return;
            }
            // quadrant 9
            else if ( y > 5 ) {
                for ( int i = 6; i < 9; i++ ) {
                    for ( int j = 6; j < 9; j++ ) {
                        if ( board.getBoard()[i][j] == 0 ) {
                            board.getPossible()[i][j].remove( board.getBoard()[x][y] );
                        }
                    }
                }
                return;
            }
            // quadrant 6
            else {
                for ( int i = 6; i < 9; i++ ) {
                    for ( int j = 3; j < 6; j++ ) {
                        if ( board.getBoard()[i][j] == 0 ) {
                            board.getPossible()[i][j].remove( board.getBoard()[x][y] );
                        }
                    }
                }
                return;
            }
        }
        else {
            // quadrant 2
            if ( y < 3 ) {
                for ( int i = 3; i < 6; i++ ) {
                    for ( int j = 0; j < 3; j++ ) {
                        if ( board.getBoard()[i][j] == 0 ) {
                            board.getPossible()[i][j].remove( board.getBoard()[x][y] );
                        }
                    }
                }
                return;
            }
            // quadrant 8
            else if ( y > 5 ) {
                for ( int i = 3; i < 6; i++ ) {
                    for ( int j = 6; j < 9; j++ ) {
                        if ( board.getBoard()[i][j] == 0 ) {
                            board.getPossible()[i][j].remove( board.getBoard()[x][y] );
                        }
                    }
                }
                return;
            }
            // quadrant 5
            else {
                for ( int i = 3; i < 6; i++ ) {
                    for ( int j = 3; j < 6; j++ ) {
                        if ( board.getBoard()[i][j] == 0 ) {
                            board.getPossible()[i][j].remove( board.getBoard()[x][y] );
                        }
                    }
                }
                return;
            }
        }
    }

    /**
     * Checks if the Board is valid
     *
     * @param board
     *            the Board to validate
     * @return true if valid, false if not
     */
    private static boolean checkValid ( final int[][] board ) {
        for ( int i = 0; i < 9; i++ ) {
            final Set<Integer> row = new HashSet<Integer>();
            final Set<Integer> column = new HashSet<Integer>();
            for ( int j = 0; j < 9; j++ ) {
                if ( !row.add( board[i][j] ) || !column.add( board[j][i] ) ) {
                    return false;
                }
            }
        }

        // quadrant 1
        Set<Integer> quadrant = new HashSet<Integer>();
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                if ( board[i][j] != 0 ) {
                    if ( !quadrant.add( board[i][j] ) ) {
                        return false;
                    }
                }
            }
        }

        // quadrant 2
        quadrant = new HashSet<Integer>();
        for ( int i = 3; i < 6; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                if ( board[i][j] != 0 ) {
                    if ( !quadrant.add( board[i][j] ) ) {
                        return false;
                    }
                }
            }
        }

        // quadrant 3
        quadrant = new HashSet<Integer>();
        for ( int i = 6; i < 9; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                if ( board[i][j] != 0 ) {
                    if ( !quadrant.add( board[i][j] ) ) {
                        return false;
                    }
                }
            }
        }

        // quadrant 4
        quadrant = new HashSet<Integer>();
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 3; j < 6; j++ ) {
                if ( board[i][j] != 0 ) {
                    if ( !quadrant.add( board[i][j] ) ) {
                        return false;
                    }
                }
            }
        }

        // quadrant 5
        quadrant = new HashSet<Integer>();
        for ( int i = 3; i < 6; i++ ) {
            for ( int j = 3; j < 6; j++ ) {
                if ( board[i][j] != 0 ) {
                    if ( !quadrant.add( board[i][j] ) ) {
                        return false;
                    }
                }
            }
        }

        // quadrant 6
        quadrant = new HashSet<Integer>();
        for ( int i = 6; i < 9; i++ ) {
            for ( int j = 3; j < 6; j++ ) {
                if ( board[i][j] != 0 ) {
                    if ( !quadrant.add( board[i][j] ) ) {
                        return false;
                    }
                }
            }
        }

        // quadrant 7
        quadrant = new HashSet<Integer>();
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 6; j < 9; j++ ) {
                if ( board[i][j] != 0 ) {
                    if ( !quadrant.add( board[i][j] ) ) {
                        return false;
                    }
                }
            }
        }

        // quadrant 8
        quadrant = new HashSet<Integer>();
        for ( int i = 3; i < 6; i++ ) {
            for ( int j = 6; j < 9; j++ ) {
                if ( board[i][j] != 0 ) {
                    if ( !quadrant.add( board[i][j] ) ) {
                        return false;
                    }
                }
            }
        }

        // quadrant 9
        quadrant = new HashSet<Integer>();
        for ( int i = 6; i < 9; i++ ) {
            for ( int j = 6; j < 9; j++ ) {
                if ( board[i][j] != 0 ) {
                    if ( !quadrant.add( board[i][j] ) ) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Checks of the puzzle is complete (not valid/correct)
     *
     * @param board
     *            the Board to check
     * @return true if complete, false if not
     */
    private static boolean checkComplete ( final int[][] board ) {
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                if ( board[i][j] == 0 ) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Represents a Sudoku board
     *
     * @author jtnguye3
     */
    private static class Board {
        private final int[][] board;    // values for the board
        private Possible[][]  possible; // possible values for the board
        private final Action  action;   // the last action taken
        private Board         parent;   // the previous Board before the Action
                                        // was taken

        /**
         * Creates a Board from an input file
         *
         * @param file
         *            input file
         */
        public Board ( final String file ) {
            Scanner fileScanner = null;
            try {
                fileScanner = new Scanner( new File( file ) );
            }
            catch ( final FileNotFoundException e ) {

            }
            this.board = new int[9][9];
            for ( int i = 0; i < 9; i++ ) {
                for ( int j = 0; j < 9; j++ ) {
                    try {
                        final String next = fileScanner.next();
                        board[i][j] = Integer.parseInt( next );
                    }
                    catch ( final NumberFormatException e ) {
                        board[i][j] = 0;
                    }
                }
            }
            this.possible = new Possible[9][9];
            setPossible();

            this.action = null;
            this.parent = null;
        }

        /**
         * Creates a Board from an Action
         *
         * @param action
         *            the Action made to create a Board
         */
        public Board ( final Action action ) {
            this.board = null;
            this.action = action;
        }

        public int[][] getBoard () {
            return board;
        }

        public Possible[][] getPossible () {
            return possible;
        }

        /**
         * Sets the possible moves for each space
         */
        private void setPossible () {
            for ( int i = 0; i < 9; i++ ) {
                for ( int j = 0; j < 9; j++ ) {
                    possible[i][j] = new Possible();
                    if ( board[i][j] == 0 ) {
                        for ( Integer k = 1; k < 10; k++ ) {
                            possible[i][j].add( k );
                        }
                    }
                }
            }

            for ( int i = 0; i < 9; i++ ) {
                for ( int j = 0; j < 9; j++ ) {
                    if ( board[i][j] != 0 ) {
                        for ( int k = 0; k < 9; k++ ) {
                            if ( board[i][k] == 0 ) {
                                possible[i][k].remove( board[i][j] );
                            }
                            if ( board[k][j] == 0 ) {
                                possible[k][j].remove( board[i][j] );
                            }
                        }
                        clearQuadrant( board[i][j], i, j );
                    }
                }
            }
        }

        /**
         * Removes a possible move from a quadrant.
         *
         * @param num
         *            number to remove
         * @param x
         *            horizontal number
         * @param y
         *            vertical number
         */
        private void clearQuadrant ( final int num, final int x, final int y ) {
            if ( x < 3 ) {
                // quadrant 1
                if ( y < 3 ) {
                    for ( int i = 0; i < 3; i++ ) {
                        for ( int j = 0; j < 3; j++ ) {
                            if ( board[i][j] == 0 ) {
                                possible[i][j].remove( num );
                            }
                        }
                    }
                    return;
                }
                // quadrant 7
                else if ( y > 5 ) {
                    for ( int i = 0; i < 3; i++ ) {
                        for ( int j = 6; j < 9; j++ ) {
                            if ( board[i][j] == 0 ) {
                                possible[i][j].remove( num );
                            }
                        }
                    }
                    return;
                }
                // quadrant 4
                else {
                    for ( int i = 0; i < 3; i++ ) {
                        for ( int j = 3; j < 6; j++ ) {
                            if ( board[i][j] == 0 ) {
                                possible[i][j].remove( num );
                            }
                        }
                    }
                    return;
                }
            }
            else if ( x > 5 ) {
                // quadrant 3
                if ( y < 3 ) {
                    for ( int i = 6; i < 9; i++ ) {
                        for ( int j = 0; j < 3; j++ ) {
                            if ( board[i][j] == 0 ) {
                                possible[i][j].remove( num );
                            }
                        }
                    }
                    return;
                }
                // quadrant 9
                else if ( y > 5 ) {
                    for ( int i = 6; i < 9; i++ ) {
                        for ( int j = 6; j < 9; j++ ) {
                            if ( board[i][j] == 0 ) {
                                possible[i][j].remove( num );
                            }
                        }
                    }
                    return;
                }
                // quadrant 6
                else {
                    for ( int i = 6; i < 9; i++ ) {
                        for ( int j = 3; j < 6; j++ ) {
                            if ( board[i][j] == 0 ) {
                                possible[i][j].remove( num );
                            }
                        }
                    }
                    return;
                }
            }
            else {
                // quadrant 2
                if ( y < 3 ) {
                    for ( int i = 3; i < 6; i++ ) {
                        for ( int j = 0; j < 3; j++ ) {
                            if ( board[i][j] == 0 ) {
                                possible[i][j].remove( num );
                            }
                        }
                    }
                    return;
                }
                // quadrant 8
                else if ( y > 5 ) {
                    for ( int i = 3; i < 6; i++ ) {
                        for ( int j = 6; j < 9; j++ ) {
                            if ( board[i][j] == 0 ) {
                                possible[i][j].remove( num );
                            }
                        }
                    }
                    return;
                }
                // quadrant 5
                else {
                    for ( int i = 3; i < 6; i++ ) {
                        for ( int j = 3; j < 6; j++ ) {
                            if ( board[i][j] == 0 ) {
                                possible[i][j].remove( num );
                            }
                        }
                    }
                    return;
                }
            }
        }

        public Board getParent () {
            return parent;
        }

        /**
         * Prints the Board
         */
        public void printBoard () {
            for ( int i = 0; i < 9; i++ ) {
                for ( int j = 0; j < 9; j++ ) {
                    System.out.print( board[i][j] + " " );
                    if ( j == 2 || j == 5 ) {
                        System.out.print( "| " );
                    }
                }
                System.out.println();
                if ( i == 2 || i == 5 ) {
                    System.out.println( "---------------------" );
                }
            }
        }
    }

    /**
     * Represents an move on the Board. (Not yet implemented. This will be for
     * when I implement solving puzzles that require guessing.)
     *
     * @author jtnguye3
     */
    private static class Action {
        private final int   action;
        private final int   x;
        private final int   y;
        private final Board board;

        /**
         * Constructor for an Action
         *
         * @param action
         *            the number to add to board
         * @param x
         *            horizontal value
         * @param y
         *            vertical value
         * @param board
         *            the Board to make the move to
         */
        public Action ( final int action, final int x, final int y, final Board board ) {
            this.action = action;
            this.x = x;
            this.y = y;
            this.board = board;
        }

        public int getAction () {
            return action;
        }

        public int getX () {
            return x;
        }

        public int getY () {
            return y;
        }

        public Board getBoard () {
            return board;
        }

        public Board getResult () {
            return new Board( this );
        }
    }

    /**
     * Represents a possible move
     */
    private static class Possible {
        private int                 x;        // horizontal value
        private int                 y;        // vertical value
        private final List<Integer> possible; // list of possible values

        public Possible () {
            possible = new LinkedList<Integer>();
        }

        /**
         * Returns the number of possible values
         *
         * @return size
         */
        public int size () {
            return possible.size();
        }

        /**
         * Adds a possible value
         *
         * @param num
         *            value to add
         */
        public void add ( final Integer num ) {
            possible.add( num );
        }

        /**
         * Removes a possible value
         *
         * @param num
         *            value to remove
         */
        public void remove ( final Integer num ) {
            possible.remove( num );
        }

        /**
         * Gets value at given index
         *
         * @param index
         *            position in list
         * @return value at index
         */
        public Integer get ( final int index ) {
            return possible.get( index );
        }
    }
}
