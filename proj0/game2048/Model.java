package game2048;

import java.util.Formatter;
import java.util.Observable;

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
        boolean changed;
        changed = false;

//        for (int col = 0; col < this.size(); col += 1) {
//            for (int row = this.size() - 1; row >= 0; row -= 1) {
//                if (!isTile(row + 1, col, _board)) {
//                    if (this.tile(col, row) == null) {
//                        continue;
//                    }
//                    else {
//                        for (int SubRow = this.size() - 1; row >= 0; row -= 1) {
//                            if (!isTile(SubRow - 1, col, _board)) {
//                                break;
//                            }
//                            else if (this.tile(col, row).value() == this.tile(col, SubRow - 1).value()) {
//                                _board.move(col, row, this.tile(col, SubRow));
//                                break;
//                            }
//                            else if (this.tile(col, SubRow - 1) == null) {
//                                continue;
//                            }
//                            else if (this.tile(col, row).value() != this.tile(col, SubRow - 1).value()) {
//                                break;
//                            }
//                        }
//                        }
//                    }
//                else {
//                    if (this.tile(col, row) == null) {
//                        for (int SubRow = this.size() - 1; row >= 0; row -= 1) {
//                            if (!isTile(SubRow - 1, col, _board)) {
//                                break;
//                            }
//                            else if (this.tile(col, SubRow - 1) != null) {
//                                _board.move(col, row, this.tile(col, SubRow));
//                            }
//                            else {
//                                continue;
//                            }
//                        }
//                    }
//                    else {
//                        break;
//                    }
//                }
//                }
//            }

//        Get columns
          int[][] columns = getColumns();

//        Perform algorithm on each column

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /**
     * Imagine that you take a column from a matrix and then transpose that column into a row.
     * Then this row can be though of as an array on which we can easily white board out the algorithm
     * using a dynamically sized sliding window. I refer to the edges of this window as left and right leg.
     *
     * @param column A column from a matrix. It's elements are ordered in a top-down manner.
     * @return the column after performing the shift up algorithm on it (Re-orienting will take care of the others)
     */
    public int[] algo(int[] column) {
          int left = 0;
          int right = 1;

          while (right <  column.length) {
//              If left zero, then swap, and then right leg
              if (column[left] == 0) {
                  int tmp = column[left];
                  column[left] = column[right];
                  column[right] = tmp;
              }
//              If right zero, then both legs
              if (column[right] == 0) {
                  left++;
              }
//               If same, then merge, and then right leg
              if (column[left] == column[right]) {
                  column[left] = column[left] + column[right];
                  column[right] = 0;
              }
//              If both diff (non both non-zero), then both legs
              if (column[left] != column[right] && (column[left] != 0 && column[right] != 0)) {
                  left++;
              }
              right ++; // Realized right leg is inevitable.
          }
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
    public int[][] getColumns() {
        int[][] m = new int[4][4];
        for (int i = 0; i <= 3; i++) {
            for (int j = 3; j >= 0; j--) {
                Tile t = this._board.tile(i, j);
                if (t == null) {
                    m[i][j] = 0;
                } else {
                    m[i][j] = t.value();
                }
            }
        }
        return m;
    }


    public static void main(String[] args) {
//        int[][] board = {{2, 0, 2, 0}, {4, 4, 2, 2}, {0, 4, 0, 0}, {2, 4, 4, 8}};
//        Model m = new Model(board, 0, 2048, false);
//        int[][] columns = m.getColumns();
//        System.out.println(columns[0][0]);
//        System.out.println(columns[0][1]);
//        System.out.println(columns[0][2]);
//        System.out.println(columns[0][3]);

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

