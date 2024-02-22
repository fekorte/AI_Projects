public class ManhattanDistance {

  public int calculateHeuristic(int[][][] currentState, int[][][] goalState) {
    int heuristic = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        for (int k = 0; k < 3; k++) {
          int tile = currentState[i][j][k];
          if (tile != 0) {
            int[] tileCoordinates = findTileCoordinates(tile, goalState);
            if(tileCoordinates.length == 0) { //Only happens in case of an error
              System.out.println("Error, coordinate not found");
              break;
            }
            heuristic +=
                Math.abs(i - tileCoordinates[0]) +
                Math.abs(j - tileCoordinates[1]) +
                Math.abs(k - tileCoordinates[2]);
          }
        }
      }
    }
    return heuristic;
  }

  private int[] findTileCoordinates(int tile, int[][][] goalState) {
    int[] coordinates = new int[3];
    for (coordinates[0] = 0; coordinates[0] < 3; coordinates[0]++) {
      for (coordinates[1] = 0; coordinates[1] < 3; coordinates[1]++) {
        for (coordinates[2] = 0; coordinates[2] < 3; coordinates[2]++) {
          if (goalState[coordinates[0]][coordinates[1]][coordinates[2]] == tile) {
            return coordinates;
          }
        }
      }
    }
    return new int[0]; // this should never happen if the input is valid
  }
}
