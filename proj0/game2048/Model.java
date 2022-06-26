package game2048;

import java.util.*;

/** The state of a game of 2048.
 *  @author Yu Hsuan Lee
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board _board;
    /** Current score. */
    private int _score;
    /** Maximum score so far.  Updated when game ends. */
    private int _maxScore;
    /** True iff game is ended. */
    private boolean _gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        this._score = _maxScore = 0;
        this._board = new Board(size);
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        this._board = new Board(rawValues, score);
        this._score = score;
        this._maxScore = maxScore;
        this._gameOver = gameOver;
    }


    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     */
    public Tile tile(int col, int row) {
        return _board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return _board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (_gameOver) {
            _maxScore = Math.max(_score, _maxScore);
        }
        return _gameOver;
    }

    /** Return the current score. */
    public int score() {
        return _score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return _maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        _score = 0;
        _gameOver = false;
        _board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        _board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */
    public boolean tilt(Side side) {
        boolean[] state = {false};
        state[0] = false; // see if this is redundant later. May not be due to observers or something.

        int[][] columns = getColumns(side);
//      Perform algorithm on each column. Achieved through side effects.
        Arrays.asList(columns).stream().forEach((int[] column) -> {
            algo(column,state);
        });
//      Columns are now mutated. Use the resulting transposed matrix to update board state.
        columns = transpose(columns);
        this._board = new Board(columns, this._score);
//        for (int i = 0; i < columns.length; i++) {
//            for (int j = 0; j < columns[i].length; j++) {
////                System.out.println("[" + i + "]"+ "[" + j + "] = " + columns[i][j]);
//                Tile t = this._board.tile();
//            }
//        }

        checkGameOver();
        if (state[0]) {
            setChanged();
        }
        return state[0];
    }

    /**
     * Creates a 2-D array of integers from a given board.
     *
     * Example:
     * If you have a board that looks like this,
     *         2 0 2 0
     *         4 4 2 2
     *         0 4 0 0
     *         2 4 4 8
     * then the array of columns looks like this
     * {{2, 4, 0, 2}, {0, 4, 4, 4}, {2, 2, 0, 4}, {0, 2, 0, 8}}
     * They're read from top-down, left to right.
     *
     *
     * @return int[4][4] Columns oriented in a way that works well with the algorithm
     */
    public int[][] getColumns(Side side) {
        this._board.setViewingPerspective(Side.EAST); // Orient this wya to grab columns to match image above.
        int k = this._board.size();
        int[][] m = new int[k][k];
        for (int i = 0; i <= k - 1; i++) {
            for (int j = 0; j <= k - 1; j++) {
                Tile t = this._board.tile(j, i);
                if (t == null) {
                    m[i][j] = 0;
                } else {
                    m[i][j] = t.value();
                }
            }
        }
        this._board.setViewingPerspective(side);
        return m;
    }

    /**
     * Imagine that you take a column from a matrix and then transpose that column into a row.
     * Then this row can be though of as an array on which we can easily white board out the algorithm
     * using a dynamically sized sliding window. I refer to the edges of this window as left and right leg.
     *
     * This method mutates a column by performing the shift up
     * algorithm on it (Re-orienting will take care of the others).
     * I will change this later to adhere to immutability if it becomes an issue later on.
     *
     * @param column A column from a matrix. It's elements are ordered in a top-down manner.
     */
    public void algo(int[] column, boolean[] state) {
          int left = 0;
          int right = 1;

          while (left <  column.length - 2 && right <= column.length - 1) {
              //Only advance the left leg when a proper comparison is done.
              if (eval(left, right, column, state)) {
                  left ++;
              }
              // Always advance right leg until end.
              if (right < column.length - 1) {
                  right++;
              }
          }
//        Perform the final comparison.
          eval(left, right, column, state);
    }

    /**
     * This mutates the 2D board array by performing swaps and merges where necessary.
     * It returns true only if a comparison between non-zero numbers has occurred. This
     * serves to notify the left leg index of when it is allowed to increment.
     *
     * @param left A trailing index on the dynamically resized sliding window.
     * @param right The leading index on the dynamically resized sliding window.
     * @param column An array of integers representing a column from the board read in top-down fashion.
     * @param state State passed down from top level (tilt -> algo -> here).
     * @return A boolean flag to notify the left index of when it can advance.
     */
    public boolean eval(int left, int right, int[] column, boolean[] state) {
        // Swap
        if (column[left] == 0) {
            int tmp = column[left];
            column[left] = column[right];
            column[right] = tmp;
            return false; // Only increment left leg when a proper comparison has occurred.
        }
        // merge
        else if (column[left] == column[right]) {
            column[left] = column[left] + column[right]; // Maybe change this to * 2 later?
            column[right] = 0;
            this._score += column[left];
        }
        state[0] = true;
        return true;
    }

    /**
     * Transposes a square matrix.
     *
     * note: I need to transpose the resulting matrix after performing the algorithm
     * on the original matrix. This is because I pulled the values from the board using the
     * column perspective and couldn't resolve the issue after trying all 4
     * of the Side view options.
     *
     * @param matrix
     * @return the matrix transposed
     */
    public static int[][] transpose(int[][] matrix) {
        int s = matrix.length;
        int[][] transposed = new int[s][s];
        int h = 0;
        for (int i = 0; i < s; i++) {
            int [] row = new int[s];
            int k = 0;
            for (int j = 0; j < s; j++) {
                row[k] = matrix[j][i];
                k++;
            }
            transposed[h] = row;
            h++;
        }
        return transposed;
    }

    /**
     * I wrote my own mocked-up board with a test board to see if the algorithm works.
     * @param args
     */
    public static void main(String[] args) {
//        int[][] board = {{2, 0, 2, 0}, {4, 4, 2, 2}, {0, 4, 0, 0}, {2, 4, 4, 8}};
        int[][] board = new int[][] {
                {0, 0, 4, 0},
                {0, 0, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        Model m = new Model(board, 0, 0, false);
        m.tilt(Side.NORTH);
        int[][] columns = m.getColumns(Side.NORTH);
        boolean[] state = {false};

//        This performs the algorithm on the mocked up board.
        Arrays.asList(columns).stream().forEach((int[] column) -> {
            m.algo(column, state);
        });

//        Verify that the algorithm works as expected.
        Arrays.stream(columns).flatMapToInt((int[] column) -> Arrays.stream(column))
                .forEach(System.out::println);

//        Run a test on feeding this new board state to a Board.
          Board b = new Board(transpose(columns),0);
          System.out.println(b);
          System.out.println("Done!");
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        _gameOver = checkGameOver(_board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     */
    public static boolean emptySpaceExists(Board b) {
        for (int col = 0; col < b.size(); col += 1) {
            for (int row = 0; row < b.size(); row += 1) {
                if (b.tile(col, row) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for (int col = 0; col < b.size(); col += 1) {
            for (int row = 0; row < b.size(); row += 1) {
                if (b.tile(col, row) == null) {
                    continue;
                }
                else if (b.tile(col, row).value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */

    public static boolean atLeastOneMoveExists(Board b) {
        if (emptySpaceExists(b) == true) {
            return true;
        }
        else if (AdjacentTilesSameVal(b) == true){
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isTile(int row, int col, Board b) {
        return 0 <= col && col < b.size() && 0 <= row && row < b.size();
    }

    public static boolean AdjacentTilesSameVal(Board b) {
        for (int col = 0; col < b.size(); col += 1) {
            for (int row = 0; row < b.size(); row += 1) {
                if (isTile(row + 1, col, b) && b.tile(col, row).value() == b.tile(col, row + 1).value()) {
                    return true;
                }
                else if (isTile(row - 1, col, b) && b.tile(col, row).value() == b.tile(col, row - 1).value()) {
                    return true;
                }
                else if (isTile(row, col - 1, b) && b.tile(col, row).value() == b.tile(col - 1, row).value()) {
                    return true;
                }
                else if (isTile(row, col + 1, b) && b.tile(col, row).value() == b.tile(col + 1, row).value()) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Returns the model as a string, used for debugging. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    /** Returns whether two models are equal. */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    /** Returns hash code of Model’s string. */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

