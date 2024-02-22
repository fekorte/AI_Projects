import java.util.Arrays;

public class PuzzleState {
  private final int[][][] board;
  private final int depth; //equal to g(n)
  private final int fValue; //f(n)
  private final PuzzleState parent;
  private final Action action;
  private final int[] blankPosition;

  public PuzzleState(int[][][] board, int depth, int hValue,
      PuzzleState parent, Action action, int[] blankPosition) {
    this.board = board;
    this.depth = depth;
    this.fValue = depth + hValue;
    this.parent = parent;
    this.action = action;
    this.blankPosition = blankPosition;
  }


  public PuzzleState(int[][][] board, int depth, int hValue,
      PuzzleState parent, Action action) {
    this.board = board;
    this.depth = depth;
    this.fValue = depth + hValue;
    this.parent = parent;
    this.action = action;
    this.blankPosition = new int[]{0, 1, 1};
  }

  public int[][][] getBoard() {
    return board;
  }

  public PuzzleState getParent(){
    return parent;
  }

  public int getFValue(){
    return fValue;
  }

  public int[] getBlankPosition() {
    return blankPosition;
  }

  public int getDepth() {
    return depth;
  }

  public Action getAction(){
    return action;
  }

  //To avoid repeated states, two puzzle states equal each other if their board is similar
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    PuzzleState other = (PuzzleState) obj;
    return Arrays.deepEquals(board, other.board);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(board);
  }
}