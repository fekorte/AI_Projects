import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarSearch {
  private PriorityQueue<PuzzleState> priorityQueue;
  private Set<PuzzleState> prioritySet;
  private Set<PuzzleState> visitedNodes;
  private int totalNodesGenerated;
  int[][][] initialState;
  int[][][] goalState;

  public List<PuzzleState> performAStarSearch(int[][][] initialState, int[][][] goalState) {
    this.initialState = initialState;
    this.goalState = goalState;

    initializeAStar();
    return searchGoalNode();
  }

  public int getTotalNodesGenerated(){
    return totalNodesGenerated;
  }

  private void initializeAStar(){
    /*The priority queue will be used to keep track of all f(n) values of the generated nodes.
    The smallest f(n) value is at the front, which helps us to decide which node will be expanded.*/
    priorityQueue = new PriorityQueue<>(Comparator.comparingInt(PuzzleState::getFValue));
    //Add a set which also keeps track of the nodes in the PriorityQueue for better run time
    prioritySet = new HashSet<>();
    /*Once the node with the smallest f(n) value is expanded,
    we pop the node and add it to the visitedNodes set.*/
    visitedNodes = new HashSet<>();

    //Initialize the root node
    PuzzleState root = new PuzzleState(initialState, 0,
        new ManhattanDistance().calculateHeuristic(initialState, goalState), null, null);
    addToPriority(root);
  }

  private List<PuzzleState> searchGoalNode(){
    while (!priorityQueue.isEmpty()) {
      PuzzleState currentNode = priorityQueue.poll(); //pop the front node

      //Check if current node (which has the lowest f(n) cost) equals the goal state
      if (Arrays.deepEquals(currentNode.getBoard(), goalState)) {
        //Goal state reached, reconstruct the optimal goal path
        return reconstructGoalPath(currentNode);
      }

      /*If the current node is not the goal state, we add it to our visitedNodes set
      to avoid repeated states when generating new child nodes*/
      visitedNodes.add(currentNode);
      prioritySet.remove(currentNode);

      updatePriorityQueue(generateChildNodes(currentNode));
    }
    return Collections.emptyList(); //No solution found
  }

  private List<PuzzleState> reconstructGoalPath(PuzzleState goalNode) {
    List<PuzzleState> path = new ArrayList<>();
    while (goalNode != null) {
      path.add(goalNode);
      goalNode = goalNode.getParent();
    }
    Collections.reverse(path);
    return path;
  }

  private void updatePriorityQueue(List<PuzzleState> expandedNodes) {
    for (PuzzleState childNode : expandedNodes) {
      /*To avoid repeated states, we don't add child nodes similar to those that we have
      already visited to the queue.*/
      if (visitedNodes.contains(childNode))
        continue;

      //If the child node is not in the priority queue, add it
      if (!prioritySet.contains(childNode)) {
        addToPriority(childNode);
      } else {
        /*If the child node is in the priority queue with a higher cost, update the queue and replace
        the node with higher cost with the new node*/
        PuzzleState existingNode = getNodeFromPriorityQueue(childNode);
        if (existingNode != null && childNode.getDepth() < existingNode.getDepth()) {
          removeFromPriority(existingNode);
          addToPriority(childNode);
        }
      }
      /*If the neighbour is in the priority queue with a lower cost,
      don't update the priority queue*/
    }
  }

  private PuzzleState getNodeFromPriorityQueue(PuzzleState neighbor) {
    return priorityQueue.stream()
        .filter(node -> node.equals(neighbor))
        .findFirst().orElse(null);
  }

  private void addToPriority(PuzzleState node) {
    priorityQueue.add(node);
    prioritySet.add(node);
    totalNodesGenerated++;
  }

  private void removeFromPriority(PuzzleState node) {
    priorityQueue.remove(node);
    prioritySet.remove(node);
  }

  private List<PuzzleState> generateChildNodes(PuzzleState currentState) {
    List<PuzzleState> childNodes = new ArrayList<>();

    // Retrieve the coordinates of the blank position on the board
    int blankX = currentState.getBlankPosition()[0];
    int blankY = currentState.getBlankPosition()[1];
    int blankZ = currentState.getBlankPosition()[2];

    // Iterate through all possible actions and apply them to the blank space
    for (Action action : Action.values()) {
      int newX = blankX + action.getX();
      int newY = blankY + action.getY();
      int newZ = blankZ + action.getZ();

      // Check if action is allowed/possible
      if (isValidMove(newX, newY, newZ)) {
        int[][][] newBoard = copyBoard(currentState.getBoard());

        // Swap the blank space with the tile on the respective coordinate
        newBoard[blankX][blankY][blankZ] = newBoard[newX][newY][newZ];
        newBoard[newX][newY][newZ] = 0;

        // Update blank space coordinates in the new board
        int[] newBlankPosition = {newX, newY, newZ};

        // Create child node
        PuzzleState child = new PuzzleState(newBoard, currentState.getDepth() + 1,
            new ManhattanDistance().calculateHeuristic(newBoard, goalState),
            currentState, action, newBlankPosition);
        childNodes.add(child);
      }
    }
    return childNodes;
  }

  private int[][][] copyBoard(int[][][] original) {
    int[][][] copy = new int[3][3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        copy[i][j] = Arrays.copyOf(original[i][j], 3);
      }
    }
    return copy;
  }

  //Check if the coordinate of our move is not out of the bounds of our board
  private boolean isValidMove(int x, int y, int z) {
    return x >= 0 && x < 3 && y >= 0 && y < 3 && z >= 0 && z < 3;
  }
}
